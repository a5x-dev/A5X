package br.com.a5x.trd.codegen.mitch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import br.com.a5x.trd.codegen.model.FieldDefinition;
import br.com.a5x.trd.codegen.model.MessageDefinition;

public class MitchCSharpGenerator {
    
    public void generate(List<MessageDefinition> messages, String outputDir) throws IOException {
        File dir = new File(outputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        for (MessageDefinition message : messages) {
            generateClass(message, outputDir);
        }
    }
    
    private void generateClass(MessageDefinition message, String outputDir) throws IOException {
        StringBuilder sb = new StringBuilder();
        
        sb.append("using System;\n");
        sb.append("using System.Numerics;\n");
        sb.append("using System.Text;\n\n");
        sb.append("namespace A5X.Trading.Mitch.Model\n{\n");
        
        if (message.getDescription() != null && !message.getDescription().isEmpty()) {
            sb.append("    /// <summary>\n");
            sb.append("    /// ").append(message.getDescription()).append("\n");
            sb.append("    /// </summary>\n");
        }
        
        sb.append("    public class ").append(message.getName()).append("\n    {\n");
        
        // Fields
        for (FieldDefinition field : message.getFields()) {
            sb.append("        /// <summary>\n");
            sb.append("        /// ").append(field.getDescription()).append("\n");
            sb.append("        /// </summary>\n");
            sb.append("        public ").append(getCSharpType(field)).append(" ").append(capitalize(field.getName()));
            
            if ("bytearray".equals(field.getType())) {
                sb.append(" = new byte[").append(field.getLength()).append("]");
            }
            
            sb.append(" { get; set; }\n\n");

            if ("price4".equals(field.getType()) || "price8".equals(field.getType())) {
                sb.append("        public decimal ").append(capitalize(field.getName())).append("AsDecimal => ").append(capitalize(field.getName())).append(" / 10000m;\n\n");
            }
        }
        
        // Parse method
        sb.append("        public static void Parse(").append(message.getName()).append(" msg, Span<byte> data)\n");
        sb.append("        {\n");
        sb.append("            int offset = 0;\n");
        
        for (FieldDefinition field : message.getFields()) {
            sb.append(getCSharpParseCode(field));
        }
        
        sb.append("        }\n\n");
        
        // Encode method
        sb.append("        public static void Encode(").append(message.getName()).append(" msg, Span<byte> data)\n");
        sb.append("        {\n");
        sb.append("            int offset = 0;\n");
        
        for (FieldDefinition field : message.getFields()) {
            sb.append(getCSharpEncodeCode(field));
        }
        
        sb.append("        }\n");
        sb.append("    }\n");
        sb.append("}\n");
        
        File outputFile = new File(outputDir, message.getName() + ".cs");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(sb.toString());
        }
    }
    
    private String getCSharpType(FieldDefinition field) {
        switch (field.getType()) {
            case "uint32": return "uint";
            case "uint64": return "BigInteger";
            case "uint8": return "byte";
            case "char": return "char";
            case "byte": return "byte";
            case "bytearray": return "byte[]";
            case "string": return "string";
            case "alpha": return "string";
            case "price4": return "int";
            case "price8": return "long";
            default: return "object";
        }
    }
    
    private String getCSharpParseCode(FieldDefinition field) {
        StringBuilder sb = new StringBuilder();
        String fieldName = capitalize(field.getName());
        
        switch (field.getType()) {
            case "uint32":
                sb.append("            msg.").append(fieldName).append(" = BitConverter.ToUInt32(data.Slice(offset, 4));\n");
                sb.append("            offset += 4;\n");
                break;
            case "uint64":
                sb.append("            msg.").append(fieldName).append(" = new BigInteger(data.Slice(offset, 8));\n");
                sb.append("            offset += 8;\n");
                break;
            case "uint8":
                sb.append("            msg.").append(fieldName).append(" = data[offset++];\n");
                break;
            case "char":
                sb.append("            msg.").append(fieldName).append(" = (char)data[offset++];\n");
                break;
            case "byte":
                sb.append("            msg.").append(fieldName).append(" = data[offset++];\n");
                break;
            case "bytearray":
                sb.append("            data.Slice(offset, ").append(field.getLength()).append(").CopyTo(msg.").append(fieldName).append(");\n");
                sb.append("            offset += ").append(field.getLength()).append(";\n");
                break;
            case "string":
            case "alpha":
                sb.append("            msg.").append(fieldName).append(" = Encoding.ASCII.GetString(data.Slice(offset, ").append(field.getLength()).append(")).TrimEnd();\n");
                sb.append("            offset += ").append(field.getLength()).append(";\n");
                break;
            case "price4":
                sb.append("            msg.").append(fieldName).append(" = BitConverter.ToInt32(data.Slice(offset, 4));\n");
                sb.append("            offset += 4;\n");
                break;
            case "price8":
                sb.append("            msg.").append(fieldName).append(" = BitConverter.ToInt64(data.Slice(offset, 8));\n");
                sb.append("            offset += 8;\n");
                break;
        }
        
        return sb.toString();
    }
    
    private String getCSharpEncodeCode(FieldDefinition field) {
        StringBuilder sb = new StringBuilder();
        String fieldName = capitalize(field.getName());
        
        switch (field.getType()) {
            case "uint32":
                sb.append("            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.").append(fieldName).append(");\n");
                sb.append("            offset += 4;\n");
                break;
            case "uint64":
                sb.append("            msg.").append(fieldName).append(".TryWriteBytes(data.Slice(offset, 8), out _);\n");
                sb.append("            offset += 8;\n");
                break;
            case "uint8":
            case "char":
            case "byte":
                sb.append("            data[offset++] = (byte)msg.").append(fieldName).append(";\n");
                break;
            case "bytearray":
                sb.append("            msg.").append(fieldName).append(".CopyTo(data.Slice(offset, ").append(field.getLength()).append("));\n");
                sb.append("            offset += ").append(field.getLength()).append(";\n");
                break;
            case "string":
            case "alpha":
                sb.append("            var ").append(field.getName()).append("Bytes = Encoding.ASCII.GetBytes(msg.").append(fieldName).append(".PadRight(").append(field.getLength()).append("));\n");
                sb.append("            ").append(field.getName()).append("Bytes.AsSpan(0, ").append(field.getLength()).append(").CopyTo(data.Slice(offset, ").append(field.getLength()).append("));\n");
                sb.append("            offset += ").append(field.getLength()).append(";\n");
                break;
            case "price4":
                sb.append("            BitConverter.TryWriteBytes(data.Slice(offset, 4), msg.").append(fieldName).append(");\n");
                sb.append("            offset += 4;\n");
                break;
            case "price8":
                sb.append("            BitConverter.TryWriteBytes(data.Slice(offset, 8), msg.").append(fieldName).append(");\n");
                sb.append("            offset += 8;\n");
                break;
        }
        
        return sb.toString();
    }
    
    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
