package br.com.a5x.trd.codegen.nativ;

import br.com.a5x.trd.codegen.model.MessageDefinition;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class NativeWrapperGenerator {

    public void generate(List<MessageDefinition> messages, String outputDir) throws IOException {
        File dir = new File(outputDir);
        if (!dir.exists()) dir.mkdirs();

        StringBuilder sb = new StringBuilder();
        
        sb.append("package br.com.a5x.trd.pub.nativ;\n\n");
        sb.append("import br.com.a5x.trd.pub.nativ.model.*;\n");
        sb.append("import java.nio.ByteBuffer;\n\n");
        
        sb.append("/**\n");
        sb.append(" * Native protocol message dispatcher.\n");
        sb.append(" * Parses message header and routes to appropriate message parser.\n");
        sb.append(" */\n");
        sb.append("public class NativeEventWrapper {\n\n");
        
        sb.append("    public static final byte START_OF_MESSAGE = 0x02;\n\n");
        
        sb.append("    public interface INativePackageHandler {\n");
        for (MessageDefinition msg : messages) {
            sb.append("        void on").append(msg.getName()).append("(").append(msg.getName()).append(" msg);\n");
        }
        sb.append("    }\n\n");
        
        sb.append("    public static void parse(ByteBuffer data, INativePackageHandler handler) {\n");
        sb.append("        byte startOfMessage = data.get();\n");
        sb.append("        if (startOfMessage != START_OF_MESSAGE) {\n");
        sb.append("            throw new IllegalArgumentException(\"Invalid start of message: \" + startOfMessage);\n");
        sb.append("        }\n\n");
        sb.append("        int length = Short.toUnsignedInt(data.getShort());\n");
        sb.append("        byte messageType = data.get();\n\n");
        sb.append("        switch (messageType) {\n");
        
        for (MessageDefinition msg : messages) {
            String msgType = msg.getMessageType();
            if (msgType.startsWith("0x")) {
                sb.append("            case ").append(msgType).append(":\n");
            } else {
                sb.append("            case '").append(msgType.replace("0x", "")).append("':\n");
            }
            sb.append("                ").append(msg.getName()).append(" ").append(toLowerFirst(msg.getName())).append(" = new ").append(msg.getName()).append("();\n");
            sb.append("                ").append(msg.getName()).append(".parse(").append(toLowerFirst(msg.getName())).append(", data);\n");
            sb.append("                handler.on").append(msg.getName()).append("(").append(toLowerFirst(msg.getName())).append(");\n");
            sb.append("                break;\n");
        }
        
        sb.append("            default:\n");
        sb.append("                throw new IllegalArgumentException(\"Unknown message type: \" + (char) messageType);\n");
        sb.append("        }\n");
        sb.append("    }\n\n");
        
        sb.append("    public static void encode(ByteBuffer data, Object msg) {\n");
        sb.append("        data.put(START_OF_MESSAGE);\n");
        sb.append("        int lengthPos = data.position();\n");
        sb.append("        data.putShort((short) 0);\n");
        sb.append("        int startPos = data.position();\n\n");
        
        for (int i = 0; i < messages.size(); i++) {
            MessageDefinition msg = messages.get(i);
            sb.append("        ").append(i > 0 ? "} else " : "").append("if (msg instanceof ").append(msg.getName()).append(") {\n");
            String msgType = msg.getMessageType();
            if (msgType.startsWith("0x")) {
                sb.append("            data.put((byte) ").append(msgType).append(");\n");
            } else {
                sb.append("            data.put((byte) '").append(msgType.replace("0x", "")).append("');\n");
            }
            sb.append("            ").append(msg.getName()).append(".encode((").append(msg.getName()).append(") msg, data);\n");
        }
        sb.append("        } else {\n");
        sb.append("            throw new IllegalArgumentException(\"Unknown message type: \" + msg.getClass().getName());\n");
        sb.append("        }\n\n");
        
        sb.append("        int endPos = data.position();\n");
        sb.append("        data.putShort(lengthPos, (short) (endPos - startPos));\n");
        sb.append("    }\n");
        sb.append("}\n");
        
        File file = new File(outputDir, "NativeEventWrapper.java");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(sb.toString());
        }
    }

    public void generateCSharp(List<MessageDefinition> messages, String outputDir) throws IOException {
        File dir = new File(outputDir);
        if (!dir.exists()) dir.mkdirs();

        StringBuilder sb = new StringBuilder();
        
        sb.append("using System;\n\n");
        sb.append("namespace A5X.Trading.Native\n{\n");
        sb.append("    public interface INativePackageHandler\n    {\n");
        for (MessageDefinition msg : messages) {
            sb.append("        void On").append(msg.getName()).append("(").append(msg.getName()).append(" msg);\n");
        }
        sb.append("    }\n\n");
        
        sb.append("    public static class NativeEventWrapper\n    {\n");
        sb.append("        public const byte START_OF_MESSAGE = 0x02;\n\n");
        
        sb.append("        public static void Parse(Span<byte> data, INativePackageHandler handler)\n        {\n");
        sb.append("            if (data[0] != START_OF_MESSAGE)\n");
        sb.append("                throw new ArgumentException($\"Invalid start of message: {data[0]}\");\n\n");
        sb.append("            ushort length = BitConverter.ToUInt16(data.Slice(1, 2));\n");
        sb.append("            byte messageType = data[3];\n\n");
        sb.append("            switch ((char)messageType)\n            {\n");
        
        for (MessageDefinition msg : messages) {
            String msgType = msg.getMessageType();
            sb.append("                case '").append(msgType.replace("0x", "")).append("':\n");
            sb.append("                    var ").append(toLowerFirst(msg.getName())).append(" = new ").append(msg.getName()).append("();\n");
            sb.append("                    ").append(msg.getName()).append(".Parse(").append(toLowerFirst(msg.getName())).append(", data.Slice(4));\n");
            sb.append("                    handler.On").append(msg.getName()).append("(").append(toLowerFirst(msg.getName())).append(");\n");
            sb.append("                    break;\n");
        }
        
        sb.append("                default:\n");
        sb.append("                    throw new ArgumentException($\"Unknown message type: {(char)messageType}\");\n");
        sb.append("            }\n        }\n    }\n}\n");
        
        File file = new File(outputDir, "NativeEventWrapper.cs");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(sb.toString());
        }
    }

    public void generateCpp(List<MessageDefinition> messages, String outputDir) throws IOException {
        File dir = new File(outputDir);
        if (!dir.exists()) dir.mkdirs();

        StringBuilder sb = new StringBuilder();
        
        sb.append("#pragma once\n\n");
        sb.append("#include <cstdint>\n");
        sb.append("#include <stdexcept>\n");
        for (MessageDefinition msg : messages) {
            sb.append("#include \"").append(msg.getName()).append(".hpp\"\n");
        }
        sb.append("\nnamespace a5x::trading::native {\n\n");
        
        sb.append("class INativePackageHandler {\npublic:\n");
        sb.append("    virtual ~INativePackageHandler() = default;\n");
        for (MessageDefinition msg : messages) {
            sb.append("    virtual void on").append(msg.getName()).append("(const ").append(msg.getName()).append("& msg) = 0;\n");
        }
        sb.append("};\n\n");
        
        sb.append("class NativeEventWrapper {\npublic:\n");
        sb.append("    static constexpr uint8_t START_OF_MESSAGE = 0x02;\n\n");
        
        sb.append("    static void parse(const uint8_t* data, INativePackageHandler& handler) {\n");
        sb.append("        if (data[0] != START_OF_MESSAGE)\n");
        sb.append("            throw std::runtime_error(\"Invalid start of message\");\n\n");
        sb.append("        uint16_t length;\n");
        sb.append("        std::memcpy(&length, data + 1, 2);\n");
        sb.append("        uint8_t messageType = data[3];\n\n");
        sb.append("        switch (messageType) {\n");
        
        for (MessageDefinition msg : messages) {
            String msgType = msg.getMessageType();
            sb.append("            case '").append(msgType.replace("0x", "")).append("': {\n");
            sb.append("                ").append(msg.getName()).append(" ").append(toLowerFirst(msg.getName())).append(";\n");
            sb.append("                ").append(msg.getName()).append("::parse(").append(toLowerFirst(msg.getName())).append(", data + 4);\n");
            sb.append("                handler.on").append(msg.getName()).append("(").append(toLowerFirst(msg.getName())).append(");\n");
            sb.append("                break;\n            }\n");
        }
        
        sb.append("            default:\n");
        sb.append("                throw std::runtime_error(\"Unknown message type\");\n");
        sb.append("        }\n    }\n};\n\n");
        sb.append("} // namespace a5x::trading::native\n");
        
        File file = new File(outputDir, "NativeEventWrapper.hpp");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(sb.toString());
        }
    }

    private String toLowerFirst(String str) {
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
}
