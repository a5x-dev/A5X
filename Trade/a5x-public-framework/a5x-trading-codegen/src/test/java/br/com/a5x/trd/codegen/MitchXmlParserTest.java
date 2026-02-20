package br.com.a5x.trd.codegen;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.a5x.trd.codegen.mitch.MitchXmlParser;
import br.com.a5x.trd.codegen.model.FieldDefinition;
import br.com.a5x.trd.codegen.model.MessageDefinition;

class MitchXmlParserTest {

    @Test
    void testParseSimpleMessage() throws Exception {
        InputStream xmlStream = getClass().getResourceAsStream("/mitch-messages.xml");
        assertNotNull(xmlStream, "XML file should exist");
        
        MitchXmlParser parser = new MitchXmlParser();
        List<MessageDefinition> messages = parser.parse(xmlStream);
        
        assertNotNull(messages);
        assertTrue(messages.size() > 0, "Should parse at least one message");
    }
    
    @Test
    void testParseMessageFields() throws Exception {
        InputStream xmlStream = getClass().getResourceAsStream("/mitch-messages.xml");
        
        MitchXmlParser parser = new MitchXmlParser();
        List<MessageDefinition> messages = parser.parse(xmlStream);
        
        MessageDefinition firstMsg = messages.get(0);
        assertNotNull(firstMsg.getName());
        assertNotNull(firstMsg.getMessageType());
        assertNotNull(firstMsg.getFields());
        assertTrue(firstMsg.getFields().size() > 0);
    }
    
    @Test
    void testParseFieldTypes() throws Exception {
        InputStream xmlStream = getClass().getResourceAsStream("/mitch-messages.xml");
        
        MitchXmlParser parser = new MitchXmlParser();
        List<MessageDefinition> messages = parser.parse(xmlStream);
        
        for (MessageDefinition msg : messages) {
            for (FieldDefinition field : msg.getFields()) {
                assertNotNull(field.getName());
                assertNotNull(field.getType());
                assertNotNull(field.getDescription());
            }
        }
    }
    
    @Test
    void testParseByteArrayWithLength() throws Exception {
        InputStream xmlStream = getClass().getResourceAsStream("/mitch-messages.xml");
        
        MitchXmlParser parser = new MitchXmlParser();
        List<MessageDefinition> messages = parser.parse(xmlStream);
        
        // Find a message with bytearray field
        boolean foundByteArray = false;
        for (MessageDefinition msg : messages) {
            for (FieldDefinition field : msg.getFields()) {
                if ("bytearray".equals(field.getType())) {
                    assertNotNull(field.getLength(), "Bytearray should have length");
                    assertTrue(field.getLength() > 0);
                    foundByteArray = true;
                }
            }
        }
        
        if (!foundByteArray) {
            System.out.println("Warning: No bytearray fields found in test XML");
        }
    }
}
