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
        
        // The header is four bytes: the start-of-message byte, a 16-bit little-endian
        // length and the message type. The length counts from the message type onwards,
        // so a message occupies 3 + length bytes on the wire.
        sb.append("-- Start-of-message byte and header size. The length field counts from the\n");
        sb.append("-- message type onwards, so a message occupies 3 + length bytes on the wire.\n");
        sb.append("local NATIVE_SOM = 0x02\n");
        sb.append("local NATIVE_HEADER = 4\n\n");

        sb.append("-- Dissects the one message that begins at 'start'.\n");
        sb.append("local function dissect_message(buffer, start, pinfo, tree)\n");
        sb.append("    local msg_length = buffer(start + 1, 2):le_uint()\n");
        sb.append("    local total = 3 + msg_length\n\n");
        sb.append("    local subtree = tree:add(native_proto, buffer(start, total), \"Native Protocol\")\n");
        sb.append("    local offset = start\n\n");
        sb.append("    subtree:add(f.start, buffer(offset, 1))\n");
        sb.append("    offset = offset + 1\n\n");
        sb.append("    subtree:add(f.length, buffer(offset, 2))\n");
        sb.append("    offset = offset + 2\n\n");
        sb.append("    local msgtype = buffer(offset, 1):string()\n");
        sb.append("    subtree:add(f.msgtype, buffer(offset, 1))\n");
        sb.append("    offset = offset + 1\n\n");
        sb.append("    local msg_name = message_types[msgtype] or \"Unknown\"\n");
        sb.append("    subtree:append_text(\" - \" .. msg_name)\n");
        sb.append("    pinfo.cols.info = msg_name\n\n");
        
        for (MessageDefinition msg : messages) {
            sb.append("    if msgtype == \"").append(msg.getMessageType()).append("\" then\n");
            sb.append("        -- ").append(msg.getName()).append("\n");
        for (FieldDefinition field : msg.getFields()) {
                String fieldName = msg.getName().toLowerCase() + "_" + field.getName();
                int size = getFieldSize(field);
                if ("price4".equals(field.getType())) {
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

        // A TCP segment carries whatever happened to be in the socket buffer: several
        // messages, or the front half of one. Walk every message in the segment, and
        // hand the rest back to Wireshark's reassembly when the segment stops short.
        sb.append("function native_proto.dissector(buffer, pinfo, tree)\n");
        sb.append("    if buffer:len() == 0 then return 0 end\n\n");
        sb.append("    pinfo.cols.protocol = native_proto.name\n");
        sb.append("    local offset = 0\n\n");
        sb.append("    while offset < buffer:len() do\n");
        sb.append("        local available = buffer:len() - offset\n\n");
        sb.append("        -- Not even the header yet: ask for one more segment.\n");
        sb.append("        if available < NATIVE_HEADER then\n");
        sb.append("            pinfo.desegment_offset = offset\n");
        sb.append("            pinfo.desegment_len = DESEGMENT_ONE_MORE_SEGMENT\n");
        sb.append("            return buffer:len()\n");
        sb.append("        end\n\n");
        sb.append("        if buffer(offset, 1):uint() ~= NATIVE_SOM then return offset end\n\n");
        sb.append("        local total = 3 + buffer(offset + 1, 2):le_uint()\n");
        sb.append("        if total < NATIVE_HEADER then return offset end\n\n");
        sb.append("        -- Header says more than this segment holds: ask for the rest.\n");
        sb.append("        if available < total then\n");
        sb.append("            pinfo.desegment_offset = offset\n");
        sb.append("            pinfo.desegment_len = total - available\n");
        sb.append("            return buffer:len()\n");
        sb.append("        end\n\n");
        sb.append("        dissect_message(buffer, offset, pinfo, tree)\n");
        sb.append("        offset = offset + total\n");
        sb.append("    end\n\n");
        sb.append("    return offset\n");
        sb.append("end\n\n");

        sb.append("-- Known Native message types for heuristic detection\n");
        sb.append("local known_native_types = {\n");
        for (MessageDefinition msg : messages) {
            String msgType = msg.getMessageType();
            if (msgType.length() == 1) {
                sb.append("    [0x").append(String.format("%02x", (int) msgType.charAt(0)))
                  .append("] = true, -- ").append(msg.getName()).append("\n");
            }
        }
        sb.append("}\n\n");

        // Recognised by the shape of the data rather than by port: ports differ per
        // environment, and a dissector that had to be reconfigured every time one moved
        // would be wrong more often than right.
        sb.append("local function native_heuristic(buffer, pinfo, tree)\n");
        sb.append("    if buffer:len() < NATIVE_HEADER then return false end\n\n");
        sb.append("    if buffer(0, 1):uint() ~= NATIVE_SOM then return false end\n\n");
        sb.append("    local msg_length = buffer(1, 2):le_uint()\n");
        sb.append("    if msg_length < 1 or msg_length > 4096 then return false end\n");
        sb.append("    if not known_native_types[buffer(3, 1):uint()] then return false end\n\n");
        sb.append("    -- Require the messages in this segment to chain exactly: each one\n");
        sb.append("    -- starts with the start-of-message byte and a known type, and the\n");
        sb.append("    -- lengths add up. That is what separates Native from traffic that\n");
        sb.append("    -- merely starts with plausible looking bytes.\n");
        sb.append("    local offset = 0\n");
        sb.append("    while offset + NATIVE_HEADER <= buffer:len() do\n");
        sb.append("        if buffer(offset, 1):uint() ~= NATIVE_SOM then return false end\n");
        sb.append("        local total = 3 + buffer(offset + 1, 2):le_uint()\n");
        sb.append("        if total < NATIVE_HEADER then return false end\n");
        sb.append("        if not known_native_types[buffer(offset + 3, 1):uint()] then return false end\n");
        sb.append("        -- The last message may run into the next segment; that is fine.\n");
        sb.append("        if offset + total > buffer:len() then break end\n");
        sb.append("        offset = offset + total\n");
        sb.append("    end\n\n");
        sb.append("    native_proto.dissector(buffer, pinfo, tree)\n");
        sb.append("    pinfo.conversation = native_proto\n");
        sb.append("    return true\n");
        sb.append("end\n\n");
        sb.append("native_proto:register_heuristic(\"tcp\", native_heuristic)\n\n");

        // Same fallback the MITCH dissector carries, for a capture that begins
        // mid-session and so never offers the heuristic a first segment to match on.
        sb.append("-- Fallback for a capture that begins mid-session: the heuristic is only\n");
        sb.append("-- offered the first segment of a conversation, so a capture that missed the\n");
        sb.append("-- start has nothing to match on. Set the port in Preferences > Protocols >\n");
        sb.append("-- NATIVE then. 0, the default, means the heuristic above is doing the work.\n");
        sb.append("native_proto.prefs.tcp_port = Pref.uint(\"Native TCP port (fallback)\", 0,\n");
        sb.append("    \"Only needed when the capture misses the start of the connection\")\n\n");
        sb.append("local tcp_port = DissectorTable.get(\"tcp.port\")\n");
        sb.append("local registered_tcp_port = 0\n\n");
        sb.append("function native_proto.prefs_changed()\n");
        sb.append("    if registered_tcp_port ~= 0 then\n");
        sb.append("        tcp_port:remove(registered_tcp_port, native_proto)\n");
        sb.append("    end\n");
        sb.append("    registered_tcp_port = native_proto.prefs.tcp_port\n");
        sb.append("    if registered_tcp_port ~= 0 then\n");
        sb.append("        tcp_port:add(registered_tcp_port, native_proto)\n");
        sb.append("    end\n");
        sb.append("end\n");
        
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
            case "price4":
            case "int32":
                return "ProtoField.int32(\"native." + fieldName + "\", \"" + capitalize(field.getName()) + "\", base.DEC)";
            case "price8":
                return "ProtoField.int64(\"native." + fieldName + "\", \"" + capitalize(field.getName()) + "\", base.DEC)";
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
            case "price4":
            case "int32":
            case "uint32": return 4;
            case "price8":
            case "uint64": return 8;
            default: return 1;
        }
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
