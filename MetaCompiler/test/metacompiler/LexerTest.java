package metacompiler;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;

import util.LineColumnNumberReader;

public class LexerTest {

	private LineColumnNumberReader input;
	private Lexer lexer;

	@Test
	public final void match() throws IOException, LexerException {
		assertTrue(lexer.match("\'aa\'", input));
	}

	@Test(expected = LexerException.class)
	public final void noMatch() throws IOException, LexerException {
		lexer.match("'aab'", input);
	}

	@Before
	public final void setUp() {
		lexer = new Lexer();
		input = new LineColumnNumberReader(new StringReader("aaa"));
	}
}
