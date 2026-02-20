package br.com.a5x.trd.codegen;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import br.com.a5x.trd.codegen.mitch.MitchCodeGenerator;
import br.com.a5x.trd.codegen.mitch.MitchXmlParser;
import br.com.a5x.trd.codegen.model.MessageDefinition;

class MitchCodeGeneratorTest {

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
        MitchCodeGenerator generator = new MitchCodeGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File[] files = tempDir.listFiles((dir, name) -> name.endsWith(".java"));
        assertNotNull(files);
        assertEquals(messages.size(), files.length, "Should generate one file per message");
    }
    
    @Test
    void testGeneratedFileContainsClassName() throws Exception {
        MitchCodeGenerator generator = new MitchCodeGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        MessageDefinition firstMsg = messages.get(0);
        File generatedFile = new File(tempDir, firstMsg.getName() + ".java");
        assertTrue(generatedFile.exists());
        
        String content = Files.readString(generatedFile.toPath());
        assertTrue(content.contains("public class " + firstMsg.getName()));
    }
    
    @Test
    void testGeneratedFileContainsParseMethod() throws Exception {
        MitchCodeGenerator generator = new MitchCodeGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        MessageDefinition firstMsg = messages.get(0);
        File generatedFile = new File(tempDir, firstMsg.getName() + ".java");
        
        String content = Files.readString(generatedFile.toPath());
        assertTrue(content.contains("public static void parse"));
        assertTrue(content.contains("ByteBuffer pData"));
    }
    
    @Test
    void testGeneratedFileContainsEncodeMethod() throws Exception {
        MitchCodeGenerator generator = new MitchCodeGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        MessageDefinition firstMsg = messages.get(0);
        File generatedFile = new File(tempDir, firstMsg.getName() + ".java");
        
        String content = Files.readString(generatedFile.toPath());
        assertTrue(content.contains("public static void encode"));
        assertTrue(content.contains("ByteBuffer pData"));
    }
    
    @Test
    void testGeneratedFileContainsFields() throws Exception {
        MitchCodeGenerator generator = new MitchCodeGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        MessageDefinition firstMsg = messages.get(0);
        File generatedFile = new File(tempDir, firstMsg.getName() + ".java");
        
        String content = Files.readString(generatedFile.toPath());
        
        for (var field : firstMsg.getFields()) {
            assertTrue(content.contains("public "), "Should have public fields");
            assertTrue(content.contains(field.getName()), "Should contain field: " + field.getName());
        }
    }
    
    @Test
    void testGeneratedFileContainsToString() throws Exception {
        MitchCodeGenerator generator = new MitchCodeGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        MessageDefinition firstMsg = messages.get(0);
        File generatedFile = new File(tempDir, firstMsg.getName() + ".java");
        
        String content = Files.readString(generatedFile.toPath());
        assertTrue(content.contains("@Override"));
        assertTrue(content.contains("public String toString()"));
    }
    
    @Test
    void testGeneratedFileContainsImports() throws Exception {
        MitchCodeGenerator generator = new MitchCodeGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        MessageDefinition firstMsg = messages.get(0);
        File generatedFile = new File(tempDir, firstMsg.getName() + ".java");
        
        String content = Files.readString(generatedFile.toPath());
        assertTrue(content.contains("import java.math.BigInteger"));
        assertTrue(content.contains("import java.nio.ByteBuffer"));
        assertTrue(content.contains("import br.com.a5x.trd.pub.fw.utils.BitUtils"));
    }
}
