package lexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Test2 {

	/**
	 * Test of alternation symbol in EBNF "|"
	 * 
	 * @throws UndefinedSymbolException
	 *             in case "|" is not recognized
	 * 
	 */
	@Test
	public final void altSymbol() throws UndefinedSymbolException {
		final Lexer lexer = new EBNFLexer("|");
		assertTrue("Alternation symbol should be recognized as a token",
				lexer.hasNextToken());
		final Token token = lexer.getNextToken();
		assertEquals(Token.TokenType.ALTERNATION, token.getType());
	}

	/**
	 * Test of concatenation symbol in EBNF ","
	 * 
	 * @throws UndefinedSymbolException
	 *             in case "," is not recognized
	 * 
	 */
	@Test
	public final void conSymbol() throws UndefinedSymbolException {
		final Lexer lexer = new EBNFLexer(",");
		assertTrue("Concatenation symbol should be recognized as a token",
				lexer.hasNextToken());
		final Token token = lexer.getNextToken();
		assertEquals(Token.TokenType.CONCATENATION, token.getType());
	}

	/**
	 * Test of definition symbol in EBNF "="
	 * 
	 * @throws UndefinedSymbolException
	 *             in case "=" is not recognized as a symbol
	 */
	@Test
	public final void defSymbol() throws UndefinedSymbolException {
		final Lexer lexer = new EBNFLexer("=");
		assertTrue("Definition symbol should be recognized as a token",
				lexer.hasNextToken());
		final Token token = lexer.getNextToken();
		assertEquals(Token.TokenType.DEFINITION, token.getType());
	}

	/**
	 * Test of exception symbol in EBNF "-"
	 * 
	 * @throws UndefinedSymbolException
	 *             in case "-" is not recognized as a symbol
	 * 
	 */
	@Test
	public final void excSymbol() throws UndefinedSymbolException {
		final Lexer lexer = new EBNFLexer("-");
		assertTrue(lexer.hasNextToken());
		final Token token = lexer.getNextToken();
		assertEquals(Token.TokenType.EXCEPTION, token.getType());
	}

	/**
	 * Test of repetition symbol in EBNF "*"
	 * 
	 * @throws UndefinedSymbolException
	 *             in case "*" is not recognized as a symbol
	 * 
	 */
	@Test
	public final void repSymbol() throws UndefinedSymbolException {
		final Lexer lexer = new EBNFLexer("*");
		assertTrue(lexer.hasNextToken());
		final Token token = lexer.getNextToken();
		assertEquals(Token.TokenType.REPETITION, token.getType());
	}

	/**
	 * Test of termination symbol in EBNF ";"
	 * 
	 * @throws UndefinedSymbolException
	 *             in case ";" is not recognized as a symbol
	 * 
	 */
	@Test
	public final void terSymbol() throws UndefinedSymbolException {
		final Lexer lexer = new EBNFLexer(";");
		assertTrue(lexer.hasNextToken());
		final Token token = lexer.getNextToken();
		assertEquals(Token.TokenType.TERMINATION, token.getType());
	}
}
