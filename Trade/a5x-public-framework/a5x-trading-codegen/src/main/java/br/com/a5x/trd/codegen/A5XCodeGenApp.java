package br.com.a5x.trd.codegen;

import java.io.InputStream;
import java.util.List;

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

public class A5XCodeGenApp {
	public static void main(String[] args) {
		try {
			String xmlFile = args.length > 0 ? args[0] : "/mitch-messages.xml";
			String outputDir = args.length > 1 ? args[1] : "./generated";
			String language = args.length > 2 ? args[2] : "java";
			
			InputStream xmlStream = A5XCodeGenApp.class.getResourceAsStream(xmlFile);
			if (xmlStream == null) {
				System.err.println("XML file not found: " + xmlFile);
				return;
			}
			
			boolean isNative = xmlFile.contains("native");
			
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
						return;
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
						return;
				}
				
				System.out.println("Generated " + messages.size() + " " + language + " classes in " + outputDir);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
