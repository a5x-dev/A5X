package br.com.a5x.trd.codegen.nativ;

import br.com.a5x.trd.codegen.model.MessageDefinition;
import br.com.a5x.trd.codegen.model.FieldDefinition;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class NativeCodeGenerator {

    public void generate(List<MessageDefinition> messages, String outputDir) throws IOException {
        File dir = new File(outputDir);
        if (!dir.exists()) dir.mkdirs();

        for (MessageDefinition msg : messages) {
            generateMessage(msg, outputDir);
        }
    }

    private void generateMessage(MessageDefinition msg, String outputDir) throws IOException {
        StringBuilder sb = new StringBuilder();
        
        sb.append("package br.com.a5x.trd.pub.nativ.model;\n\n");
        sb.append("import java.nio.ByteBuffer;\n");
        sb.append("import java.nio.charset.StandardCharsets;\n\n");
        
        sb.append("/**\n");
        sb.append(" * ").append(msg.getDescription()).append("\n");
        sb.append(" * Message Type: ").append(msg.getMessageType()).append("\n");
        sb.append(" */\n");
        sb.append("public class ").append(msg.getName()).append(" {\n\n");
        
        for (FieldDefinition field : msg.getFields()) {
            sb.append("    /** ").append(field.getDescription()).append(" */\n");
            sb.append("    public ").append(getJavaType(field)).append(" ").append(field.getName());
            if (field.getType().equals("alpha")) {
                sb.append(" = \"\"");
            }
            sb.append(";\n\n");
            // Both price types carry 4 implied decimals. See the <types> block in
            // native-messages.xml for why price8 is scale 4 and not the 8 the portal states.
            if ("price4".equals(field.getType()) || "price8".equals(field.getType())) {
                sb.append("    public java.math.BigDecimal ").append(field.getName()).append("AsDecimal() { return java.math.BigDecimal.valueOf(").append(field.getName()).append(", 4); }\n\n");
            }
        }
        
        sb.append("    public static void parse(").append(msg.getName()).append(" msg, ByteBuffer data) {\n");
        for (FieldDefinition field : msg.getFields()) {
            sb.append("        msg.").append(field.getName()).append(" = ");
            sb.append(getParseCode(field)).append(";\n");
        }
        sb.append("    }\n\n");
        
        sb.append("    public static void encode(").append(msg.getName()).append(" msg, ByteBuffer data) {\n");
        for (FieldDefinition field : msg.getFields()) {
            sb.append("        ").append(getEncodeCode(field)).append(";\n");
        }
        sb.append("    }\n\n");
        
        sb.append("    @Override\n");
        sb.append("    public String toString() {\n");
        
        if (msg.getFields().isEmpty()) {
            sb.append("        return \"").append(msg.getName()).append("{}\";\n");
        } else {
            sb.append("        return \"").append(msg.getName()).append("{\" +\n");
            for (int i = 0; i < msg.getFields().size(); i++) {
                FieldDefinition field = msg.getFields().get(i);
                sb.append("                \"").append(field.getName()).append("=\" + ").append(field.getName());
                sb.append(i < msg.getFields().size() - 1 ? " + \", \" +\n" : " + '}';\n");
            }
        }
        
        sb.append("    }\n\n");
        
        // Native alpha is null-terminated: read up to the first NUL, pad writes with NUL.
        sb.append("    private static String readAlpha(ByteBuffer data, int length) {\n");
        sb.append("        byte[] raw = new byte[length];\n");
        sb.append("        data.get(raw);\n");
        sb.append("        int end = 0;\n");
        sb.append("        while (end < length && raw[end] != 0) {\n");
        sb.append("            end++;\n");
        sb.append("        }\n");
        sb.append("        return new String(raw, 0, end, StandardCharsets.US_ASCII);\n");
        sb.append("    }\n\n");
        
        sb.append("    private static void writeAlpha(ByteBuffer data, String value, int length) {\n");
        sb.append("        byte[] out = new byte[length];\n");
        sb.append("        if (value != null) {\n");
        sb.append("            byte[] src = value.getBytes(StandardCharsets.US_ASCII);\n");
        sb.append("            System.arraycopy(src, 0, out, 0, Math.min(src.length, length));\n");
        sb.append("        }\n");
        sb.append("        data.put(out);\n");
        sb.append("    }\n");
        sb.append("}\n");
        
        File file = new File(outputDir, msg.getName() + ".java");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(sb.toString());
        }
    }

    private String getJavaType(FieldDefinition field) {
        switch (field.getType()) {
            // Keep in sync with MitchCodeGenerator.getJavaType for the shared core types.
            case "alpha": return "String";
            case "byte": return "byte";
            case "price4": return "int";
            case "price8": return "long";
            case "uint8": return "int";
            case "uint16": return "int";
            case "uint32": return "long";
            case "uint64": return "long";
            case "int32": return "int";
            case "bitfield": return "byte";
            default: return "byte[]";
        }
    }

    private String getParseCode(FieldDefinition field) {
        switch (field.getType()) {
            case "alpha":
                return "readAlpha(data, " + field.getLength() + ")";
            case "byte":
                return "data.get()";
            case "price4":
                return "data.getInt()";
            case "price8":
                return "data.getLong()";
            case "uint8":
                return "Byte.toUnsignedInt(data.get())";
            case "uint16":
                return "Short.toUnsignedInt(data.getShort())";
            case "uint32":
                return "Integer.toUnsignedLong(data.getInt())";
            case "uint64":
                return "data.getLong()";
            case "int32":
                return "data.getInt()";
            case "bitfield":
                return "data.get()";
            default:
                return "data.get()";
        }
    }

    private String getEncodeCode(FieldDefinition field) {
        switch (field.getType()) {
            case "alpha":
                return "writeAlpha(data, msg." + field.getName() + ", " + field.getLength() + ")";
            case "byte":
                return "data.put(msg." + field.getName() + ")";
            case "price4":
                return "data.putInt(msg." + field.getName() + ")";
            case "price8":
                return "data.putLong(msg." + field.getName() + ")";
            case "uint8":
                return "data.put((byte) msg." + field.getName() + ")";
            case "uint16":
                return "data.putShort((short) msg." + field.getName() + ")";
            case "uint32":
                return "data.putInt((int) msg." + field.getName() + ")";
            case "uint64":
                return "data.putLong(msg." + field.getName() + ")";
            case "int32":
                return "data.putInt(msg." + field.getName() + ")";
            case "bitfield":
                return "data.put(msg." + field.getName() + ")";
            default:
                return "data.put(msg." + field.getName() + ")";
        }
    }
}
