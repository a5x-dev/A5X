package br.com.a5x.trd.codegen.mitch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import br.com.a5x.trd.codegen.model.FieldDefinition;
import br.com.a5x.trd.codegen.model.MessageDefinition;

public class MitchWiresharkGenerator {
    
    public void generate(List<MessageDefinition> messages, String outputDir) throws IOException {
        File dir = new File(outputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        generateDissector(messages, outputDir);
    }
    
    private void generateDissector(List<MessageDefinition> messages, String outputDir) throws IOException {
        StringBuilder sb = new StringBuilder();
        
        sb.append("-- MITCH Protocol Dissector\n");
        sb.append("-- Auto-generated from XML definition\n\n");
        sb.append("local mitch_proto = Proto(\"MITCH\", \"A5X MITCH Protocol\")\n\n");
        
        // Protocol fields
        sb.append("-- Protocol fields\n");
        sb.append("-- Unit Header fields\n");
        sb.append("local f_unit_length = ProtoField.uint16(\"mitch.unit.length\", \"Unit Length\", base.DEC)\n");
        sb.append("local f_unit_msg_count = ProtoField.uint8(\"mitch.unit.msgcount\", \"Message Count\", base.DEC)\n");
        sb.append("local f_unit_mdg = ProtoField.uint8(\"mitch.unit.mdg\", \"Market Data Group\", base.HEX)\n");
        sb.append("local f_unit_seqnum = ProtoField.uint64(\"mitch.unit.seqnum\", \"Sequence Number\", base.DEC)\n");
        sb.append("-- Message fields\n");
        sb.append("local f_msg_type = ProtoField.uint8(\"mitch.msgtype\", \"Message Type\", base.HEX)\n");
        sb.append("local f_msg_length = ProtoField.uint16(\"mitch.length\", \"Message Length\", base.DEC)\n\n");
        
        // Message-specific fields
        sb.append("-- Message-specific fields\n");
        sb.append("local fields = {}\n");
        for (MessageDefinition message : messages) {
            for (FieldDefinition field : message.getFields()) {
                String fieldName = "f_" + message.getName().toLowerCase() + "_" + field.getName().toLowerCase();
                sb.append("fields.").append(fieldName).append(" = ProtoField.");
                sb.append(getWiresharkFieldType(field));
                sb.append("(\"mitch.").append(message.getName().toLowerCase()).append(".").append(field.getName().toLowerCase());
                sb.append("\", \"").append(field.getName()).append("\", ");
                sb.append(getWiresharkBase(field)).append(")\n");
            }
        }
        sb.append("\n");
        
        // Register fields
        sb.append("mitch_proto.fields = {\n");
        sb.append("    f_unit_length, f_unit_msg_count, f_unit_mdg, f_unit_seqnum,\n");
        sb.append("    f_msg_type, f_msg_length");
        
        for (MessageDefinition message : messages) {
            for (FieldDefinition field : message.getFields()) {
                sb.append(",\n    fields.f_").append(message.getName().toLowerCase()).append("_").append(field.getName().toLowerCase());
            }
        }
        
        sb.append("\n}\n\n");
        
        // Message type table
        sb.append("local msg_types = {\n");
        for (MessageDefinition message : messages) {
            String msgType = message.getMessageType().replace("0x", "");
            sb.append("    [0x").append(msgType).append("] = \"").append(message.getName()).append("\",\n");
        }
        sb.append("}\n\n");
        
        // Dissector function
        sb.append("function mitch_proto.dissector(buffer, pinfo, tree)\n");
        sb.append("    local length = buffer:len()\n");
        sb.append("    if length == 0 then return end\n\n");
        sb.append("    pinfo.cols.protocol = mitch_proto.name\n\n");
        sb.append("    local subtree = tree:add(mitch_proto, buffer(), \"MITCH Protocol Data\")\n");
        sb.append("    local offset = 0\n\n");
        sb.append("    -- Unit Header (12 bytes)\n");
        sb.append("    local unit_tree = subtree:add(mitch_proto, buffer(offset, 12), \"Unit Header\")\n");
        sb.append("    local unit_length = buffer(offset, 2):le_uint()\n");
        sb.append("    unit_tree:add_le(f_unit_length, buffer(offset, 2))\n");
        sb.append("    offset = offset + 2\n\n");
        sb.append("    local msg_count = buffer(offset, 1):uint()\n");
        sb.append("    unit_tree:add(f_unit_msg_count, buffer(offset, 1))\n");
        sb.append("    offset = offset + 1\n\n");
        sb.append("    local mdg = buffer(offset, 1):uint()\n");
        sb.append("    unit_tree:add(f_unit_mdg, buffer(offset, 1))\n");
        sb.append("    offset = offset + 1\n\n");
        sb.append("    local seq_num = buffer(offset, 8):le_uint64()\n");
        sb.append("    unit_tree:add_le(f_unit_seqnum, buffer(offset, 8))\n");
        sb.append("    offset = offset + 8\n\n");
        sb.append("    pinfo.cols.info = string.format(\"MDG=%d SeqNum=%s MsgCount=%d\", mdg, tostring(seq_num), msg_count)\n\n");
        sb.append("    -- Parse payload messages\n");
        sb.append("    for i = 1, msg_count do\n");
        sb.append("        local msg_start = offset\n");
        sb.append("        local msg_length = buffer(offset, 2):le_uint()\n");
        sb.append("        local msg_tree = subtree:add(mitch_proto, buffer(offset, msg_length), \"Message \" .. i)\n");
        sb.append("        msg_tree:add_le(f_msg_length, buffer(offset, 2))\n");
        sb.append("        offset = offset + 2\n\n");
        sb.append("        local msg_type = buffer(offset, 1):uint()\n");
        sb.append("        msg_tree:add(f_msg_type, buffer(offset, 1))\n");
        sb.append("        offset = offset + 1\n\n");
        sb.append("        local msg_name = msg_types[msg_type] or \"Unknown\"\n");
        sb.append("        msg_tree:append_text(\" - \" .. msg_name)\n\n");
        
        // Message-specific dissection
        for (int i = 0; i < messages.size(); i++) {
            MessageDefinition message = messages.get(i);
            String msgType = message.getMessageType().replace("0x", "");
            
            if (i == 0) {
                sb.append("        if msg_type == 0x").append(msgType).append(" then\n");
            } else {
                sb.append("        elseif msg_type == 0x").append(msgType).append(" then\n");
            }
            
            sb.append("            -- ").append(message.getName()).append("\n");
            
            for (FieldDefinition field : message.getFields()) {
                int size = getFieldSize(field);
                String fieldName = "fields.f_" + message.getName().toLowerCase() + "_" + field.getName().toLowerCase();
                String addMethod = isStringType(field) ? "msg_tree:add(" : "msg_tree:add_le(";
                if (isPriceType(field)) {
                    long divisor = getPriceDivisor(field);
                    sb.append("            do local v = buffer(offset, ").append(size).append("):le_int64()")
                      .append(" msg_tree:add_le(").append(fieldName).append(", buffer(offset, ").append(size).append("))")
                      .append(":set_text(\"").append(field.getName()).append(": \" .. string.format(\"%.4f\", tonumber(tostring(v)) / ").append(divisor).append(")) end\n");
                } else {
                    sb.append("            ").append(addMethod).append(fieldName).append(", buffer(offset, ").append(size).append("))\n");
                }
                sb.append("            offset = offset + ").append(size).append("\n");
            }
        }
        
        sb.append("        end\n");
        sb.append("        offset = msg_start + msg_length\n");
        sb.append("    end\n");
        sb.append("end\n\n");
        
        // Register dissector
        sb.append("-- Heuristic dissector for multicast IP range 224.4.85.0/24\n");
        sb.append("local function heuristic_checker(buffer, pinfo, tree)\n");
        sb.append("    local dst_ip = tostring(pinfo.dst)\n");
        sb.append("    if dst_ip:match(\"^224%.4%.85%.\") then\n");
        sb.append("        mitch_proto.dissector(buffer, pinfo, tree)\n");
        sb.append("        return true\n");
        sb.append("    end\n");
        sb.append("    return false\n");
        sb.append("end\n\n");
        sb.append("mitch_proto:register_heuristic(\"udp\", heuristic_checker)\n");
        
        File outputFile = new File(outputDir, "mitch_dissector.lua");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(sb.toString());
        }
    }
    
    private String getWiresharkFieldType(FieldDefinition field) {
        switch (field.getType()) {
            case "uint32": return "uint32";
            case "uint64": return "uint64";
            case "uint8": return "uint8";
            case "char": return "string";
            case "byte": return "uint8";
            case "bytearray": return "bytes";
            case "alpha":
            case "string": return "string";
            case "price4": return "int32";
            case "price8": return "int64";
            default: return "bytes";
        }
    }
    
    private String getWiresharkBase(FieldDefinition field) {
        switch (field.getType()) {
            case "uint32":
            case "uint64":
            case "uint8":
            case "price4":
            case "price8":
                return "base.DEC";
            case "char":
            case "alpha":
            case "string":
                return "base.ASCII";
            case "byte":
                return "base.HEX";
            case "bytearray":
                return "base.SPACE";
            default:
                return "base.NONE";
        }
    }

    private boolean isStringType(FieldDefinition field) {
        switch (field.getType()) {
            case "alpha":
            case "string":
            case "char":
                return true;
            default:
                return false;
        }
    }

    private boolean isPriceType(FieldDefinition field) {
        return "price4".equals(field.getType()) || "price8".equals(field.getType());
    }

    private int getPriceDecimals(FieldDefinition field) {
        return "price4".equals(field.getType()) ? 4 : 8;
    }

    private long getPriceDivisor(FieldDefinition field) {
        return 10_000L;
    }
    
    private int getFieldSize(FieldDefinition field) {
        switch (field.getType()) {
            case "uint32": return 4;
            case "uint64": return 8;
            case "uint8": return 1;
            case "uint16": return 2;
            case "int32": return 4;
            case "int64": return 8;
            case "price4": return 4;
            case "price8": return 8;
            case "char": return 1;
            case "byte": return 1;
            case "bitfield": return 1;
            case "bytearray": return field.getLength() != null ? field.getLength() : 0;
            case "string": return field.getLength() != null ? field.getLength() : 0;
            case "alpha": return field.getLength() != null ? field.getLength() : 0;
            case "date": return field.getLength() != null ? field.getLength() : 8;
            case "time": return field.getLength() != null ? field.getLength() : 8;
            case "extendedtime": return field.getLength() != null ? field.getLength() : 12;
            case "datetime": return field.getLength() != null ? field.getLength() : 27;
            default: return 0;
        }
    }
}
