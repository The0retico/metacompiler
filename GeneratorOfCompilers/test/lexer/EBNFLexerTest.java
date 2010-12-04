package lexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import lexer.EBNFToken.UndefinedSymbolException;

import org.junit.Test;

public class EBNFLexerTest {

	/**
	 * Test of alternation symbol in EBNF "|".
	 * 
	 * @throws UndefinedSymbolException
	 *             in case "|" is not recognized
	 * 
	 */
	@Test
	public final void altSymbol() throws UndefinedSymbolException {
		final EBNFLexer lexer = new EBNFLexer("|");
		assertTrue("Alternation symbol should be recognized as a token",
				lexer.hasNextToken());
		assertEquals(EBNFToken.ALTERNATION, lexer.getNextToken());
		assertFalse("Lexer should not offer any more tokens",
				lexer.hasNextToken());
	}

	/**
	 * Test of concatenation symbol in EBNF ",".
	 * 
	 * @throws UndefinedSymbolException
	 *             in case "," is not recognized
	 * 
	 */
	@Test
	public final void conSymbol() throws UndefinedSymbolException {
		final EBNFLexer lexer = new EBNFLexer(",");
		assertTrue("Concatenation symbol should be recognized as a token",
				lexer.hasNextToken());
		final EBNFToken token = lexer.getNextToken();
		assertEquals(EBNFToken.CONCATENATION, token);
		assertFalse("Lexer should not offer any more tokens",
				lexer.hasNextToken());
	}

	/**
	 * Test of definition symbol in EBNF "=".
	 * 
	 * @throws UndefinedSymbolException
	 *             in case "=" is not recognized as a symbol
	 */
	@Test
	public final void defSymbol() throws UndefinedSymbolException {
		final EBNFLexer lexer = new EBNFLexer("=");
		assertTrue("Definition symbol should be recognized as a token",
				lexer.hasNextToken());
		final EBNFToken token = lexer.getNextToken();
		assertEquals(EBNFToken.DEFINITION, token);
		assertFalse("Lexer should not offer any more tokens",
				lexer.hasNextToken());
	}

	/**
	 * Test of exception symbol in EBNF "-".
	 * 
	 * @throws UndefinedSymbolException
	 *             in case "-" is not recognized as a symbol
	 * 
	 */
	@Test
	public final void excSymbol() throws UndefinedSymbolException {
		final EBNFLexer lexer = new EBNFLexer("-");
		assertTrue("Exception symbol should be recognized as a token",
				lexer.hasNextToken());
		assertEquals(EBNFToken.EXCEPTION, lexer.getNextToken());
		assertFalse("Lexer should not offer any more tokens",
				lexer.hasNextToken());
	}

	/**
	 * Test of repetition symbol in EBNF "*".
	 * 
	 * @throws UndefinedSymbolException
	 *             in case "*" is not recognized as a symbol
	 * 
	 */
	@Test
	public final void repSymbol() throws UndefinedSymbolException {
		final EBNFLexer lexer = new EBNFLexer("*");
		assertTrue("Repetition symbol should be recognized as a token",
				lexer.hasNextToken());
		assertEquals(EBNFToken.REPETITION, lexer.getNextToken());
		assertFalse("Lexer should not offer any more tokens",
				lexer.hasNextToken());
	}

	/**
	 * Test of termination symbol in EBNF ";".
	 * 
	 * @throws UndefinedSymbolException
	 *             in case ";" is not recognized as a symbol
	 * 
	 */
	@Test
	public final void terSymbol() throws UndefinedSymbolException {
		final EBNFLexer lexer = new EBNFLexer(";");
		assertTrue("Termination symbol should be recognized as a token",
				lexer.hasNextToken());
		assertEquals(EBNFToken.TERMINATION, lexer.getNextToken());
		assertFalse("Lexer should not offer any more tokens",
				lexer.hasNextToken());
	}
	
	/**
	 * Test of left option symbol in EBNF ";".
	 * 
	 * @throws UndefinedSymbolException
	 *             in case "[" is not recognized as a symbol
	 * 
	 */
	@Test
	public final void lOptSymbol() throws UndefinedSymbolException {
		final EBNFLexer lexer = new EBNFLexer("[");
		assertTrue("LTOPTION symbol should be recognized as a token",
				lexer.hasNextToken());
		assertEquals(EBNFToken.LTOPTION, lexer.getNextToken());
		assertFalse("Lexer should not offer any more tokens",
				lexer.hasNextToken());
	}
	
	/**
	 * Test of right option symbol in EBNF ";".
	 * 
	 * @throws UndefinedSymbolException
	 *             in case "]" is not recognized as a symbol
	 * 
	 */
	@Test
	public final void rOptSymbol() throws UndefinedSymbolException {
		final EBNFLexer lexer = new EBNFLexer("]");
		assertTrue("RTOPTION symbol should be recognized as a token",
				lexer.hasNextToken());
		assertEquals(EBNFToken.RTOPTION, lexer.getNextToken());
		assertFalse("Lexer should not offer any more tokens",
				lexer.hasNextToken());
	}	
	
	/**
	 * Test of left repetition symbol in EBNF ";".
	 * 
	 * @throws UndefinedSymbolException
	 *             in case "{" is not recognized as a symbol
	 * 
	 */
	@Test
	public final void lRepSymbol() throws UndefinedSymbolException {
		final EBNFLexer lexer = new EBNFLexer("{");
		assertTrue("LTREP symbol should be recognized as a token",
				lexer.hasNextToken());
		assertEquals(EBNFToken.LTREP, lexer.getNextToken());
		assertFalse("Lexer should not offer any more tokens",
				lexer.hasNextToken());
	}
	
	/**
	 * Test of right repetition symbol in EBNF ";".
	 * 
	 * @throws UndefinedSymbolException
	 *             in case "}" is not recognized as a symbol
	 * 
	 */
	@Test
	public final void rRepSymbol() throws UndefinedSymbolException {
		final EBNFLexer lexer = new EBNFLexer("}");
		assertTrue("RTREP symbol should be recognized as a token",
				lexer.hasNextToken());
		assertEquals(EBNFToken.RTREP, lexer.getNextToken());
		assertFalse("Lexer should not offer any more tokens",
				lexer.hasNextToken());
	}
	
	/**
	 * Test of left grouping symbol in EBNF ";".
	 * 
	 * @throws UndefinedSymbolException
	 *             in case "(" is not recognized as a symbol
	 * 
	 */
	@Test
	public final void lGroupSymbol() throws UndefinedSymbolException {
		final EBNFLexer lexer = new EBNFLexer("(");
		assertTrue("LTGROUP symbol should be recognized as a token",
				lexer.hasNextToken());
		assertEquals(EBNFToken.LTGROUP, lexer.getNextToken());
		assertFalse("Lexer should not offer any more tokens",
				lexer.hasNextToken());
	}
	
	/**
	 * Test of right grouping symbol in EBNF ";".
	 * 
	 * @throws UndefinedSymbolException
	 *             in case ")" is not recognized as a symbol
	 * 
	 */
	@Test
	public final void rGroupSymbol() throws UndefinedSymbolException {
		final EBNFLexer lexer = new EBNFLexer(")");
		assertTrue("RTGROUP symbol should be recognized as a token",
				lexer.hasNextToken());
		assertEquals(EBNFToken.RTGROUP, lexer.getNextToken());
		assertFalse("Lexer should not offer any more tokens",
				lexer.hasNextToken());
	}
	
	
}
