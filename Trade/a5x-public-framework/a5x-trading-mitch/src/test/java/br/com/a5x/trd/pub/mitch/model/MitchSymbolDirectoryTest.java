package br.com.a5x.trd.pub.mitch.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link MitchSymbolDirectory} class.
 * Tests symbol directory message parsing.
 */
class MitchSymbolDirectoryTest {

	@Test
	void testSymbolDirectoryToString() {
		MitchSymbolDirectory dir = new MitchSymbolDirectory();
		dir.nanosecond = 1000;
		dir.instrumentId = 12345;
		dir.symbolStatus = "T";
		
		String result = dir.toString();
		
		assertTrue(result.contains("nanosecond=1000"));
		assertTrue(result.contains("instrumentId=12345"));
		assertTrue(result.contains("symbolStatus=T"));
	}
}
