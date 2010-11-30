package lexer;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Test2 {

	@Test
	public final void symbol() {
		final Lexer lexer = new EBNFLexer(" = , | ;");
		Token token;// = new Token();
		assertTrue(lexer.hasNextToken());
		while (lexer.hasNextToken()) {
			token = lexer.getNextToken();
			System.out.println(token.nameOfToken);
			// assertTrue(lexer.hasNextToken());
		}
	}
}
