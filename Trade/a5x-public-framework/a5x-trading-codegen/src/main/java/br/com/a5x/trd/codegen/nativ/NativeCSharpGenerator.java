package br.com.a5x.trd.codegen.nativ;

import br.com.a5x.trd.codegen.model.MessageDefinition;
import br.com.a5x.trd.codegen.model.FieldDefinition;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class NativeCSharpGenerator {

    public void generate(List<MessageDefinition> messages, String outputDir) throws IOException {
        File dir = new File(outputDir);
        if (!dir.exists()) dir.mkdirs();

        for (MessageDefinition msg : messages) {
            generateMessage(msg, outputDir);
        }
    }

    private void generateMessage(MessageDefinition msg, String outputDir) throws IOException {
        StringBuilder sb = new StringBuilder();
        
        sb.append("using System;\n");
        sb.append("using System.Text;\n\n");
        sb.append("namespace A5X.Trading.Native.Model\n{\n");
        sb.append("    /// <summary>\n");
        sb.append("    /// ").append(msg.getDescription()).append("\n");
        sb.append("    /// Message Type: ").append(msg.getMessageType()).append("\n");
        sb.append("    /// </summary>\n");
        sb.append("    public class ").append(msg.getName()).append("\n    {\n");
        
        for (FieldDefinition field : msg.getFields()) {
            sb.append("        /// <summary>").append(field.getDescription()).append("</summary>\n");
            sb.append("        public ").append(getCSharpType(field)).append(" ").append(capitalize(field.getName())).append(" { get; set; }\n\n");
            if ("price".equals(field.getType())) {
                sb.append("        public decimal ").append(capitalize(field.getName())).append("AsDecimal => ").append(capitalize(field.getName())).append(" / 10000m;\n\n");
            }
        }
        
        sb.append("        public static void Parse(").append(msg.getName()).append(" msg, Span<byte> data)\n        {\n");
        int offset = 0;
        for (FieldDefinition field : msg.getFields()) {
            sb.append("            msg.").append(capitalize(field.getName())).append(" = ");
            sb.append(getParseCode(field, offset)).append(";\n");
            offset += getFieldSize(field);
        }
        sb.append("        }\n\n");
        
        sb.append("        public static void Encode(").append(msg.getName()).append(" msg, Span<byte> data)\n        {\n");
        offset = 0;
        for (FieldDefinition field : msg.getFields()) {
            sb.append("            ").append(getEncodeCode(field, offset)).append(";\n");
            offset += getFieldSize(field);
        }
        sb.append("        }\n");
        
        sb.append("    }\n}\n");
        
        File file = new File(outputDir, msg.getName() + ".cs");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(sb.toString());
        }
    }

    private String getCSharpType(FieldDefinition field) {
        switch (field.getType()) {
            case "alpha": return "byte[]";
            case "byte": return "byte";
            case "price": return "int";
            case "uint8": return "byte";
            case "uint16": return "ushort";
            case "uint32": return "uint";
            case "uint64": return "ulong";
            case "int32": return "int";
            case "bitfield": return "byte";
            default: return "byte[]";
        }
    }

    private String getParseCode(FieldDefinition field, int offset) {
        switch (field.getType()) {
            case "alpha":
                return "data.Slice(" + offset + ", " + field.getLength() + ").ToArray()";
            case "byte":
            case "uint8":
            case "bitfield":
                return "data[" + offset + "]";
            case "price":
            case "int32":
                return "BitConverter.ToInt32(data.Slice(" + offset + ", 4))";
            case "uint16":
                return "BitConverter.ToUInt16(data.Slice(" + offset + ", 2))";
            case "uint32":
                return "BitConverter.ToUInt32(data.Slice(" + offset + ", 4))";
            case "uint64":
                return "BitConverter.ToUInt64(data.Slice(" + offset + ", 8))";
            default:
                return "data[" + offset + "]";
        }
    }

    private String getEncodeCode(FieldDefinition field, int offset) {
        switch (field.getType()) {
            case "alpha":
                return "msg." + capitalize(field.getName()) + ".CopyTo(data.Slice(" + offset + ", " + field.getLength() + "))";
            case "byte":
            case "uint8":
            case "bitfield":
                return "data[" + offset + "] = msg." + capitalize(field.getName());
            case "price":
            case "int32":
                return "BitConverter.TryWriteBytes(data.Slice(" + offset + ", 4), msg." + capitalize(field.getName()) + ")";
            case "uint16":
                return "BitConverter.TryWriteBytes(data.Slice(" + offset + ", 2), msg." + capitalize(field.getName()) + ")";
            case "uint32":
                return "BitConverter.TryWriteBytes(data.Slice(" + offset + ", 4), msg." + capitalize(field.getName()) + ")";
            case "uint64":
                return "BitConverter.TryWriteBytes(data.Slice(" + offset + ", 8), msg." + capitalize(field.getName()) + ")";
            default:
                return "data[" + offset + "] = msg." + capitalize(field.getName());
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

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
