package br.com.a5x.trd.codegen;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import br.com.a5x.trd.codegen.nativ.NativeCppGenerator;
import br.com.a5x.trd.codegen.nativ.NativeXmlParser;
import br.com.a5x.trd.codegen.model.MessageDefinition;

class NativeCppGeneratorTest {

    @TempDir
    File tempDir;
    
    private List<MessageDefinition> messages;
    
    @BeforeEach
    void setUp() throws Exception {
        InputStream xmlStream = getClass().getResourceAsStream("/native-messages.xml");
        NativeXmlParser parser = new NativeXmlParser();
        messages = parser.parse(xmlStream);
    }
    
    @Test
    void testGenerateCreatesFiles() throws Exception {
        NativeCppGenerator generator = new NativeCppGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File hppFile = new File(tempDir, "NativeLogon.hpp");
        assertTrue(hppFile.exists());
    }
    
    @Test
    void testGeneratedFileContainsIncludeGuards() throws Exception {
        NativeCppGenerator generator = new NativeCppGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File hppFile = new File(tempDir, "NativeLogon.hpp");
        String content = Files.readString(hppFile.toPath());
        
        assertTrue(content.contains("#pragma once"));
    }
    
    @Test
    void testGeneratedFileContainsIncludes() throws Exception {
        NativeCppGenerator generator = new NativeCppGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File hppFile = new File(tempDir, "NativeLogon.hpp");
        String content = Files.readString(hppFile.toPath());
        
        assertTrue(content.contains("#include <cstdint>"));
        assertTrue(content.contains("#include <cstring>"));
    }
    
    @Test
    void testGeneratedFileContainsNamespace() throws Exception {
        NativeCppGenerator generator = new NativeCppGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File hppFile = new File(tempDir, "NativeLogon.hpp");
        String content = Files.readString(hppFile.toPath());
        
        assertTrue(content.contains("namespace a5x::trading::native"));
    }
    
    @Test
    void testGeneratedFileContainsStruct() throws Exception {
        NativeCppGenerator generator = new NativeCppGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File hppFile = new File(tempDir, "NativeLogon.hpp");
        String content = Files.readString(hppFile.toPath());
        
        assertTrue(content.contains("struct NativeLogon"));
    }
    
    @Test
    void testGeneratedFileContainsParseMethod() throws Exception {
        NativeCppGenerator generator = new NativeCppGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File hppFile = new File(tempDir, "NativeLogon.hpp");
        String content = Files.readString(hppFile.toPath());
        
        assertTrue(content.contains("static void parse"));
        assertTrue(content.contains("const uint8_t* data"));
    }
    
    @Test
    void testGeneratedFileContainsEncodeMethod() throws Exception {
        NativeCppGenerator generator = new NativeCppGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File hppFile = new File(tempDir, "NativeLogon.hpp");
        String content = Files.readString(hppFile.toPath());
        
        assertTrue(content.contains("static void encode"));
    }
}
