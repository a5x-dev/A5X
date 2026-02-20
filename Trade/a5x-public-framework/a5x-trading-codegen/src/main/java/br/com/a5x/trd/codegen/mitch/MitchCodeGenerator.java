package br.com.a5x.trd.codegen.mitch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import br.com.a5x.trd.codegen.model.FieldDefinition;
import br.com.a5x.trd.codegen.model.MessageDefinition;

public class MitchCodeGenerator {
    
    private static final String PACKAGE = "br.com.a5x.trd.pub.mitch.model";
    
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
        
        sb.append("package ").append(PACKAGE).append(";\n\n");
        sb.append("import java.math.BigInteger;\n");
        sb.append("import java.nio.ByteBuffer;\n\n");
        sb.append("import br.com.a5x.trd.pub.fw.utils.BitUtils;\n\n");
        
        if (message.getDescription() != null && !message.getDescription().isEmpty()) {
            sb.append("/**\n");
            sb.append(" * ").append(message.getDescription()).append("\n");
            sb.append(" */\n");
        }
        
        sb.append("public class ").append(message.getName()).append(" {\n");
        
        for (FieldDefinition field : message.getFields()) {
            sb.append("\t/**\n");
            sb.append("\t * ").append(field.getDescription()).append("\n");
            sb.append("\t */\n");
            
            String javaType = getJavaType(field.getType(), field.getLength());
            sb.append("\tpublic ").append(javaType).append(" ").append(field.getName());
            
            if ("bytearray".equals(field.getType()) && field.getLength() != null) {
                sb.append(" = new byte[").append(field.getLength()).append("]");
            }
            
            sb.append(";\n\n");
            
            if ("price4".equals(field.getType()) || "price8".equals(field.getType())) {
                sb.append("\tpublic java.math.BigDecimal ").append(field.getName()).append("AsDecimal() { return java.math.BigDecimal.valueOf(").append(field.getName()).append(", 4); }\n\n");
            }
        }
        
        sb.append("\t/**\n");
        sb.append("\t * Parses ").append(message.getName().replace("Mitch", "")).append(" message from ByteBuffer.\n");
        sb.append("\t * \n");
        sb.append("\t * @param p").append(message.getName().replace("Mitch", "")).append(" the ").append(message.getName()).append(" instance to populate\n");
        sb.append("\t * @param pData the ByteBuffer containing message data (little-endian)\n");
        sb.append("\t */\n");
        sb.append("\tpublic static void parse(").append(message.getName()).append(" p").append(message.getName().replace("Mitch", "")).append(", ByteBuffer pData) {\n");
        
        for (FieldDefinition field : message.getFields()) {
            String paramPrefix = "p" + message.getName().replace("Mitch", "");
            
            if ("bytearray".equals(field.getType())) {
                sb.append("\t\tpData.get(").append(paramPrefix).append(".").append(field.getName()).append(");\n");
            } else if ("string".equals(field.getType()) || "alpha".equals(field.getType()) || 
                       "date".equals(field.getType()) || "time".equals(field.getType()) || 
                       "extendedtime".equals(field.getType()) || "datetime".equals(field.getType())) {
                sb.append("\t\tbyte[] ").append(field.getName()).append("Bytes = new byte[").append(field.getLength()).append("];\n");
                sb.append("\t\tpData.get(").append(field.getName()).append("Bytes);\n");
                sb.append("\t\t").append(paramPrefix).append(".").append(field.getName()).append(" = new String(").append(field.getName()).append("Bytes).trim();\n");
            } else {
                sb.append("\t\t").append(paramPrefix).append(".").append(field.getName()).append(" = ");
                sb.append(getParseMethod(field)).append(";\n");
            }
        }
        
        sb.append("\t}\n\n");
        
        // Generate encode method
        sb.append("\t/**\n");
        sb.append("\t * Encodes ").append(message.getName().replace("Mitch", "")).append(" message to ByteBuffer.\n");
        sb.append("\t * \n");
        sb.append("\t * @param p").append(message.getName().replace("Mitch", "")).append(" the ").append(message.getName()).append(" instance to encode\n");
        sb.append("\t * @param pData the ByteBuffer to write message data (little-endian)\n");
        sb.append("\t */\n");
        sb.append("\tpublic static void encode(").append(message.getName()).append(" p").append(message.getName().replace("Mitch", "")).append(", ByteBuffer pData) {\n");
        
        for (FieldDefinition field : message.getFields()) {
            String paramPrefix = "p" + message.getName().replace("Mitch", "");
            sb.append(getEncodeMethod(field, paramPrefix));
        }
        
        sb.append("\t}\n\n");
        
        sb.append("\t@Override\n");
        sb.append("\tpublic String toString() {\n");
        
        if (message.getFields().isEmpty()) {
            sb.append("\t\treturn \"").append(message.getName()).append("[]\";\n");
        } else {
            sb.append("\t\treturn \"").append(message.getName()).append(" [");
            
            for (int i = 0; i < message.getFields().size(); i++) {
                FieldDefinition field = message.getFields().get(i);
                if (i > 0) sb.append(" + \", ");
                else sb.append("\" + \"");
                sb.append(field.getName()).append("=\" + ").append(field.getName());
            }
            
            sb.append(" + \"]\";\n");
        }
        
        sb.append("\t}\n");
        sb.append("}\n");
        
        File outputFile = new File(outputDir, message.getName() + ".java");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(sb.toString());
        }
    }
    
    private String getJavaType(String xmlType, Integer length) {
        switch (xmlType) {
            case "uint32": return "long";
            case "uint64": return "BigInteger";
            case "uint8": return "int";
            case "uint16": return "int";
            case "int32": return "int";
            case "int64": return "long";
            case "char": return "char";
            case "byte": return "byte";
            case "bytearray": return "byte[]";
            case "string": return "String";
            case "alpha": return "String";
            case "price4": return "int";
            case "price8": return "long";
            case "bitfield": return "byte";
            case "date": return "String";
            case "time": return "String";
            case "extendedtime": return "String";
            case "datetime": return "String";
            default: return "Object";
        }
    }
    
    private String getParseMethod(FieldDefinition field) {
        String paramName = "p" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        
        switch (field.getType()) {
            case "uint32": return "BitUtils.byteArrayToUInt32(pData, false)";
            case "uint64": return "BitUtils.byteArrayToUInt64(pData, false)";
            case "uint8": return "BitUtils.byteToUInt8(pData)";
            case "uint16": return "BitUtils.byteArrayToUInt16(pData, false)";
            case "int32": return "pData.getInt()";
            case "int64": return "pData.getLong()";
            case "price4": return "pData.getInt()";
            case "price8": return "pData.getLong()";
            case "char": return "(char) pData.get()";
            case "byte": return "pData.get()";
            case "bitfield": return "pData.get()";
            case "bytearray": 
                return "pData.get(" + paramName + ")";
            default: return "null";
        }
    }
    
    private String getEncodeMethod(FieldDefinition field, String paramPrefix) {
        StringBuilder sb = new StringBuilder();
        String fieldAccess = paramPrefix + "." + field.getName();
        
        switch (field.getType()) {
            case "uint32":
                sb.append("\t\tBitUtils.uint32ToByteArray(").append(fieldAccess).append(", pData, false);\n");
                break;
            case "uint64":
                sb.append("\t\tBitUtils.uint64ToByteArray(").append(fieldAccess).append(", pData, false);\n");
                break;
            case "uint8":
                sb.append("\t\tBitUtils.uint8ToByte(").append(fieldAccess).append(", pData);\n");
                break;
            case "uint16":
                sb.append("\t\tBitUtils.uint16ToByteArray(").append(fieldAccess).append(", pData, false);\n");
                break;
            case "int32":
                sb.append("\t\tpData.putInt(").append(fieldAccess).append(");\n");
                break;
            case "int64":
                sb.append("\t\tpData.putLong(").append(fieldAccess).append(");\n");
                break;
            case "price4":
                sb.append("\t\tpData.putInt(").append(fieldAccess).append(");\n");
                break;
            case "price8":
                sb.append("\t\tpData.putLong(").append(fieldAccess).append(");\n");
                break;
            case "char":
                sb.append("\t\tpData.put((byte) ").append(fieldAccess).append(");\n");
                break;
            case "byte":
            case "bitfield":
                sb.append("\t\tpData.put(").append(fieldAccess).append(");\n");
                break;
            case "bytearray":
                sb.append("\t\tpData.put(").append(fieldAccess).append(");\n");
                break;
            case "string":
            case "alpha":
            case "date":
            case "time":
            case "extendedtime":
            case "datetime":
                sb.append("\t\tbyte[] ").append(field.getName()).append("Bytes = new byte[").append(field.getLength()).append("];\n");
                sb.append("\t\tbyte[] ").append(field.getName()).append("Src = ").append(fieldAccess).append(".getBytes();\n");
                sb.append("\t\tSystem.arraycopy(").append(field.getName()).append("Src, 0, ").append(field.getName()).append("Bytes, 0, Math.min(").append(field.getName()).append("Src.length, ").append(field.getLength()).append("));\n");
                sb.append("\t\tpData.put(").append(field.getName()).append("Bytes);\n");
                break;
            default:
                sb.append("\t\t// Unknown type: ").append(field.getType()).append("\n");
        }
        
        return sb.toString();
    }
}
