package br.com.a5x.trd.codegen.mitch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import br.com.a5x.trd.codegen.model.MessageDefinition;

public class MitchWrapperGenerator {
    
    private static final String PACKAGE = "br.com.a5x.trd.pub.mitch.model";
    
    public void generate(List<MessageDefinition> messages, String outputDir) throws IOException {
        generateJavaWrapper(messages, outputDir);
    }
    
    public void generateCSharp(List<MessageDefinition> messages, String outputDir) throws IOException {
        generateCSharpWrapper(messages, outputDir);
    }
    
    public void generateCpp(List<MessageDefinition> messages, String outputDir) throws IOException {
        generateCppWrapper(messages, outputDir);
    }
    
    private void generateJavaWrapper(List<MessageDefinition> messages, String outputDir) throws IOException {
        StringBuilder sb = new StringBuilder();
        
        sb.append("package ").append(PACKAGE).append(";\n\n");
        sb.append("import java.nio.ByteBuffer;\n\n");
        sb.append("import br.com.a5x.trd.pub.fw.utils.BitUtils;\n\n");
        
        sb.append("/**\n");
        sb.append(" * Wrapper class for parsing and dispatching MITCH protocol messages.\n");
        sb.append(" * Handles all MITCH message types and routes them to appropriate message objects.\n");
        sb.append(" */\n");
        sb.append("public class MitchEventWrapper {\n");
        sb.append("\tpublic char eventType;\n");
        
        // Declare message instances
        for (MessageDefinition msg : messages) {
            String varName = toVariableName(msg.getName());
            sb.append("\tpublic ").append(msg.getName()).append(" ").append(varName);
            sb.append(" = new ").append(msg.getName()).append("();\n");
        }
        
        sb.append("\t\n");
        sb.append("\t/**\n");
        sb.append("\t * Parses a MITCH message from ByteBuffer and populates the appropriate message object.\n");
        sb.append("\t * Automatically handles message length and positioning.\n");
        sb.append("\t * \n");
        sb.append("\t * @param pData the ByteBuffer containing the message data (little-endian)\n");
        sb.append("\t */\n");
        sb.append("\tpublic void parse(ByteBuffer pData) {\n");
        sb.append("\t\tint tInitialPosition = pData.position();\n");
        sb.append("\t\tint tLength = BitUtils.byteArrayToUInt16(pData, false);\n");
        sb.append("\t\tif (tLength == 0) {\n");
        sb.append("\t\t\treturn;\n");
        sb.append("\t\t}\n");
        sb.append("\t\t\n");
        sb.append("\t\teventType = (char)pData.get();\n");
        sb.append("\t\t\n");
        sb.append("\t\tswitch (eventType) {\n");
        
        // Generate switch cases
        for (MessageDefinition msg : messages) {
            String varName = toVariableName(msg.getName());
            String msgType = msg.getMessageType();
            
            sb.append("\t\tcase (char)").append(msgType).append(": {\n");
            sb.append("\t\t\t").append(msg.getName()).append(".parse(").append(varName).append(", pData);\n");
            sb.append("\t\t\tbreak;\n");
            sb.append("\t\t}\n");
        }
        
        sb.append("\t\tdefault:\n");
        sb.append("\t\t\tSystem.out.println(\"Unknown message type: \" + eventType + \" (0x\" + Integer.toHexString(eventType) + \"), length: \" + tLength);\n");
        sb.append("\t\t}\n");
        sb.append("\t\t\n");
        sb.append("\t\t// Handle message length changes\n");
        sb.append("\t\tpData.position(tInitialPosition + tLength);\n");
        sb.append("\t}\n");
        sb.append("}\n");
        
        File outputFile = new File(outputDir, "MitchEventWrapper.java");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(sb.toString());
        }
    }
    
    private String toVariableName(String className) {
        String name = className.replace("Mitch", "");
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }
    
    private void generateCSharpWrapper(List<MessageDefinition> messages, String outputDir) throws IOException {
        StringBuilder sb = new StringBuilder();
        
        sb.append("using System;\n");
        sb.append("using System.IO;\n\n");
        
        sb.append("namespace A5X.Trading.Mitch\n");
        sb.append("{\n");
        sb.append("    /// <summary>\n");
        sb.append("    /// Wrapper class for parsing and dispatching MITCH protocol messages.\n");
        sb.append("    /// </summary>\n");
        sb.append("    public class MitchEventWrapper\n");
        sb.append("    {\n");
        sb.append("        public char EventType { get; set; }\n");
        
        for (MessageDefinition msg : messages) {
            String varName = toVariableName(msg.getName());
            sb.append("        public ").append(msg.getName()).append(" ");
            sb.append(Character.toUpperCase(varName.charAt(0))).append(varName.substring(1));
            sb.append(" { get; set; } = new ").append(msg.getName()).append("();\n");
        }
        
        sb.append("\n        public void Parse(Span<byte> data)\n");
        sb.append("        {\n");
        sb.append("            int offset = 0;\n");
        sb.append("            int initialPosition = offset;\n");
        sb.append("            int length = BitConverter.ToUInt16(data.Slice(offset, 2));\n");
        sb.append("            offset += 2;\n");
        sb.append("            if (length == 0) return;\n\n");
        sb.append("            EventType = (char)data[offset];\n");
        sb.append("            offset += 1;\n\n");
        sb.append("            switch (EventType)\n");
        sb.append("            {\n");
        
        for (MessageDefinition msg : messages) {
            String varName = toVariableName(msg.getName());
            String propName = Character.toUpperCase(varName.charAt(0)) + varName.substring(1);
            sb.append("                case (char)").append(msg.getMessageType()).append(":\n");
            sb.append("                    ").append(msg.getName()).append(".Parse(").append(propName).append(", data.Slice(offset));\n");
            sb.append("                    break;\n");
        }
        
        sb.append("                default:\n");
        sb.append("                    Console.WriteLine($\"Unknown message type: {EventType} (0x{(int)EventType:X}), length: {length}\");\n");
        sb.append("                    break;\n");
        sb.append("            }\n");
        sb.append("        }\n");
        sb.append("    }\n");
        sb.append("}\n");
        
        File outputFile = new File(outputDir, "MitchEventWrapper.cs");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(sb.toString());
        }
    }
    
    private void generateCppWrapper(List<MessageDefinition> messages, String outputDir) throws IOException {
        StringBuilder sb = new StringBuilder();
        
        sb.append("#ifndef MITCH_EVENT_WRAPPER_H\n");
        sb.append("#define MITCH_EVENT_WRAPPER_H\n\n");
        sb.append("#include <cstdint>\n");
        sb.append("#include <cstring>\n");
        sb.append("#include <iostream>\n");
        
        for (MessageDefinition msg : messages) {
            sb.append("#include \"").append(msg.getName()).append(".h\"\n");
        }
        
        sb.append("\nnamespace a5x {\n");
        sb.append("namespace mitch {\n\n");
        
        sb.append("class MitchEventWrapper {\n");
        sb.append("public:\n");
        sb.append("    char eventType;\n");
        
        for (MessageDefinition msg : messages) {
            String varName = toVariableName(msg.getName());
            sb.append("    ").append(msg.getName()).append(" ").append(varName).append(";\n");
        }
        
        sb.append("\n    void parse(const uint8_t* data, size_t length) {\n");
        sb.append("        size_t offset = 0;\n");
        sb.append("        size_t initialPosition = offset;\n");
        sb.append("        uint16_t msgLength;\n");
        sb.append("        std::memcpy(&msgLength, data + offset, 2);\n");
        sb.append("        offset += 2;\n");
        sb.append("        if (msgLength == 0) return;\n\n");
        sb.append("        eventType = static_cast<char>(data[offset]);\n");
        sb.append("        offset += 1;\n\n");
        sb.append("        switch (eventType) {\n");
        
        for (MessageDefinition msg : messages) {
            String varName = toVariableName(msg.getName());
            sb.append("            case ").append(msg.getMessageType()).append(":\n");
            sb.append("                ").append(msg.getName()).append("::parse(&").append(varName).append(", data + offset);\n");
            sb.append("                break;\n");
        }
        
        sb.append("            default:\n");
        sb.append("                std::cout << \"Unknown message type: \" << eventType << \" (0x\" << std::hex << (int)eventType << \"), length: \" << msgLength << std::endl;\n");
        sb.append("                break;\n");
        sb.append("        }\n");
        sb.append("    }\n");
        sb.append("};\n\n");
        
        sb.append("}  // namespace mitch\n");
        sb.append("}  // namespace a5x\n\n");
        sb.append("#endif  // MITCH_EVENT_WRAPPER_H\n");
        
        File outputFile = new File(outputDir, "MitchEventWrapper.h");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(sb.toString());
        }
    }
}
