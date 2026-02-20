package br.com.a5x.trd.codegen.nativ;

import br.com.a5x.trd.codegen.model.MessageDefinition;
import br.com.a5x.trd.codegen.model.FieldDefinition;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class NativeCppGenerator {

    public void generate(List<MessageDefinition> messages, String outputDir) throws IOException {
        File dir = new File(outputDir);
        if (!dir.exists()) dir.mkdirs();

        for (MessageDefinition msg : messages) {
            generateMessage(msg, outputDir);
        }
    }

    private void generateMessage(MessageDefinition msg, String outputDir) throws IOException {
        StringBuilder sb = new StringBuilder();
        
        sb.append("#pragma once\n\n");
        sb.append("#include <cstdint>\n");
        sb.append("#include <cstring>\n\n");
        sb.append("namespace a5x::trading::native {\n\n");
        sb.append("/**\n");
        sb.append(" * ").append(msg.getDescription()).append("\n");
        sb.append(" * Message Type: ").append(msg.getMessageType()).append("\n");
        sb.append(" */\n");
        sb.append("struct ").append(msg.getName()).append(" {\n");
        
        for (FieldDefinition field : msg.getFields()) {
            sb.append("    /** ").append(field.getDescription()).append(" */\n");
            sb.append("    ").append(getCppType(field)).append(" ").append(field.getName());
            if (field.getType().equals("alpha")) {
                sb.append("[").append(field.getLength()).append("]");
            }
            sb.append(";\n\n");
            if ("price".equals(field.getType())) {
                sb.append("    double ").append(field.getName()).append("AsDecimal() const { return ").append(field.getName()).append(" / 10000.0; }\n\n");
            }
        }
        
        sb.append("    static void parse(").append(msg.getName()).append("& msg, const uint8_t* data) {\n");
        int offset = 0;
        for (FieldDefinition field : msg.getFields()) {
            sb.append("        ").append(getParseCode(field, offset)).append(";\n");
            offset += getFieldSize(field);
        }
        sb.append("    }\n\n");
        
        sb.append("    static void encode(const ").append(msg.getName()).append("& msg, uint8_t* data) {\n");
        offset = 0;
        for (FieldDefinition field : msg.getFields()) {
            sb.append("        ").append(getEncodeCode(field, offset)).append(";\n");
            offset += getFieldSize(field);
        }
        sb.append("    }\n");
        
        sb.append("};\n\n");
        sb.append("} // namespace a5x::trading::native\n");
        
        File file = new File(outputDir, msg.getName() + ".hpp");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(sb.toString());
        }
    }

    private String getCppType(FieldDefinition field) {
        switch (field.getType()) {
            case "alpha": return "uint8_t";
            case "byte": return "uint8_t";
            case "price": return "int32_t";
            case "uint8": return "uint8_t";
            case "uint16": return "uint16_t";
            case "uint32": return "uint32_t";
            case "uint64": return "uint64_t";
            case "int32": return "int32_t";
            case "bitfield": return "uint8_t";
            default: return "uint8_t";
        }
    }

    private String getParseCode(FieldDefinition field, int offset) {
        switch (field.getType()) {
            case "alpha":
                return "std::memcpy(msg." + field.getName() + ", data + " + offset + ", " + field.getLength() + ")";
            case "byte":
            case "uint8":
            case "bitfield":
                return "msg." + field.getName() + " = data[" + offset + "]";
            case "price":
            case "int32":
                return "std::memcpy(&msg." + field.getName() + ", data + " + offset + ", 4)";
            case "uint16":
                return "std::memcpy(&msg." + field.getName() + ", data + " + offset + ", 2)";
            case "uint32":
                return "std::memcpy(&msg." + field.getName() + ", data + " + offset + ", 4)";
            case "uint64":
                return "std::memcpy(&msg." + field.getName() + ", data + " + offset + ", 8)";
            default:
                return "msg." + field.getName() + " = data[" + offset + "]";
        }
    }

    private String getEncodeCode(FieldDefinition field, int offset) {
        switch (field.getType()) {
            case "alpha":
                return "std::memcpy(data + " + offset + ", msg." + field.getName() + ", " + field.getLength() + ")";
            case "byte":
            case "uint8":
            case "bitfield":
                return "data[" + offset + "] = msg." + field.getName();
            case "price":
            case "int32":
                return "std::memcpy(data + " + offset + ", &msg." + field.getName() + ", 4)";
            case "uint16":
                return "std::memcpy(data + " + offset + ", &msg." + field.getName() + ", 2)";
            case "uint32":
                return "std::memcpy(data + " + offset + ", &msg." + field.getName() + ", 4)";
            case "uint64":
                return "std::memcpy(data + " + offset + ", &msg." + field.getName() + ", 8)";
            default:
                return "data[" + offset + "] = msg." + field.getName();
        }
    }

    private int getFieldSize(FieldDefinition field) {
        switch (field.getType()) {
            case "alpha": return field.getLength();
            case "byte":
            case "uint8":
            case "bitfield": return 1;
            case "uint16": return 2;
            case "price":
            case "int32":
            case "uint32": return 4;
            case "uint64": return 8;
            default: return 1;
        }
    }
}
