package br.com.a5x.trd.codegen;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import br.com.a5x.trd.codegen.nativ.NativeCodeGenerator;
import br.com.a5x.trd.codegen.nativ.NativeXmlParser;
import br.com.a5x.trd.codegen.model.MessageDefinition;

class NativeCodeGeneratorTest {

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
        NativeCodeGenerator generator = new NativeCodeGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File javaFile = new File(tempDir, "NativeLogon.java");
        assertTrue(javaFile.exists());
    }
    
    @Test
    void testGeneratedFileContainsClassName() throws Exception {
        NativeCodeGenerator generator = new NativeCodeGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File javaFile = new File(tempDir, "NativeLogon.java");
        String content = Files.readString(javaFile.toPath());
        
        assertTrue(content.contains("public class NativeLogon"));
    }
    
    @Test
    void testGeneratedFileContainsFields() throws Exception {
        NativeCodeGenerator generator = new NativeCodeGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File javaFile = new File(tempDir, "NativeLogon.java");
        String content = Files.readString(javaFile.toPath());
        
        assertTrue(content.contains("public byte[] compId"));
        assertTrue(content.contains("public int protocolVersion"));
    }
    
    @Test
    void testGeneratedFileContainsParseMethod() throws Exception {
        NativeCodeGenerator generator = new NativeCodeGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File javaFile = new File(tempDir, "NativeLogon.java");
        String content = Files.readString(javaFile.toPath());
        
        assertTrue(content.contains("public static void parse"));
        assertTrue(content.contains("ByteBuffer data"));
    }
    
    @Test
    void testGeneratedFileContainsEncodeMethod() throws Exception {
        NativeCodeGenerator generator = new NativeCodeGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File javaFile = new File(tempDir, "NativeLogon.java");
        String content = Files.readString(javaFile.toPath());
        
        assertTrue(content.contains("public static void encode"));
    }
    
    @Test
    void testGeneratedFileContainsToString() throws Exception {
        NativeCodeGenerator generator = new NativeCodeGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File javaFile = new File(tempDir, "NativeLogon.java");
        String content = Files.readString(javaFile.toPath());
        
        assertTrue(content.contains("public String toString"));
    }
    
    @Test
    void testGeneratedFileContainsImports() throws Exception {
        NativeCodeGenerator generator = new NativeCodeGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File javaFile = new File(tempDir, "NativeLogon.java");
        String content = Files.readString(javaFile.toPath());
        
        assertTrue(content.contains("import java.nio.ByteBuffer"));
    }
}
