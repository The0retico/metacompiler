package lexer;

import static org.junit.Assert.*;

import org.junit.Test;



public class Test2 {
	
	@Test
	public void Symbol(){
		Lexer lexer = new EBNFLexer(" = , | ;");
		Token token;// = new Token();
		assertTrue(lexer.hasNextToken());
		while (lexer.hasNextToken()){
			token = lexer.getNextToken();
			System.out.println(token.nameOfToken);
			//assertTrue(lexer.hasNextToken());
		}
	}
}
