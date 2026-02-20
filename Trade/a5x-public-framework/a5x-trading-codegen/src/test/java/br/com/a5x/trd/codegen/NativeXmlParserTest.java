package br.com.a5x.trd.codegen;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.a5x.trd.codegen.nativ.NativeXmlParser;
import br.com.a5x.trd.codegen.model.MessageDefinition;

class NativeXmlParserTest {

    @Test
    void testParseSimpleMessage() throws Exception {
        InputStream xmlStream = getClass().getResourceAsStream("/native-messages.xml");
        assertNotNull(xmlStream, "XML file should exist");
        
        NativeXmlParser parser = new NativeXmlParser();
        List<MessageDefinition> messages = parser.parse(xmlStream);
        
        assertNotNull(messages);
        assertEquals(2, messages.size());
    }
    
    @Test
    void testParseMessageFields() throws Exception {
        InputStream xmlStream = getClass().getResourceAsStream("/native-messages.xml");
        NativeXmlParser parser = new NativeXmlParser();
        List<MessageDefinition> messages = parser.parse(xmlStream);
        
        MessageDefinition msg = messages.get(0);
        assertEquals("NativeLogon", msg.getName());
        assertEquals(3, msg.getFields().size());
    }
    
    @Test
    void testParseFieldTypes() throws Exception {
        InputStream xmlStream = getClass().getResourceAsStream("/native-messages.xml");
        NativeXmlParser parser = new NativeXmlParser();
        List<MessageDefinition> messages = parser.parse(xmlStream);
        
        MessageDefinition msg = messages.get(0);
        assertEquals("alpha", msg.getFields().get(0).getType());
        assertEquals("int32", msg.getFields().get(2).getType());
    }
    
    @Test
    void testParseAlphaWithLength() throws Exception {
        InputStream xmlStream = getClass().getResourceAsStream("/native-messages.xml");
        NativeXmlParser parser = new NativeXmlParser();
        List<MessageDefinition> messages = parser.parse(xmlStream);
        
        MessageDefinition msg = messages.get(0);
        assertEquals(11, msg.getFields().get(0).getLength());
        assertEquals(25, msg.getFields().get(1).getLength());
    }
}
