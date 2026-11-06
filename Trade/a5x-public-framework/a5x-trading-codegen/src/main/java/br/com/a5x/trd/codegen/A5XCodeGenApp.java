package br.com.a5x.trd.codegen;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

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
 * A5XCodeGenApp &lt;schema&gt; &lt;outputDir&gt; &lt;language&gt;
 *   schema    a classpath resource (/mitch-messages.xml, the current version), a schema file,
 *             or a directory with one sub-directory per specification version (spec/)
 *   language  java, csharp, cpp, wireshark or all
 * </pre>
 *
 * Given the spec/ directory, every version is generated: spec/&lt;version&gt;/mitch-messages.xml
 * goes to &lt;outputDir&gt;/&lt;version&gt;/mitch, and the same for native-messages.xml.
 */
public class A5XCodeGenApp {

	private static final String[] PROTOCOLS = { "mitch", "native" };

	public static void main(String[] args) {
		try {
			String xmlFile = args.length > 0 ? args[0] : "/mitch-messages.xml";
			String outputDir = args.length > 1 ? args[1] : "./generated";
			String language = args.length > 2 ? args[2] : "java";

			Path path = Path.of(xmlFile);
			boolean ok;
			if (Files.isDirectory(path)) {
				ok = generateVersions(path, Path.of(outputDir), language);
			} else {
				ok = generate(xmlFile, outputDir, language);
			}
			if (!ok) {
				System.exit(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
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

	public static boolean generate(String xmlFile, String outputDir, String language) throws Exception {
		try (InputStream xmlStream = open(xmlFile)) {
			if (xmlStream == null) {
				System.err.println("XML file not found: " + xmlFile);
				return false;
			}

			boolean isNative = Path.of(xmlFile).getFileName().toString().contains("native");

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
