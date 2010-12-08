package lexer.ebnf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LexerTest2 {
	/**
	 * Lexer with parameterized input.
	 */
	private final Lexer lexer = new Lexer("#");

	/**
	 * Type of the token for the symbol in the input of lexer.
	 */
	private final IToken token = new Identifier("#");

	/**
	 * Test of exception, when input is not a Token.
	 */
	@Test(expected = Exception.class)
	public final void lexerException() throws Exception {
		assertTrue(lexer.hasNextToken());
		assertEquals(token, lexer.getNextToken());
	}

	/**
	 * Test of Identifier function equals and getValue.
	 */
	@Test
	public final void identifierTest() throws Exception {
		final Lexer lexer1 = new Lexer("s");
		final IToken badToken = new Terminal("sa");
		final IToken nullToken = new Identifier(null);
		final IToken otherToken = new Identifier("s");
		IToken myToken = lexer1.getNextToken();
		assertTrue(myToken.equals(otherToken));
		assertTrue(myToken.equals(myToken));
		assertFalse(myToken.equals(null));
		assertFalse(myToken.equals(badToken));
		assertFalse(nullToken.equals(otherToken));
		assertFalse(myToken.equals(new Identifier("s1234")));
		assertEquals("s", myToken.getValue());
	}

	/**
	 * Test of Terminal function equals and getValue.
	 */
	@Test
	public final void TerminalTest() throws Exception {
		final Lexer lexer1 = new Lexer("'s'");
		final IToken badToken = new Identifier("sa");
		final IToken nullToken = new Terminal(null);
		final IToken otherToken = new Terminal("s");
		IToken myToken = lexer1.getNextToken();
		assertTrue(myToken.equals(otherToken));
		assertTrue(myToken.equals(myToken));
		assertFalse(myToken.equals(null));
		assertFalse(myToken.equals(badToken));
		assertFalse(nullToken.equals(otherToken));
		assertFalse(myToken.equals(new Terminal("'s1234'")));
		assertEquals("s", myToken.getValue());
	}
	
	/**
	 * Test of Special sequence function equals and getValue.
	 */
	@Test
	public final void SpecialTest() throws Exception {
		final Lexer lexer1 = new Lexer("?s?");
		final IToken badToken = new Identifier("sa");
		final IToken nullToken = new Special(null);
		final IToken otherToken = new Special("s");
		IToken myToken = lexer1.getNextToken();
		assertTrue(myToken.equals(otherToken));
		assertTrue(myToken.equals(myToken));
		assertFalse(myToken.equals(null));
		assertFalse(myToken.equals(badToken));
		assertFalse(nullToken.equals(otherToken));
		assertFalse(myToken.equals(new Special("?s1234?")));
		assertEquals("s", myToken.getValue());
	}
	
	/**
	 * Test of Number function equals and getValue.
	 */
	@Test
	public final void NumberTest() throws Exception {
		final Lexer lexer1 = new Lexer("12");
		final IToken badToken = new Identifier("sa");
		final IToken otherToken = new Number(12);
		IToken myToken = lexer1.getNextToken();
		assertTrue(myToken.equals(otherToken));
		assertTrue(myToken.equals(myToken));
		assertFalse(myToken.equals(null));
		assertFalse(myToken.equals(badToken));
		assertFalse(myToken.equals(new Number(1234)));
		assertEquals("12", myToken.getValue());
	}
}
