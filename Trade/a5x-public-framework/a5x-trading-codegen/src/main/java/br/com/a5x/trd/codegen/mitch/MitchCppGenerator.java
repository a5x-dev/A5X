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
        sb.append("#include <algorithm>\n");
        sb.append("#include <cstdint>\n");
        sb.append("#include <cstring>\n");
        sb.append("#include <string>\n\n");

        // MITCH is little-endian on the wire. memcpy copies in *host* order, which happens
        // to match on x86 and silently does not on a big-endian target. These assemble the
        // value byte by byte, so the result never depends on the host.
        // Every header carries the helpers, so they are guarded: including two headers in
        // one translation unit would otherwise define them twice.
        sb.append("#ifndef A5X_MITCH_WIRE_HELPERS\n");
        sb.append("#define A5X_MITCH_WIRE_HELPERS\n");
        sb.append("namespace {\n");
        sb.append("inline uint16_t a5x_read_le16(const uint8_t* p) { uint16_t v = 0; for (int i = 1; i >= 0; --i) v = static_cast<uint16_t>((v << 8) | p[i]); return v; }\n");
        sb.append("inline uint32_t a5x_read_le32(const uint8_t* p) { uint32_t v = 0; for (int i = 3; i >= 0; --i) v = (v << 8) | p[i]; return v; }\n");
        sb.append("inline uint64_t a5x_read_le64(const uint8_t* p) { uint64_t v = 0; for (int i = 7; i >= 0; --i) v = (v << 8) | p[i]; return v; }\n");
        sb.append("inline void a5x_write_le16(uint8_t* p, uint16_t v) { for (int i = 0; i < 2; ++i) p[i] = static_cast<uint8_t>(v >> (8 * i)); }\n");
        sb.append("inline void a5x_write_le32(uint8_t* p, uint32_t v) { for (int i = 0; i < 4; ++i) p[i] = static_cast<uint8_t>(v >> (8 * i)); }\n");
        sb.append("inline void a5x_write_le64(uint8_t* p, uint64_t v) { for (int i = 0; i < 8; ++i) p[i] = static_cast<uint8_t>(v >> (8 * i)); }\n");
        // Sign-magnitude: the top bit is the sign, the rest the absolute value. This is how
        // MITCH sends a negative price; it is not two's complement.
        sb.append("inline int32_t a5x_read_sm32(const uint8_t* p) { uint32_t v = a5x_read_le32(p); int32_t m = static_cast<int32_t>(v & 0x7FFFFFFFu); return (v >> 31) ? -m : m; }\n");
        sb.append("inline int64_t a5x_read_sm64(const uint8_t* p) { uint64_t v = a5x_read_le64(p); int64_t m = static_cast<int64_t>(v & 0x7FFFFFFFFFFFFFFFull); return (v >> 63) ? -m : m; }\n");
        sb.append("inline void a5x_write_sm32(uint8_t* p, int32_t v) { a5x_write_le32(p, v < 0 ? (static_cast<uint32_t>(-static_cast<int64_t>(v)) | 0x80000000u) : static_cast<uint32_t>(v)); }\n");
        sb.append("inline void a5x_write_sm64(uint8_t* p, int64_t v) { a5x_write_le64(p, v < 0 ? ((~static_cast<uint64_t>(v) + 1) | 0x8000000000000000ull) : static_cast<uint64_t>(v)); }\n");
        sb.append("} // namespace\n");
        sb.append("#endif // A5X_MITCH_WIRE_HELPERS\n\n");
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
                int scale = "price8".equals(field.getType()) ? 100000000 : 10000;
                sb.append("    double ").append(field.getName()).append("AsDecimal() const { return ").append(field.getName()).append(" / ").append(scale).append(".0; }\n\n");
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
            case "uint16": return "uint16_t";
            case "int32": return "int32_t";
            case "byte": return "uint8_t";
            case "bitfield": return "uint8_t";
            case "bytearray": return "uint8_t";
            case "alpha": return "std::string";
            case "price4": return "int32_t";
            case "price8": return "int64_t";
            case "date": return "std::string";
            case "time": return "std::string";
            case "extendedtime": return "std::string";
            case "datetime": return "std::string";
            default: return "void*";
        }
    }
    
    private String getCppParseCode(FieldDefinition field) {
        StringBuilder sb = new StringBuilder();
        
        switch (field.getType()) {
            case "uint32":
                sb.append("        msg.").append(field.getName()).append(" = a5x_read_le32(data + offset);\n");
                sb.append("        offset += 4;\n");
                break;
            case "uint64":
                sb.append("        msg.").append(field.getName()).append(" = a5x_read_le64(data + offset);\n");
                sb.append("        offset += 8;\n");
                break;
            case "uint16":
                sb.append("        msg.").append(field.getName()).append(" = a5x_read_le16(data + offset);\n");
                sb.append("        offset += 2;\n");
                break;
            case "int32":
                sb.append("        msg.").append(field.getName()).append(" = a5x_read_le32(data + offset);\n");
                sb.append("        offset += 4;\n");
                break;
            case "uint8":
            case "byte":
            case "bitfield":
                sb.append("        msg.").append(field.getName()).append(" = data[offset++];\n");
                break;
            case "bytearray":
                sb.append("        std::memcpy(msg.").append(field.getName()).append(", data + offset, ").append(field.getLength()).append(");\n");
                sb.append("        offset += ").append(field.getLength()).append(";\n");
                break;
            case "alpha":
            case "date":
            case "time":
            case "extendedtime":
            case "datetime":
                sb.append("        msg.").append(field.getName()).append(" = std::string(reinterpret_cast<const char*>(data + offset), ").append(field.getLength()).append(");\n");
                sb.append("        msg.").append(field.getName()).append(".erase(msg.").append(field.getName()).append(".find_last_not_of(' ') + 1);\n");
                sb.append("        offset += ").append(field.getLength()).append(";\n");
                break;
            case "price4":
                sb.append("        msg.").append(field.getName()).append(field.isSignMagnitude() ? " = a5x_read_sm32(" : " = a5x_read_le32(").append("data + offset);\n");
                sb.append("        offset += 4;\n");
                break;
            case "price8":
                sb.append("        msg.").append(field.getName()).append(field.isSignMagnitude() ? " = a5x_read_sm64(" : " = a5x_read_le64(").append("data + offset);\n");
                sb.append("        offset += 8;\n");
                break;
        }
        
        return sb.toString();
    }
    
    private String getCppEncodeCode(FieldDefinition field) {
        StringBuilder sb = new StringBuilder();
        
        switch (field.getType()) {
            case "uint32":
                sb.append("        a5x_write_le32(data + offset, msg.").append(field.getName()).append(");\n");
                sb.append("        offset += 4;\n");
                break;
            case "uint64":
                sb.append("        a5x_write_le64(data + offset, msg.").append(field.getName()).append(");\n");
                sb.append("        offset += 8;\n");
                break;
            case "uint16":
                sb.append("        a5x_write_le16(data + offset, msg.").append(field.getName()).append(");\n");
                sb.append("        offset += 2;\n");
                break;
            case "int32":
                sb.append("        a5x_write_le32(data + offset, msg.").append(field.getName()).append(");\n");
                sb.append("        offset += 4;\n");
                break;
            case "uint8":
            case "byte":
            case "bitfield":
                sb.append("        data[offset++] = msg.").append(field.getName()).append(";\n");
                break;
            case "bytearray":
                sb.append("        std::memcpy(data + offset, msg.").append(field.getName()).append(", ").append(field.getLength()).append(");\n");
                sb.append("        offset += ").append(field.getLength()).append(";\n");
                break;
            case "alpha":
            case "date":
            case "time":
            case "extendedtime":
            case "datetime":
                sb.append("        std::memset(data + offset, ' ', ").append(field.getLength()).append(");\n");
                sb.append("        std::memcpy(data + offset, msg.").append(field.getName()).append(".c_str(), std::min(msg.").append(field.getName()).append(".size(), size_t(").append(field.getLength()).append(")));\n");
                sb.append("        offset += ").append(field.getLength()).append(";\n");
                break;
            case "price4":
                sb.append(field.isSignMagnitude() ? "        a5x_write_sm32(" : "        a5x_write_le32(").append("data + offset, msg.").append(field.getName()).append(");\n");
                sb.append("        offset += 4;\n");
                break;
            case "price8":
                sb.append(field.isSignMagnitude() ? "        a5x_write_sm64(" : "        a5x_write_le64(").append("data + offset, msg.").append(field.getName()).append(");\n");
                sb.append("        offset += 8;\n");
                break;
        }
        
        return sb.toString();
    }
}
