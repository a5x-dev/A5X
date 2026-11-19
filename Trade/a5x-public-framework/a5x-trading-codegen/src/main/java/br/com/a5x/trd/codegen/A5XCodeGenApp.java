package br.com.a5x.trd.codegen;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import br.com.a5x.trd.codegen.mitch.MitchCSharpGenerator;
import br.com.a5x.trd.codegen.mitch.MitchCodeGenerator;
import br.com.a5x.trd.codegen.mitch.MitchCppGenerator;
import br.com.a5x.trd.codegen.mitch.MitchWiresharkGenerator;
import br.com.a5x.trd.codegen.mitch.MitchWrapperGenerator;
import br.com.a5x.trd.codegen.mitch.MitchXmlParser;
import br.com.a5x.trd.codegen.nativ.NativeCodeGenerator;
import br.com.a5x.trd.codegen.nativ.NativeCSharpGenerator;
import br.com.a5x.trd.codegen.nativ.NativeCppGenerator;
import br.com.a5x.trd.codegen.nativ.NativeWiresharkGenerator;
import br.com.a5x.trd.codegen.nativ.NativeWrapperGenerator;
import br.com.a5x.trd.codegen.nativ.NativeXmlParser;
import br.com.a5x.trd.codegen.model.MessageDefinition;

/**
 * Generates the SDK from a schema.
 *
 * <pre>
 * A5XCodeGenApp [--schema &lt;schema&gt;] [--lang &lt;language&gt;] [--out &lt;outputDir&gt;]
 * A5XCodeGenApp &lt;schema&gt; &lt;outputDir&gt; &lt;language&gt;
 *   schema    a schema file, a classpath resource (/mitch-messages.xml, the current version),
 *             or a directory with one sub-directory per specification version (spec/)
 *   language  java, csharp, cpp, wireshark or all
 * </pre>
 *
 * The named options go in any order and each one can be left out (defaults:
 * /mitch-messages.xml, java, ./generated). The positional form is still accepted.
 *
 * The protocol (MITCH or Native) comes from the namespace of the root element of the schema,
 * not from the file name.
 *
 * Given the spec/ directory, every version is generated: spec/&lt;version&gt;/mitch-messages.xml
 * goes to &lt;outputDir&gt;/&lt;version&gt;/mitch, and the same for native-messages.xml.
 */
public class A5XCodeGenApp {

	private static final String[] PROTOCOLS = { "mitch", "native" };

	static final String MITCH_NAMESPACE = "http://a5x.com.br/mitch";
	static final String NATIVE_NAMESPACE = "http://a5x.com.br/native";

	static final String USAGE = String.join(System.lineSeparator(),
			"Usage: A5XCodeGenApp [--schema <schema>] [--lang <language>] [--out <outputDir>]",
			"       A5XCodeGenApp <schema> <outputDir> <language>",
			"  --schema, -s  schema file, classpath resource or spec/ directory (default /mitch-messages.xml)",
			"  --lang, -l    java, csharp, cpp, wireshark or all (default java)",
			"  --out, -o     output directory (default ./generated)");

	/** The command line: the schema, the output directory and the language. */
	record Options(String schema, String outputDir, String language, boolean help) {

		private static final String[] NAMES = { "schema", "output directory", "language" };

		static Options parse(String... args) {
			String[] values = new String[3];
			boolean[] named = new boolean[3];
			List<String> positional = new ArrayList<>();
			boolean help = false;

			for (int i = 0; i < args.length; i++) {
				String arg = args[i];
				if (arg.equals("-h") || arg.equals("--help")) {
					help = true;
					continue;
				}
				if (!arg.startsWith("-") || arg.equals("-")) {
					positional.add(arg);
					continue;
				}
				String name = arg;
				String value = null;
				int eq = arg.indexOf('=');
				if (arg.startsWith("--") && eq > 0) {
					name = arg.substring(0, eq);
					value = arg.substring(eq + 1);
				}
				int slot = switch (name) {
					case "--schema", "-s" -> 0;
					case "--out", "--output", "-o" -> 1;
					case "--lang", "--language", "-l" -> 2;
					default -> throw new IllegalArgumentException("Unknown option: " + arg);
				};
				if (value == null) {
					if (i + 1 >= args.length) {
						throw new IllegalArgumentException("Missing value for " + name);
					}
					value = args[++i];
				}
				if (named[slot]) {
					throw new IllegalArgumentException("The " + NAMES[slot] + " was given twice");
				}
				named[slot] = true;
				values[slot] = value;
			}

			// Positional arguments keep their original order: schema, output directory, language.
			if (positional.size() > 3) {
				throw new IllegalArgumentException("Too many arguments: " + positional);
			}
			for (int slot = 0; slot < positional.size(); slot++) {
				if (named[slot]) {
					throw new IllegalArgumentException("The " + NAMES[slot]
							+ " was given both as an option and as argument " + (slot + 1));
				}
				values[slot] = positional.get(slot);
			}

			return new Options(
					values[0] != null ? values[0] : "/mitch-messages.xml",
					values[1] != null ? values[1] : "./generated",
					values[2] != null ? values[2] : "java",
					help);
		}
	}

	public static void main(String[] args) {
		int status = run(args);
		if (status != 0) {
			System.exit(status);
		}
	}

	/** Runs the generator and returns the process exit status. */
	static int run(String... args) {
		Options options;
		try {
			options = Options.parse(args);
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			System.err.println(USAGE);
			return 2;
		}
		if (options.help()) {
			System.out.println(USAGE);
			return 0;
		}
		try {
			Path path = Path.of(options.schema());
			boolean ok = Files.isDirectory(path)
					? generateVersions(path, Path.of(options.outputDir()), options.language())
					: generate(options.schema(), options.outputDir(), options.language());
			return ok ? 0 : 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}

	/** One output tree per version found under specDir. */
	public static boolean generateVersions(Path specDir, Path outputDir, String language) throws Exception {
		List<Path> versions;
		try (Stream<Path> children = Files.list(specDir)) {
			versions = children.filter(Files::isDirectory).sorted().toList();
		}
		if (versions.isEmpty()) {
			System.err.println("No version directory under " + specDir);
			return false;
		}
		for (Path version : versions) {
			for (String protocol : PROTOCOLS) {
				Path xml = version.resolve(protocol + "-messages.xml");
				if (!Files.exists(xml)) continue;
				Path target = outputDir.resolve(version.getFileName().toString()).resolve(protocol);
				if (!generate(xml.toString(), target.toString(), language)) return false;
			}
		}
		return true;
	}

	/** A schema file, or a resource on the classpath when no such file exists. */
	private static InputStream open(String xmlFile) throws IOException {
		Path path = Path.of(xmlFile);
		if (Files.isRegularFile(path)) return Files.newInputStream(path);
		return A5XCodeGenApp.class.getResourceAsStream(xmlFile);
	}

	/**
	 * The namespace of the root element: http://a5x.com.br/mitch or http://a5x.com.br/native for
	 * the binary protocols, none for a QuickFIX dictionary. Only the start of the document is read.
	 */
	static String rootNamespace(byte[] xml) throws XMLStreamException {
		XMLInputFactory factory = XMLInputFactory.newFactory();
		factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
		factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
		factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);
		XMLStreamReader reader = factory.createXMLStreamReader(new ByteArrayInputStream(xml));
		try {
			while (reader.hasNext()) {
				if (reader.next() == XMLStreamReader.START_ELEMENT) {
					String namespace = reader.getNamespaceURI();
					return namespace == null ? "" : namespace;
				}
			}
			return "";
		} finally {
			reader.close();
		}
	}

	public static boolean generate(String xmlFile, String outputDir, String language) throws Exception {
		byte[] xml;
		try (InputStream xmlStream = open(xmlFile)) {
			if (xmlStream == null) {
				System.err.println("XML file not found: " + xmlFile);
				return false;
			}
			xml = xmlStream.readAllBytes();
		}

		String namespace = rootNamespace(xml);
		boolean isNative;
		if (NATIVE_NAMESPACE.equals(namespace)) {
			isNative = true;
		} else if (MITCH_NAMESPACE.equals(namespace)) {
			isNative = false;
		} else {
			System.err.println(xmlFile + " is not a MITCH or Native schema: the root element must be in the namespace "
					+ MITCH_NAMESPACE + " or " + NATIVE_NAMESPACE
					+ (namespace.isEmpty() ? " (it has none)" : " (it is " + namespace + ")")
					+ ". The FIX dictionaries are used as they are by the QuickFIX engines.");
			return false;
		}

		try (InputStream xmlStream = new ByteArrayInputStream(xml)) {
			if (isNative) {
				NativeXmlParser parser = new NativeXmlParser();
				List<MessageDefinition> messages = parser.parse(xmlStream);

				switch (language.toLowerCase()) {
					case "java":
						new NativeCodeGenerator().generate(messages, outputDir + "/java");
						new NativeWrapperGenerator().generate(messages, outputDir + "/java");
						break;
					case "csharp":
					case "cs":
						new NativeCSharpGenerator().generate(messages, outputDir + "/csharp");
						new NativeWrapperGenerator().generateCSharp(messages, outputDir + "/csharp");
						break;
					case "cpp":
					case "c++":
						new NativeCppGenerator().generate(messages, outputDir + "/cpp");
						new NativeWrapperGenerator().generateCpp(messages, outputDir + "/cpp");
						break;
					case "wireshark":
					case "lua":
						new NativeWiresharkGenerator().generate(messages, outputDir + "/wireshark");
						break;
					case "all":
						new NativeCodeGenerator().generate(messages, outputDir + "/java");
						new NativeWrapperGenerator().generate(messages, outputDir + "/java");
						new NativeCSharpGenerator().generate(messages, outputDir + "/csharp");
						new NativeWrapperGenerator().generateCSharp(messages, outputDir + "/csharp");
						new NativeCppGenerator().generate(messages, outputDir + "/cpp");
						new NativeWrapperGenerator().generateCpp(messages, outputDir + "/cpp");
						new NativeWiresharkGenerator().generate(messages, outputDir + "/wireshark");
						break;
					default:
						System.err.println("Unknown language: " + language);
						System.err.println("Supported: java, csharp, cpp, wireshark, all");
						return false;
				}
				System.out.println("Generated " + messages.size() + " Native " + language + " classes in " + outputDir);
			} else {
				MitchXmlParser parser = new MitchXmlParser();
				List<MessageDefinition> messages = parser.parse(xmlStream);

				switch (language.toLowerCase()) {
					case "java":
						MitchCodeGenerator javaGen = new MitchCodeGenerator();
						javaGen.generate(messages, outputDir + "/java");
						MitchWrapperGenerator wrapperGen = new MitchWrapperGenerator();
						wrapperGen.generate(messages, outputDir + "/java");
						break;
					case "csharp":
					case "cs":
						MitchCSharpGenerator csGen = new MitchCSharpGenerator();
						csGen.generate(messages, outputDir + "/csharp");
						MitchWrapperGenerator csWrapperGen = new MitchWrapperGenerator();
						csWrapperGen.generateCSharp(messages, outputDir + "/csharp");
						break;
					case "cpp":
					case "c++":
						MitchCppGenerator cppGen = new MitchCppGenerator();
						cppGen.generate(messages, outputDir + "/cpp");
						MitchWrapperGenerator cppWrapperGen = new MitchWrapperGenerator();
						cppWrapperGen.generateCpp(messages, outputDir + "/cpp");
						break;
					case "wireshark":
					case "lua":
						MitchWiresharkGenerator wsGen = new MitchWiresharkGenerator();
						wsGen.generate(messages, outputDir + "/wireshark");
						break;
					case "all":
						new MitchCodeGenerator().generate(messages, outputDir + "/java");
						new MitchWrapperGenerator().generate(messages, outputDir + "/java");
						new MitchCSharpGenerator().generate(messages, outputDir + "/csharp");
						new MitchWrapperGenerator().generateCSharp(messages, outputDir + "/csharp");
						new MitchCppGenerator().generate(messages, outputDir + "/cpp");
						new MitchWrapperGenerator().generateCpp(messages, outputDir + "/cpp");
						new MitchWiresharkGenerator().generate(messages, outputDir + "/wireshark");
						break;
					default:
						System.err.println("Unknown language: " + language);
						System.err.println("Supported: java, csharp, cpp, wireshark, all");
						return false;
				}

				System.out.println("Generated " + messages.size() + " " + language + " classes in " + outputDir);
			}
			return true;
		}
	}
}
