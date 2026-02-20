package br.com.a5x.trd.codegen;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import br.com.a5x.trd.codegen.mitch.MitchCSharpGenerator;
import br.com.a5x.trd.codegen.mitch.MitchCodeGenerator;
import br.com.a5x.trd.codegen.mitch.MitchCppGenerator;
import br.com.a5x.trd.codegen.mitch.MitchWiresharkGenerator;
import br.com.a5x.trd.codegen.mitch.MitchXmlParser;
import br.com.a5x.trd.codegen.model.MessageDefinition;

class CodeGeneratorIntegrationTest {

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
    void testGenerateAllLanguages() throws Exception {
        File javaDir = new File(tempDir, "java");
        File csharpDir = new File(tempDir, "csharp");
        File cppDir = new File(tempDir, "cpp");
        File wiresharkDir = new File(tempDir, "wireshark");
        
        new MitchCodeGenerator().generate(messages, javaDir.getAbsolutePath());
        new MitchCSharpGenerator().generate(messages, csharpDir.getAbsolutePath());
        new MitchCppGenerator().generate(messages, cppDir.getAbsolutePath());
        new MitchWiresharkGenerator().generate(messages, wiresharkDir.getAbsolutePath());
        
        // Verify Java files
        File[] javaFiles = javaDir.listFiles((dir, name) -> name.endsWith(".java"));
        assertNotNull(javaFiles);
        assertEquals(messages.size(), javaFiles.length);
        
        // Verify C# files
        File[] csFiles = csharpDir.listFiles((dir, name) -> name.endsWith(".cs"));
        assertNotNull(csFiles);
        assertEquals(messages.size(), csFiles.length);
        
        // Verify C++ files
        File[] cppFiles = cppDir.listFiles((dir, name) -> name.endsWith(".h"));
        assertNotNull(cppFiles);
        assertEquals(messages.size(), cppFiles.length);
        
        // Verify Wireshark file
        File luaFile = new File(wiresharkDir, "mitch_dissector.lua");
        assertTrue(luaFile.exists());
    }
    
    @Test
    void testAllGeneratorsProduceSameMessageCount() throws Exception {
        File javaDir = new File(tempDir, "java");
        File csharpDir = new File(tempDir, "csharp");
        File cppDir = new File(tempDir, "cpp");
        
        new MitchCodeGenerator().generate(messages, javaDir.getAbsolutePath());
        new MitchCSharpGenerator().generate(messages, csharpDir.getAbsolutePath());
        new MitchCppGenerator().generate(messages, cppDir.getAbsolutePath());
        
        int javaCount = javaDir.listFiles((dir, name) -> name.endsWith(".java")).length;
        int csCount = csharpDir.listFiles((dir, name) -> name.endsWith(".cs")).length;
        int cppCount = cppDir.listFiles((dir, name) -> name.endsWith(".h")).length;
        
        assertEquals(javaCount, csCount);
        assertEquals(csCount, cppCount);
        assertEquals(messages.size(), javaCount);
    }
    
    @Test
    void testGeneratedFilesHaveSameNames() throws Exception {
        File javaDir = new File(tempDir, "java");
        File csharpDir = new File(tempDir, "csharp");
        File cppDir = new File(tempDir, "cpp");
        
        new MitchCodeGenerator().generate(messages, javaDir.getAbsolutePath());
        new MitchCSharpGenerator().generate(messages, csharpDir.getAbsolutePath());
        new MitchCppGenerator().generate(messages, cppDir.getAbsolutePath());
        
        for (MessageDefinition msg : messages) {
            assertTrue(new File(javaDir, msg.getName() + ".java").exists());
            assertTrue(new File(csharpDir, msg.getName() + ".cs").exists());
            assertTrue(new File(cppDir, msg.getName() + ".h").exists());
        }
    }
    
    @Test
    void testCompleteXmlGeneration() throws Exception {
        InputStream xmlStream = getClass().getResourceAsStream("/mitch-messages-complete.xml");
        if (xmlStream == null) {
            System.out.println("Skipping complete XML test - file not found");
            return;
        }
        
        MitchXmlParser parser = new MitchXmlParser();
        List<MessageDefinition> completeMessages = parser.parse(xmlStream);
        
        assertTrue(completeMessages.size() >= messages.size(), 
            "Complete XML should have at least as many messages as simple XML");
        
        File outputDir = new File(tempDir, "complete");
        new MitchCodeGenerator().generate(completeMessages, outputDir.getAbsolutePath());
        
        File[] files = outputDir.listFiles((dir, name) -> name.endsWith(".java"));
        assertNotNull(files);
        assertEquals(completeMessages.size(), files.length);
    }
}
