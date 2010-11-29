package lexer;

import java.util.Hashtable;

public class EBNFLexer implements Lexer {
	public String inputText;
	public Hashtable<Integer, Token> TableOfSymbols;
	private int position;
	private static final char WHITE_SPACE = ' ';
	
	public EBNFLexer(String input){
		inputText = input;
		position = 0;
	}

	@Override
	public boolean hasNextToken() {
		
		for( int i = position; i< inputText.length(); i++){
			if (inputText.charAt(i) != WHITE_SPACE){
				return true;
			}
		}
		return false;
	}

	@Override
	public Token getNextToken() {
		for( int i = position; i< inputText.length(); i++){
			if (inputText.charAt(i) != WHITE_SPACE){
				if (inputText.charAt(i) == '='){
					position = i + 1;
					return new Token("Definition");
				}
				if (inputText.charAt(i) == ','){
					position = i + 1;
					return new Token("Concatenation");
				}
				if (inputText.charAt(i) == ';'){
					position = i + 1;
					return new Token("Termination");
				}
				if (inputText.charAt(i) == '|'){
					position = i + 1;
					return new Token("Alternation");
				}
				if (inputText.charAt(i) != '"'){
					position = i + 1;
					return new Token("Identifier", "f");
				}
			}
			
		}
		return null;
	}
}
