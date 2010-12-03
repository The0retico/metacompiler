package lexer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Test2 {

	
	/**Test of white space symbol
	 * 
	 */
	/*@Test
	public final void wspSymbol() {
		final Lexer lexer = new EBNFLexer(" ");
		Token token = null;// = new Token();
		assertTrue(lexer.hasNextToken());
		try {
			token = lexer.getNextToken();
		} catch (UndefinedSymbolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(Token.TokenType.DEFINITION, token.getType());
	}*/
	
	/**Test of definition symbol in EBNF "="
	 * 
	 */
	@Test
	public final void defSymbol() {
		final Lexer lexer = new EBNFLexer("=");
		Token token = null;// = new Token();
		assertTrue(lexer.hasNextToken());
		try {
			token = lexer.getNextToken();
		} catch (UndefinedSymbolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(Token.TokenType.DEFINITION, token.getType());
	}
	
	/**Test of concatenation symbol in EBNF ","
	 * 
	 */
	@Test
	public final void conSymbol() {
		final Lexer lexer = new EBNFLexer(",");
		Token token = null;// = new Token();
		assertTrue(lexer.hasNextToken());
		try {
			token = lexer.getNextToken();
		} catch (UndefinedSymbolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(Token.TokenType.CONCATENATION, token.getType());
	}
	
	/**Test of termination symbol in EBNF ";"
	 * 
	 */
	@Test
	public final void terSymbol() {
		final Lexer lexer = new EBNFLexer(";");
		Token token = null;// = new Token();
		assertTrue(lexer.hasNextToken());
		try {
			token = lexer.getNextToken();
		} catch (UndefinedSymbolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(Token.TokenType.TERMINATION, token.getType());
	}
	
	
	/**Test of alternation symbol in EBNF "|"
	 * 
	 */
	@Test
	public final void altSymbol() {
		final Lexer lexer = new EBNFLexer("|");
		Token token = null;// = new Token();
		assertTrue(lexer.hasNextToken());
		try {
			token = lexer.getNextToken();
		} catch (UndefinedSymbolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(Token.TokenType.ALTERNATION, token.getType());
	}
	
	/**Test of exception symbol in EBNF "-"
	 * 
	 */
	@Test
	public final void excSymbol() {
		final Lexer lexer = new EBNFLexer("-");
		Token token = null;// = new Token();
		assertTrue(lexer.hasNextToken());
		try {
			token = lexer.getNextToken();
		} catch (UndefinedSymbolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(Token.TokenType.EXCEPTION, token.getType());
	}
	
	/**Test of repetition symbol in EBNF "*"
	 * 
	 */
	@Test
	public final void repSymbol() {
		final Lexer lexer = new EBNFLexer("*");
		Token token = null;// = new Token();
		assertTrue(lexer.hasNextToken());
		try {
			token = lexer.getNextToken();
		} catch (UndefinedSymbolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(Token.TokenType.REPETITION, token.getType());
	}
	
}
