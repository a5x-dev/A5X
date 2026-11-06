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
    
    private static final String SIGN_MAGNITUDE_XML =
        "<messages xmlns=\"http://a5x.com.br/mitch\"><types byteOrder=\"little-endian\">"
        + "<type name=\"price8\" wireSize=\"8\" encoding=\"int64-le\" negativeEncoding=\"%s\"/>"
        + "<type name=\"uint32\" wireSize=\"4\" encoding=\"unsigned-integer-le\"/></types>"
        + "<message name=\"MitchX\" messageType=\"0x41\">"
        + "<field name=\"price\" type=\"price8\"/><field name=\"quantity\" type=\"uint32\"/></message></messages>";

    private static List<MessageDefinition> parseInline(String xml) throws Exception {
        return new MitchXmlParser().parse(new java.io.ByteArrayInputStream(xml.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
    }

    @Test
    void testSignMagnitudeComesFromTheType() throws Exception {
        List<FieldDefinition> fields = parseInline(String.format(SIGN_MAGNITUDE_XML, "sign-magnitude")).get(0).getFields();
        assertTrue(fields.get(0).isSignMagnitude(), "price8 declares sign-magnitude");
        assertFalse(fields.get(1).isSignMagnitude(), "uint32 declares nothing");

        assertFalse(parseInline(String.format(SIGN_MAGNITUDE_XML, "twos-complement")).get(0).getFields().get(0).isSignMagnitude());
    }

    @Test
    void testUnknownNegativeEncodingIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> parseInline(String.format(SIGN_MAGNITUDE_XML, "ones-complement")));
    }

    @Test
    void testPublishedSchemaPricesAreSignMagnitude() throws Exception {
        // Read the schema file that is published, not whatever the classpath resolves.
        try (InputStream xmlStream = new java.io.FileInputStream("src/main/resources/mitch-messages.xml")) {
            for (MessageDefinition msg : new MitchXmlParser().parse(xmlStream)) {
                for (FieldDefinition field : msg.getFields()) {
                    if ("price4".equals(field.getType()) || "price8".equals(field.getType())) {
                        assertTrue(field.isSignMagnitude(), msg.getName() + "." + field.getName());
                    }
                }
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
