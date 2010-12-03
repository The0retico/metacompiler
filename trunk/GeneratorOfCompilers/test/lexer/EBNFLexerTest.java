package lexer;

import static org.junit.Assert.assertEquals;
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
	}
}
