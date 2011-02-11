package util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

public class LineColumnNumberReaderTest {

	@Test
	public final void read() throws IOException {
		final LineColumnNumberReader input = new LineColumnNumberReader(
				new StringReader("ab"));
		assertEquals('a', (char) input.read());
		assertEquals('b', (char) input.read());
		assertEquals(-1, input.read());

	}
}
