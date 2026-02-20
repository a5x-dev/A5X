package br.com.a5x.trd.codegen.nativ;

import br.com.a5x.trd.codegen.model.MessageDefinition;
import br.com.a5x.trd.codegen.model.FieldDefinition;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class NativeWiresharkGenerator {

    public void generate(List<MessageDefinition> messages, String outputDir) throws IOException {
        File dir = new File(outputDir);
        if (!dir.exists()) dir.mkdirs();

        StringBuilder sb = new StringBuilder();
        
        sb.append("-- Native Protocol Dissector\n");
        sb.append("-- Auto-generated Wireshark dissector\n\n");
        sb.append("local native_proto = Proto(\"native\", \"Native Trading Protocol\")\n\n");
        
        sb.append("local f = native_proto.fields\n");
        sb.append("f.start = ProtoField.uint8(\"native.start\", \"Start of Message\", base.HEX)\n");
        sb.append("f.length = ProtoField.uint16(\"native.length\", \"Message Length\", base.DEC)\n");
        sb.append("f.msgtype = ProtoField.string(\"native.msgtype\", \"Message Type\")\n\n");
        
        for (MessageDefinition msg : messages) {
            for (FieldDefinition field : msg.getFields()) {
                String fieldName = msg.getName().toLowerCase() + "." + field.getName();
                sb.append("f.").append(fieldName.replace(".", "_")).append(" = ");
                sb.append(getWiresharkField(field, fieldName)).append("\n");
            }
        }
        
        sb.append("\nlocal message_types = {\n");
        for (MessageDefinition msg : messages) {
            sb.append("    [\"").append(msg.getMessageType()).append("\"] = \"").append(msg.getName()).append("\",\n");
        }
        sb.append("}\n\n");
        
        sb.append("function native_proto.dissector(buffer, pinfo, tree)\n");
        sb.append("    local length = buffer:len()\n");
        sb.append("    if length == 0 then return end\n\n");
        sb.append("    pinfo.cols.protocol = native_proto.name\n\n");
        sb.append("    local subtree = tree:add(native_proto, buffer(), \"Native Protocol\")\n");
        sb.append("    local offset = 0\n\n");
        sb.append("    local start = buffer(offset, 1):uint()\n");
        sb.append("    subtree:add(f.start, buffer(offset, 1))\n");
        sb.append("    offset = offset + 1\n\n");
        sb.append("    local msg_length = buffer(offset, 2):le_uint()\n");
        sb.append("    subtree:add(f.length, buffer(offset, 2))\n");
        sb.append("    offset = offset + 2\n\n");
        sb.append("    local msgtype = buffer(offset, 1):string()\n");
        sb.append("    subtree:add(f.msgtype, buffer(offset, 1))\n");
        sb.append("    offset = offset + 1\n\n");
        sb.append("    local msg_name = message_types[msgtype] or \"Unknown\"\n");
        sb.append("    pinfo.cols.info = msg_name\n\n");
        
        for (MessageDefinition msg : messages) {
            sb.append("    if msgtype == \"").append(msg.getMessageType()).append("\" then\n");
            sb.append("        -- ").append(msg.getName()).append("\n");
        for (FieldDefinition field : msg.getFields()) {
                String fieldName = msg.getName().toLowerCase() + "_" + field.getName();
                int size = getFieldSize(field);
                if ("price".equals(field.getType())) {
                    sb.append("        do local v = buffer(offset, ").append(size).append("):le_int()")
                      .append(" subtree:add(f.").append(fieldName).append(", buffer(offset, ").append(size).append("))")
                      .append(":set_text(\"").append(capitalize(field.getName())).append(": \" .. string.format(\"%.4f\", v / 10000000.0)) end\n");
                } else {
                    sb.append("        subtree:add(f.").append(fieldName).append(", buffer(offset, ").append(size).append("))\n");
                }
                sb.append("        offset = offset + ").append(size).append("\n");
            }
            sb.append("    end\n\n");
        }
        
        sb.append("end\n\n");
        sb.append("local tcp_port = DissectorTable.get(\"tcp.port\")\n");
        sb.append("tcp_port:add(9999, native_proto)\n");
        
        File file = new File(outputDir, "native_dissector.lua");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(sb.toString());
        }
    }

    private String getWiresharkField(FieldDefinition field, String fieldName) {
        switch (field.getType()) {
            case "alpha":
                return "ProtoField.string(\"native." + fieldName + "\", \"" + capitalize(field.getName()) + "\")";
            case "byte":
            case "uint8":
            case "bitfield":
                return "ProtoField.uint8(\"native." + fieldName + "\", \"" + capitalize(field.getName()) + "\", base.DEC)";
            case "uint16":
                return "ProtoField.uint16(\"native." + fieldName + "\", \"" + capitalize(field.getName()) + "\", base.DEC)";
            case "price":
            case "int32":
                return "ProtoField.int32(\"native." + fieldName + "\", \"" + capitalize(field.getName()) + "\", base.DEC)";
            case "uint32":
                return "ProtoField.uint32(\"native." + fieldName + "\", \"" + capitalize(field.getName()) + "\", base.DEC)";
            case "uint64":
                return "ProtoField.uint64(\"native." + fieldName + "\", \"" + capitalize(field.getName()) + "\", base.DEC)";
            default:
                return "ProtoField.bytes(\"native." + fieldName + "\", \"" + capitalize(field.getName()) + "\")";
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
