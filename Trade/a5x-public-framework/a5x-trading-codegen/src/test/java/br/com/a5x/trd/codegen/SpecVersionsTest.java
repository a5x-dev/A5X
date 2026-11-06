package br.com.a5x.trd.codegen;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import br.com.a5x.trd.codegen.mitch.MitchXmlParser;
import br.com.a5x.trd.codegen.model.MessageDefinition;
import br.com.a5x.trd.codegen.nativ.NativeXmlParser;

/**
 * spec/ holds the schemas of every published version of the specification, one directory per
 * version. src/main/resources is the version clients run today and must be one of them.
 */
class SpecVersionsTest {

    private static final Path SPEC = Path.of("spec");
    private static final Path RESOURCES = Path.of("src", "main", "resources");
    private static final String[] SCHEMAS = {
        "mitch-messages.xml", "native-messages.xml", "fix-oe-messages.xml", "fix-dc-messages.xml",
        "mitch-messages.xsd", "native-messages.xsd", "reject-codes.xml", "reject-codes.xsd" };

    @TempDir
    File tempDir;

    private static List<Path> versions() throws Exception {
        try (Stream<Path> children = Files.list(SPEC)) {
            return children.filter(Files::isDirectory).sorted().toList();
        }
    }

    @Test
    void everyVersionHasEverySchema() throws Exception {
        assertFalse(versions().isEmpty());
        for (Path version : versions()) {
            for (String schema : SCHEMAS) {
                assertTrue(Files.exists(version.resolve(schema)), version + "/" + schema);
            }
        }
    }

    @Test
    void currentResourcesAreOneOfTheVersions() throws Exception {
        long matches = 0;
        for (Path version : versions()) {
            boolean same = true;
            for (String schema : SCHEMAS) {
                same &= Files.mismatch(version.resolve(schema), RESOURCES.resolve(schema)) == -1;
            }
            if (same) matches++;
        }
        assertEquals(1, matches, "src/main/resources must be identical to exactly one spec/<version>");
    }

    @Test
    void everyVersionParses() throws Exception {
        for (Path version : versions()) {
            try (InputStream in = Files.newInputStream(version.resolve("mitch-messages.xml"))) {
                List<MessageDefinition> messages = new MitchXmlParser().parse(in);
                assertFalse(messages.isEmpty(), version.toString());
            }
            try (InputStream in = Files.newInputStream(version.resolve("native-messages.xml"))) {
                List<MessageDefinition> messages = new NativeXmlParser().parse(in);
                assertFalse(messages.isEmpty(), version.toString());
            }
        }
    }

    @Test
    void generatesOneTreePerVersion() throws Exception {
        assertTrue(A5XCodeGenApp.generateVersions(SPEC, tempDir.toPath(), "java"));
        for (Path version : versions()) {
            for (String protocol : new String[] { "mitch", "native" }) {
                File java = tempDir.toPath().resolve(version.getFileName().toString()).resolve(protocol).resolve("java").toFile();
                String[] files = java.list((dir, name) -> name.endsWith(".java"));
                assertNotNull(files, java.toString());
                assertTrue(files.length > 0, java.toString());
            }
        }
    }
}
