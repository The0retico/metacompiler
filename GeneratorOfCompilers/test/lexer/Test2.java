package lexer;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Test2 {

	@Test
	public final void symbol() {
		final Lexer lexer = new EBNFLexer(" = , | ;");
		Token token;// = new Token();
		Token.typeOfToken[] probablyInput = {Token.typeOfToken.DEFINITION, Token.typeOfToken.CONCATENATION, Token.typeOfToken.ALTERNATION, Token.typeOfToken.TERMINATION}; 
		Token.typeOfToken[] input = new Token.typeOfToken[4];
		int i =0;
		assertTrue(lexer.hasNextToken());
		while (lexer.hasNextToken()) {
			token = lexer.getNextToken();
			input[i] = token.getMytype();
			i++;      
			//System.out.println(token.getNameOfToken());
			// assertTrue(lexer.hasNextToken());
		}
		assertArrayEquals(input,probablyInput);
	}
}
