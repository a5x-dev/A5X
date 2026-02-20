package br.com.a5x.trd.codegen;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import br.com.a5x.trd.codegen.mitch.MitchCSharpGenerator;
import br.com.a5x.trd.codegen.mitch.MitchXmlParser;
import br.com.a5x.trd.codegen.model.MessageDefinition;

class MitchCSharpGeneratorTest {

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
    void testGenerateCreatesFiles() throws Exception {
        MitchCSharpGenerator generator = new MitchCSharpGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File[] files = tempDir.listFiles((dir, name) -> name.endsWith(".cs"));
        assertNotNull(files);
        assertEquals(messages.size(), files.length);
    }
    
    @Test
    void testGeneratedFileContainsClassName() throws Exception {
        MitchCSharpGenerator generator = new MitchCSharpGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        MessageDefinition firstMsg = messages.get(0);
        File generatedFile = new File(tempDir, firstMsg.getName() + ".cs");
        assertTrue(generatedFile.exists());
        
        String content = Files.readString(generatedFile.toPath());
        assertTrue(content.contains("public class " + firstMsg.getName()));
    }
    
    @Test
    void testGeneratedFileContainsNamespace() throws Exception {
        MitchCSharpGenerator generator = new MitchCSharpGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        MessageDefinition firstMsg = messages.get(0);
        File generatedFile = new File(tempDir, firstMsg.getName() + ".cs");
        
        String content = Files.readString(generatedFile.toPath());
        assertTrue(content.contains("namespace A5X.Trading.Mitch.Model"));
    }
    
    @Test
    void testGeneratedFileContainsParseMethod() throws Exception {
        MitchCSharpGenerator generator = new MitchCSharpGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        MessageDefinition firstMsg = messages.get(0);
        File generatedFile = new File(tempDir, firstMsg.getName() + ".cs");
        
        String content = Files.readString(generatedFile.toPath());
        assertTrue(content.contains("public static void Parse"));
        assertTrue(content.contains("Span<byte> data"));
    }
    
    @Test
    void testGeneratedFileContainsEncodeMethod() throws Exception {
        MitchCSharpGenerator generator = new MitchCSharpGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        MessageDefinition firstMsg = messages.get(0);
        File generatedFile = new File(tempDir, firstMsg.getName() + ".cs");
        
        String content = Files.readString(generatedFile.toPath());
        assertTrue(content.contains("public static void Encode"));
        assertTrue(content.contains("Span<byte> data"));
    }
    
    @Test
    void testGeneratedFileContainsProperties() throws Exception {
        MitchCSharpGenerator generator = new MitchCSharpGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        MessageDefinition firstMsg = messages.get(0);
        File generatedFile = new File(tempDir, firstMsg.getName() + ".cs");
        
        String content = Files.readString(generatedFile.toPath());
        assertTrue(content.contains("{ get; set; }"));
    }
    
    @Test
    void testGeneratedFileContainsUsings() throws Exception {
        MitchCSharpGenerator generator = new MitchCSharpGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        MessageDefinition firstMsg = messages.get(0);
        File generatedFile = new File(tempDir, firstMsg.getName() + ".cs");
        
        String content = Files.readString(generatedFile.toPath());
        assertTrue(content.contains("using System;"));
        assertTrue(content.contains("using System.Numerics;"));
    }
}
