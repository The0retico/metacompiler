package ebnf.lexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import ebnf.lexer.IToken;
import ebnf.lexer.Terminal;

/**
 * Unit tests for the terminal tokens.
 * 
 * @author The0retico
 * 
 */
public class TerminalTest {

	/**
	 * Fixture terminal token for these tests.
	 */
	private IToken fixture;

	/**
	 * Constant for the fixture.
	 */
	private static final String VALUE = "s";

	/**
	 * Test of Terminal function equals and getValue.
	 */
	@Test
	public final void equals() {
		assertEquals(fixture, fixture);
		assertEquals(fixture, new Terminal(VALUE, 1, 0));
		assertFalse(fixture.equals(VALUE));
		assertFalse(fixture.equals(new Terminal("s1234", 1, 0)));
		assertEquals("s", fixture.getValue());
	}

	/**
	 * Test the getLength method.
	 */
	@Test
	public final void length() {
		assertEquals(VALUE.length(), fixture.getLength());
	}

	/**
	 * Prepare the fixture for tests.
	 */
	@Before
	public final void setUp() {
		fixture = new Terminal(VALUE, 1, 0);
	}

}
