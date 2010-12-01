package lexer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Test2 {

	@Test
	public final void symbol() {
		final Lexer lexer = new EBNFLexer(" = , | ;");
		Token token;// = new Token();
		final Token.TokenType[] probablyInput = { Token.TokenType.DEFINITION,
				Token.TokenType.CONCATENATION, Token.TokenType.ALTERNATION,
				Token.TokenType.TERMINATION };
		final Token.TokenType[] input = new Token.TokenType[4];
		int i = 0;
		assertTrue(lexer.hasNextToken());
		while (lexer.hasNextToken()) {
			token = lexer.getNextToken();
			input[i] = token.getType();
			i++;
		}
		assertArrayEquals(probablyInput, input);
	}
}
