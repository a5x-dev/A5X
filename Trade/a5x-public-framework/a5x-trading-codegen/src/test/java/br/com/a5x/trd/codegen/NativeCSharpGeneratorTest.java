package br.com.a5x.trd.codegen;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import br.com.a5x.trd.codegen.nativ.NativeCSharpGenerator;
import br.com.a5x.trd.codegen.nativ.NativeXmlParser;
import br.com.a5x.trd.codegen.model.MessageDefinition;

class NativeCSharpGeneratorTest {

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
        NativeCSharpGenerator generator = new NativeCSharpGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File csFile = new File(tempDir, "NativeLogon.cs");
        assertTrue(csFile.exists());
    }
    
    @Test
    void testGeneratedFileContainsNamespace() throws Exception {
        NativeCSharpGenerator generator = new NativeCSharpGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File csFile = new File(tempDir, "NativeLogon.cs");
        String content = Files.readString(csFile.toPath());
        
        assertTrue(content.contains("namespace A5X.Trading.Native.Model"));
    }
    
    @Test
    void testGeneratedFileContainsClassName() throws Exception {
        NativeCSharpGenerator generator = new NativeCSharpGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File csFile = new File(tempDir, "NativeLogon.cs");
        String content = Files.readString(csFile.toPath());
        
        assertTrue(content.contains("public class NativeLogon"));
    }
    
    @Test
    void testGeneratedFileContainsProperties() throws Exception {
        NativeCSharpGenerator generator = new NativeCSharpGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File csFile = new File(tempDir, "NativeLogon.cs");
        String content = Files.readString(csFile.toPath());
        
        assertTrue(content.contains("{ get; set; }"));
    }
    
    @Test
    void testGeneratedFileContainsParseMethod() throws Exception {
        NativeCSharpGenerator generator = new NativeCSharpGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File csFile = new File(tempDir, "NativeLogon.cs");
        String content = Files.readString(csFile.toPath());
        
        assertTrue(content.contains("public static void Parse"));
        assertTrue(content.contains("Span<byte>"));
    }
    
    @Test
    void testGeneratedFileContainsEncodeMethod() throws Exception {
        NativeCSharpGenerator generator = new NativeCSharpGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File csFile = new File(tempDir, "NativeLogon.cs");
        String content = Files.readString(csFile.toPath());
        
        assertTrue(content.contains("public static void Encode"));
    }
    
    @Test
    void testGeneratedFileContainsUsings() throws Exception {
        NativeCSharpGenerator generator = new NativeCSharpGenerator();
        generator.generate(messages, tempDir.getAbsolutePath());
        
        File csFile = new File(tempDir, "NativeLogon.cs");
        String content = Files.readString(csFile.toPath());
        
        assertTrue(content.contains("using System"));
    }
}
