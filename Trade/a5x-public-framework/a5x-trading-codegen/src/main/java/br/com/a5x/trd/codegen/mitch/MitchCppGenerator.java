package br.com.a5x.trd.codegen.mitch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import br.com.a5x.trd.codegen.model.FieldDefinition;
import br.com.a5x.trd.codegen.model.MessageDefinition;

public class MitchCppGenerator {
    
    public void generate(List<MessageDefinition> messages, String outputDir) throws IOException {
        File dir = new File(outputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        for (MessageDefinition message : messages) {
            generateHeader(message, outputDir);
        }
    }
    
    private void generateHeader(MessageDefinition message, String outputDir) throws IOException {
        StringBuilder sb = new StringBuilder();
        
        String guard = message.getName().toUpperCase() + "_H";
        
        sb.append("#ifndef ").append(guard).append("\n");
        sb.append("#define ").append(guard).append("\n\n");
        sb.append("#include <cstdint>\n");
        sb.append("#include <cstring>\n");
        sb.append("#include <string>\n\n");
        sb.append("namespace a5x::trading::mitch {\n\n");
        
        if (message.getDescription() != null && !message.getDescription().isEmpty()) {
            sb.append("/// ").append(message.getDescription()).append("\n");
        }
        
        sb.append("struct ").append(message.getName()).append(" {\n");
        
        // Fields
        for (FieldDefinition field : message.getFields()) {
            sb.append("    /// ").append(field.getDescription()).append("\n");
            sb.append("    ").append(getCppType(field)).append(" ").append(field.getName());
            
            if ("bytearray".equals(field.getType())) {
                sb.append("[").append(field.getLength()).append("]");
            }
            
            sb.append(";\n\n");

            if ("price4".equals(field.getType()) || "price8".equals(field.getType())) {
                sb.append("    double ").append(field.getName()).append("AsDecimal() const { return ").append(field.getName()).append(" / 10000.0; }\n\n");
            }
        }
        
        // Parse method
        sb.append("    static void parse(").append(message.getName()).append("& msg, const uint8_t* data) {\n");
        sb.append("        size_t offset = 0;\n");
        
        for (FieldDefinition field : message.getFields()) {
            sb.append(getCppParseCode(field));
        }
        
        sb.append("    }\n\n");
        
        // Encode method
        sb.append("    static void encode(const ").append(message.getName()).append("& msg, uint8_t* data) {\n");
        sb.append("        size_t offset = 0;\n");
        
        for (FieldDefinition field : message.getFields()) {
            sb.append(getCppEncodeCode(field));
        }
        
        sb.append("    }\n");
        sb.append("};\n\n");
        sb.append("} // namespace a5x::trading::mitch\n\n");
        sb.append("#endif // ").append(guard).append("\n");
        
        File outputFile = new File(outputDir, message.getName() + ".h");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(sb.toString());
        }
    }
    
    private String getCppType(FieldDefinition field) {
        switch (field.getType()) {
            case "uint32": return "uint32_t";
            case "uint64": return "uint64_t";
            case "uint8": return "uint8_t";
            case "char": return "char";
            case "byte": return "uint8_t";
            case "bytearray": return "uint8_t";
            case "string": return "std::string";
            case "alpha": return "std::string";
            case "price4": return "int32_t";
            case "price8": return "int64_t";
            default: return "void*";
        }
    }
    
    private String getCppParseCode(FieldDefinition field) {
        StringBuilder sb = new StringBuilder();
        
        switch (field.getType()) {
            case "uint32":
                sb.append("        std::memcpy(&msg.").append(field.getName()).append(", data + offset, 4);\n");
                sb.append("        offset += 4;\n");
                break;
            case "uint64":
                sb.append("        std::memcpy(&msg.").append(field.getName()).append(", data + offset, 8);\n");
                sb.append("        offset += 8;\n");
                break;
            case "uint8":
            case "char":
            case "byte":
                sb.append("        msg.").append(field.getName()).append(" = data[offset++];\n");
                break;
            case "bytearray":
                sb.append("        std::memcpy(msg.").append(field.getName()).append(", data + offset, ").append(field.getLength()).append(");\n");
                sb.append("        offset += ").append(field.getLength()).append(";\n");
                break;
            case "string":
            case "alpha":
                sb.append("        msg.").append(field.getName()).append(" = std::string(reinterpret_cast<const char*>(data + offset), ").append(field.getLength()).append(");\n");
                sb.append("        msg.").append(field.getName()).append(".erase(msg.").append(field.getName()).append(".find_last_not_of(' ') + 1);\n");
                sb.append("        offset += ").append(field.getLength()).append(";\n");
                break;
            case "price4":
                sb.append("        std::memcpy(&msg.").append(field.getName()).append(", data + offset, 4);\n");
                sb.append("        offset += 4;\n");
                break;
            case "price8":
                sb.append("        std::memcpy(&msg.").append(field.getName()).append(", data + offset, 8);\n");
                sb.append("        offset += 8;\n");
                break;
        }
        
        return sb.toString();
    }
    
    private String getCppEncodeCode(FieldDefinition field) {
        StringBuilder sb = new StringBuilder();
        
        switch (field.getType()) {
            case "uint32":
                sb.append("        std::memcpy(data + offset, &msg.").append(field.getName()).append(", 4);\n");
                sb.append("        offset += 4;\n");
                break;
            case "uint64":
                sb.append("        std::memcpy(data + offset, &msg.").append(field.getName()).append(", 8);\n");
                sb.append("        offset += 8;\n");
                break;
            case "uint8":
            case "char":
            case "byte":
                sb.append("        data[offset++] = msg.").append(field.getName()).append(";\n");
                break;
            case "bytearray":
                sb.append("        std::memcpy(data + offset, msg.").append(field.getName()).append(", ").append(field.getLength()).append(");\n");
                sb.append("        offset += ").append(field.getLength()).append(";\n");
                break;
            case "string":
            case "alpha":
                sb.append("        std::memset(data + offset, ' ', ").append(field.getLength()).append(");\n");
                sb.append("        std::memcpy(data + offset, msg.").append(field.getName()).append(".c_str(), std::min(msg.").append(field.getName()).append(".size(), size_t(").append(field.getLength()).append(")));\n");
                sb.append("        offset += ").append(field.getLength()).append(";\n");
                break;
            case "price4":
                sb.append("        std::memcpy(data + offset, &msg.").append(field.getName()).append(", 4);\n");
                sb.append("        offset += 4;\n");
                break;
            case "price8":
                sb.append("        std::memcpy(data + offset, &msg.").append(field.getName()).append(", 8);\n");
                sb.append("        offset += 8;\n");
                break;
        }
        
        return sb.toString();
    }
}
