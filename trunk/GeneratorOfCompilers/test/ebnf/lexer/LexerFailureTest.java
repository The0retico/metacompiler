package ebnf.lexer;

import static org.junit.Assert.assertFalse;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the lexer in failure states.
 * 
 * @author The0retico
 * 
 */
public class LexerFailureTest {
	/**
	 * Lexer with parameterized input.
	 */
	private Lexer lexer;

	/**
	 * The lexer should have no more tokens.
	 */
	@Test
	public final void hasNoNextToken() {
		assertFalse("Should have no next token", lexer.hasNext());
	}

	/**
	 * Test that there are no more tokens in the input.
	 */
	@Test(expected = NoSuchElementException.class)
	public final void noNextToken() {
		lexer.next();
	}

	/**
	 * Set up the lexer before every test, so tests do not influence each other.
	 */
	@Before
	public final void setUp() {
		lexer = new Lexer("#");
	}
}
