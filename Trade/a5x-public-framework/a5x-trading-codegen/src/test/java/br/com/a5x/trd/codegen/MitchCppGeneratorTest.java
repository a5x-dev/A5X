package br.com.a5x.trd.codegen;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import br.com.a5x.trd.codegen.mitch.MitchCppGenerator;
import br.com.a5x.trd.codegen.mitch.MitchXmlParser;
import br.com.a5x.trd.codegen.model.MessageDefinition;

class MitchCppGeneratorTest {

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
        MitchCppGenerator generator = new MitchCppGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File[] files = tempDir.listFiles((dir, name) -> name.endsWith(".h"));
        assertNotNull(files);
        assertEquals(messages.size(), files.length);
    }
    
    @Test
    void testGeneratedFileContainsStruct() throws Exception {
        MitchCppGenerator generator = new MitchCppGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        MessageDefinition firstMsg = messages.get(0);
        File generatedFile = new File(tempDir, firstMsg.getName() + ".h");
        assertTrue(generatedFile.exists());
        
        String content = Files.readString(generatedFile.toPath());
        assertTrue(content.contains("struct " + firstMsg.getName()));
    }
    
    @Test
    void testGeneratedFileContainsIncludeGuards() throws Exception {
        MitchCppGenerator generator = new MitchCppGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        MessageDefinition firstMsg = messages.get(0);
        File generatedFile = new File(tempDir, firstMsg.getName() + ".h");
        
        String content = Files.readString(generatedFile.toPath());
        String guard = firstMsg.getName().toUpperCase() + "_H";
        assertTrue(content.contains("#ifndef " + guard));
        assertTrue(content.contains("#define " + guard));
        assertTrue(content.contains("#endif"));
    }
    
    @Test
    void testGeneratedFileContainsParseMethod() throws Exception {
        MitchCppGenerator generator = new MitchCppGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        MessageDefinition firstMsg = messages.get(0);
        File generatedFile = new File(tempDir, firstMsg.getName() + ".h");
        
        String content = Files.readString(generatedFile.toPath());
        assertTrue(content.contains("static void parse"));
        assertTrue(content.contains("const uint8_t* data"));
    }
    
    @Test
    void testGeneratedFileContainsEncodeMethod() throws Exception {
        MitchCppGenerator generator = new MitchCppGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        MessageDefinition firstMsg = messages.get(0);
        File generatedFile = new File(tempDir, firstMsg.getName() + ".h");
        
        String content = Files.readString(generatedFile.toPath());
        assertTrue(content.contains("static void encode"));
        assertTrue(content.contains("uint8_t* data"));
    }
    
    @Test
    void testGeneratedFileContainsNamespace() throws Exception {
        MitchCppGenerator generator = new MitchCppGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        MessageDefinition firstMsg = messages.get(0);
        File generatedFile = new File(tempDir, firstMsg.getName() + ".h");
        
        String content = Files.readString(generatedFile.toPath());
        assertTrue(content.contains("namespace a5x::trading::mitch"));
    }
    
    @Test
    void testGeneratedFileContainsIncludes() throws Exception {
        MitchCppGenerator generator = new MitchCppGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        MessageDefinition firstMsg = messages.get(0);
        File generatedFile = new File(tempDir, firstMsg.getName() + ".h");
        
        String content = Files.readString(generatedFile.toPath());
        assertTrue(content.contains("#include <cstdint>"));
        assertTrue(content.contains("#include <cstring>"));
    }
}
