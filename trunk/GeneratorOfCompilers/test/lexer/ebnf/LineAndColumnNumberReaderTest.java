package lexer.ebnf;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;

public class LineAndColumnNumberReaderTest {

	/**
	 * @param line
	 *            number at which is the reader
	 * @param column
	 *            number at which is the reader
	 * @param reader
	 *            which coutns lines and columns
	 * 
	 */
	private static void assertAt(final int line, final int column,
			final LineAndColumnNumberReader reader) {
		assertEquals(line, reader.getLineNumber());
		assertEquals(column, reader.getColumnNumber());
	}

	private LineAndColumnNumberReader fixture;

	@Test
	public void bufferedRead() throws IOException {
		final char[] buffer = new char[8];
		assertAt(1, 0, fixture);
		fixture.read(buffer, 0, 3);
		assertAt(1, 3, fixture);
		fixture.read(buffer, 0, 3);
		assertAt(2, 1, fixture);
		fixture.read(buffer, 0, 3);
		assertAt(4, 0, fixture);
		fixture.read(buffer, 0, 1);
		assertAt(4, 0, fixture);
		fixture.read(buffer, 0, 1);
		assertAt(4, 1, fixture);
		fixture.read(buffer, 0, 1);
		assertAt(4, 1, fixture);
	}

	@Test
	public void oneCharRead() throws IOException {
		assertAt(1, 0, fixture);
		fixture.read();
		assertAt(1, 1, fixture);
		fixture.read();
		assertAt(1, 2, fixture);
		fixture.read();
		assertAt(1, 3, fixture);
		fixture.read();
		assertAt(1, 4, fixture);
		fixture.read();
		assertAt(2, 0, fixture);
		fixture.read();
		assertAt(2, 1, fixture);
		fixture.read();
		assertAt(3, 0, fixture);
		fixture.read();
		assertAt(3, 1, fixture);
		fixture.read();
		assertAt(4, 0, fixture);
		fixture.read();
		assertAt(4, 1, fixture);
		fixture.read();
		assertAt(4, 1, fixture);
	}

	@Test
	public void readLine() throws IOException {
		assertAt(1, 0, fixture);
		fixture.readLine();
		assertAt(2, 0, fixture);
		fixture.readLine();
		assertAt(3, 0, fixture);
		fixture.readLine();
		assertAt(4, 0, fixture);
		fixture.readLine();
		assertAt(5, 0, fixture);
	}

	@Before
	public void setUp() {
		final Reader reader = new StringReader(" \ta1\ra\na\r\na");
		fixture = new LineAndColumnNumberReader(reader);
	}

	@Test
	public void skip() throws IOException {
		assertAt(1, 0, fixture);
		fixture.skip(3);
		assertAt(1, 3, fixture);
		fixture.skip(3);
		assertAt(2, 1, fixture);
		fixture.skip(3);
		assertAt(4, 0, fixture);
		fixture.skip(1);
		assertAt(4, 0, fixture);
		fixture.skip(1);
		assertAt(4, 1, fixture);
		fixture.skip(1);
		assertAt(4, 1, fixture);
	}
}
