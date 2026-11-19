package br.com.a5x.trd.codegen;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import br.com.a5x.trd.codegen.A5XCodeGenApp.Options;

/** The command line of the generator and how it tells the protocols apart. */
class A5XCodeGenAppTest {

    private static final Path RESOURCES = Path.of("src", "main", "resources");

    @TempDir
    File tempDir;

    @Test
    void testDefaults() {
        Options options = Options.parse();
        assertEquals("/mitch-messages.xml", options.schema());
        assertEquals("./generated", options.outputDir());
        assertEquals("java", options.language());
        assertFalse(options.help());
    }

    @Test
    void testPositionalArgumentsKeepTheirOrder() {
        Options options = Options.parse("spec", "out", "cpp");
        assertEquals("spec", options.schema());
        assertEquals("out", options.outputDir());
        assertEquals("cpp", options.language());
    }

    @Test
    void testNamedOptionsInAnyOrder() {
        Options options = Options.parse("--lang", "csharp", "--out", "build/sdk", "--schema", "spec");
        assertEquals("spec", options.schema());
        assertEquals("build/sdk", options.outputDir());
        assertEquals("csharp", options.language());

        options = Options.parse("-l", "cpp", "-s", "native.xml", "-o", "x");
        assertEquals("native.xml", options.schema());
        assertEquals("x", options.outputDir());
        assertEquals("cpp", options.language());

        options = Options.parse("--language=all", "--output=dir");
        assertEquals("all", options.language());
        assertEquals("dir", options.outputDir());
    }

    @Test
    void testLanguageWithoutOutputDirectoryKeepsTheDefault() {
        Options options = Options.parse("/native-messages.xml", "--lang", "cpp");
        assertEquals("/native-messages.xml", options.schema());
        assertEquals("./generated", options.outputDir());
        assertEquals("cpp", options.language());
    }

    @Test
    void testInvalidCommandLines() {
        assertThrows(IllegalArgumentException.class, () -> Options.parse("--color", "red"));
        assertThrows(IllegalArgumentException.class, () -> Options.parse("--lang"));
        assertThrows(IllegalArgumentException.class, () -> Options.parse("--lang", "java", "--lang", "cpp"));
        assertThrows(IllegalArgumentException.class, () -> Options.parse("spec", "--schema", "other"));
        assertThrows(IllegalArgumentException.class, () -> Options.parse("a", "b", "c", "d"));
        assertEquals(2, A5XCodeGenApp.run("--color", "red"));
        assertEquals(0, A5XCodeGenApp.run("--help"));
    }

    /** The protocol comes from the root namespace: the file name says nothing. */
    @Test
    void testProtocolFromTheRootNamespace() throws Exception {
        Path mitch = tempDir.toPath().resolve("native-but-really-mitch.xml");
        Files.copy(RESOURCES.resolve("mitch-messages.xml"), mitch);
        Path orders = tempDir.toPath().resolve("orders.xml");
        Files.copy(RESOURCES.resolve("native-messages.xml"), orders);

        Path out = tempDir.toPath().resolve("out");
        assertEquals(0, A5XCodeGenApp.run("--schema", mitch.toString(), "--out", out.resolve("a").toString()));
        assertTrue(Files.exists(out.resolve("a/java/MitchEventWrapper.java")));
        assertFalse(Files.exists(out.resolve("a/java/NativeEventWrapper.java")));

        assertEquals(0, A5XCodeGenApp.run("--schema", orders.toString(), "--out", out.resolve("b").toString()));
        assertTrue(Files.exists(out.resolve("b/java/NativeEventWrapper.java")));
        assertFalse(Files.exists(out.resolve("b/java/MitchEventWrapper.java")));
    }

    @Test
    void testRootNamespace() throws Exception {
        assertEquals(A5XCodeGenApp.MITCH_NAMESPACE,
                A5XCodeGenApp.rootNamespace(Files.readAllBytes(RESOURCES.resolve("mitch-messages.xml"))));
        assertEquals(A5XCodeGenApp.NATIVE_NAMESPACE,
                A5XCodeGenApp.rootNamespace(Files.readAllBytes(RESOURCES.resolve("native-messages.xml"))));
        assertEquals("",
                A5XCodeGenApp.rootNamespace(Files.readAllBytes(RESOURCES.resolve("fix-oe-messages.xml"))));
        assertEquals(A5XCodeGenApp.NATIVE_NAMESPACE, A5XCodeGenApp.rootNamespace(
                "<n:messages xmlns:n=\"http://a5x.com.br/native\"/>".getBytes(StandardCharsets.UTF_8)));
    }

    /** A QuickFIX dictionary is not a MITCH or Native schema: refused, nothing generated. */
    @Test
    void testFixDictionaryIsRefused() throws Exception {
        Path out = tempDir.toPath().resolve("fix");
        assertFalse(A5XCodeGenApp.generate(RESOURCES.resolve("fix-oe-messages.xml").toString(), out.toString(), "java"));
        assertFalse(Files.exists(out));
    }
}
