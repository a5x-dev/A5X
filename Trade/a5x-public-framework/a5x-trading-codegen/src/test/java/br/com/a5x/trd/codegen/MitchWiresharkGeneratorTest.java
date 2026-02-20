package br.com.a5x.trd.codegen;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import br.com.a5x.trd.codegen.mitch.MitchWiresharkGenerator;
import br.com.a5x.trd.codegen.mitch.MitchXmlParser;
import br.com.a5x.trd.codegen.model.MessageDefinition;

class MitchWiresharkGeneratorTest {

    @TempDir
    File tempDir;
    
    private List<MessageDefinition> messages;
    
    @BeforeEach
    void setUp() throws Exception {
        InputStream xmlStream = getClass().getResourceAsStream("/mitch-messages.xml");
        MitchXmlParser parser = new MitchXmlParser();
        messages = parser.parse(xmlStream);
    }
    
    @Test
    void testGenerateCreatesLuaFile() throws Exception {
        MitchWiresharkGenerator generator = new MitchWiresharkGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File luaFile = new File(tempDir, "mitch_dissector.lua");
        assertTrue(luaFile.exists());
    }
    
    @Test
    void testGeneratedFileContainsProtocolDeclaration() throws Exception {
        MitchWiresharkGenerator generator = new MitchWiresharkGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File luaFile = new File(tempDir, "mitch_dissector.lua");
        String content = Files.readString(luaFile.toPath());
        
        assertTrue(content.contains("local mitch_proto = Proto"));
        assertTrue(content.contains("MITCH"));
    }
    
    @Test
    void testGeneratedFileContainsFieldDeclarations() throws Exception {
        MitchWiresharkGenerator generator = new MitchWiresharkGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File luaFile = new File(tempDir, "mitch_dissector.lua");
        String content = Files.readString(luaFile.toPath());
        
        assertTrue(content.contains("ProtoField"));
        assertTrue(content.contains("f_msg_type"));
    }
    
    @Test
    void testGeneratedFileContainsMessageTypes() throws Exception {
        MitchWiresharkGenerator generator = new MitchWiresharkGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File luaFile = new File(tempDir, "mitch_dissector.lua");
        String content = Files.readString(luaFile.toPath());
        
        assertTrue(content.contains("local msg_types"));
        
        for (MessageDefinition msg : messages) {
            assertTrue(content.contains(msg.getName()));
        }
    }
    
    @Test
    void testGeneratedFileContainsDissectorFunction() throws Exception {
        MitchWiresharkGenerator generator = new MitchWiresharkGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File luaFile = new File(tempDir, "mitch_dissector.lua");
        String content = Files.readString(luaFile.toPath());
        
        assertTrue(content.contains("function mitch_proto.dissector"));
        assertTrue(content.contains("buffer, pinfo, tree"));
    }
    
    @Test
    void testGeneratedFileContainsTcpPortRegistration() throws Exception {
        MitchWiresharkGenerator generator = new MitchWiresharkGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File luaFile = new File(tempDir, "mitch_dissector.lua");
        String content = Files.readString(luaFile.toPath());
        
        assertTrue(content.contains("tcp_port:add"));
        assertTrue(content.contains("DissectorTable.get"));
    }
    
    @Test
    void testGeneratedFileContainsMessageParsing() throws Exception {
        MitchWiresharkGenerator generator = new MitchWiresharkGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File luaFile = new File(tempDir, "mitch_dissector.lua");
        String content = Files.readString(luaFile.toPath());
        
        assertTrue(content.contains("if msg_type =="));
        assertTrue(content.contains("subtree:add"));
    }
}
