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
        sb.append("import java.nio.ByteBuffer;\n\n");
        
        sb.append("/**\n");
        sb.append(" * ").append(msg.getDescription()).append("\n");
        sb.append(" * Message Type: ").append(msg.getMessageType()).append("\n");
        sb.append(" */\n");
        sb.append("public class ").append(msg.getName()).append(" {\n\n");
        
        for (FieldDefinition field : msg.getFields()) {
            sb.append("    /** ").append(field.getDescription()).append(" */\n");
            sb.append("    public ").append(getJavaType(field)).append(" ").append(field.getName());
            if (field.getType().equals("alpha")) {
                sb.append(" = new byte[").append(field.getLength()).append("]");
            }
            sb.append(";\n\n");
            if ("price".equals(field.getType())) {
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
                sb.append("                \"").append(field.getName()).append("=\" + ");
                if (field.getType().equals("alpha")) {
                    sb.append("new String(").append(field.getName()).append(").trim()");
                } else {
                    sb.append(field.getName());
                }
                sb.append(i < msg.getFields().size() - 1 ? " + \", \" +\n" : " + '}';\n");
            }
        }
        
        sb.append("    }\n\n");
        
        sb.append("    private static byte[] readAlpha(ByteBuffer data, int length) {\n");
        sb.append("        byte[] result = new byte[length];\n");
        sb.append("        data.get(result);\n");
        sb.append("        return result;\n");
        sb.append("    }\n\n");
        
        sb.append("    private static void writeAlpha(ByteBuffer data, byte[] value, int length) {\n");
        sb.append("        byte[] temp = new byte[length];\n");
        sb.append("        System.arraycopy(value, 0, temp, 0, Math.min(value.length, length));\n");
        sb.append("        data.put(temp);\n");
        sb.append("    }\n");
        sb.append("}\n");
        
        File file = new File(outputDir, msg.getName() + ".java");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(sb.toString());
        }
    }

    private String getJavaType(FieldDefinition field) {
        switch (field.getType()) {
            case "alpha": return "byte[]";
            case "byte": return "byte";
            case "price": return "int";
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
            case "price":
                return "data.getInt()";
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
            case "price":
                return "data.putInt(msg." + field.getName() + ")";
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
