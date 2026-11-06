package br.com.a5x.trd.codegen;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import br.com.a5x.trd.codegen.nativ.NativeXmlParser;
import br.com.a5x.trd.codegen.model.MessageDefinition;
import br.com.a5x.trd.codegen.model.FieldDefinition;

/**
 * Exercises the parser against the schema this module ships, which is the one the
 * generator actually reads. An earlier version of this test asserted counts taken
 * from a small fixture that no longer exists -- the resource lookup fell through to
 * the real schema and the test failed on every build. The assertions below name the
 * message and the fields they check instead of indexing into the list, so adding a
 * message to the schema cannot break them.
 */
class NativeXmlParserTest {

    private List<MessageDefinition> parseShippedSchema() throws Exception {
        try (InputStream xml = getClass().getResourceAsStream("/native-messages.xml")) {
            assertNotNull(xml, "native-messages.xml should be on the classpath");
            return new NativeXmlParser().parse(xml);
        }
    }

    private MessageDefinition messageNamed(List<MessageDefinition> messages, String name) {
        return messages.stream()
                .filter(message -> name.equals(message.getName()))
                .findFirst()
                .orElseGet(() -> fail("schema should declare message " + name));
    }

    private FieldDefinition fieldNamed(MessageDefinition message, String name) {
        return message.getFields().stream()
                .filter(field -> name.equals(field.getName()))
                .findFirst()
                .orElseGet(() -> fail(message.getName() + " should declare field " + name));
    }

    @Test
    void parsesEveryMessageInTheShippedSchema() throws Exception {
        List<MessageDefinition> messages = parseShippedSchema();

        assertFalse(messages.isEmpty(), "the shipped schema declares messages");

        Set<String> names = new HashSet<>();
        List<String> duplicates = new ArrayList<>();
        List<String> empty = new ArrayList<>();
        for (MessageDefinition message : messages) {
            assertNotNull(message.getName(), "every message carries a name");
            assertFalse(message.getName().isBlank(), "every message carries a name");
            assertNotNull(message.getFields(), message.getName() + " carries a field list");
            if (message.getFields().isEmpty()) {
                empty.add(message.getName());
            }
            if (!names.add(message.getName())) {
                duplicates.add(message.getName());
            }
        }
        assertTrue(duplicates.isEmpty(), "message names are unique, found duplicates: " + duplicates);

        // NativeHeartbeat is a bare header and carries no fields; nothing else may be empty.
        assertEquals(List.of("NativeHeartbeat"), empty, "only the heartbeat is a message without fields");
    }

    @Test
    void parsesLogonFieldsInWireOrder() throws Exception {
        MessageDefinition logon = messageNamed(parseShippedSchema(), "NativeLogon");

        List<String> actual = logon.getFields().stream().map(FieldDefinition::getName).toList();
        assertEquals(
                List.of("compId", "password", "newPassword", "protocolVersion", "ipAddress", "certifiedSolution"),
                actual,
                "field order is the wire order and must not be rearranged");
    }

    @Test
    void parsesFieldTypes() throws Exception {
        MessageDefinition logon = messageNamed(parseShippedSchema(), "NativeLogon");

        assertEquals("alpha", fieldNamed(logon, "compId").getType());
        assertEquals("int32", fieldNamed(logon, "protocolVersion").getType());
    }

    @Test
    void parsesAlphaLength() throws Exception {
        MessageDefinition logon = messageNamed(parseShippedSchema(), "NativeLogon");

        assertEquals(11, fieldNamed(logon, "compId").getLength());
        assertEquals(25, fieldNamed(logon, "password").getLength());
    }
}
