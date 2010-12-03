package lexer;

import java.util.Map;

public class EBNFLexer implements Lexer {
	private final String inputText;
	public Map<Integer, Token> tableOfSymbols;

	private int position;
	private static final char WHITE_SPACE = ' ';

	public EBNFLexer(final String input) {
		inputText = input;
		position = 0;
	}

	/**
	 * @return the inputText
	 */
	public String getInputText() {
		return inputText;
	}

	@Override
	public final Token getNextToken() throws UndefinedSymbolException {
		Token result = null;
		for (int i = position; i < inputText.length(); i++) {
			if (inputText.charAt(i) != WHITE_SPACE) {
				switch (inputText.charAt(i)){
					case '=':   position = i + 1;
								result = new Token(Token.TokenType.DEFINITION);
								break;
					case ',': 	position = i + 1;
								result = new Token(Token.TokenType.CONCATENATION);
								break;
					case ';':   position = i + 1;
								result = new Token(Token.TokenType.TERMINATION);
								break;
					case '|': 	position = i + 1;
								result = new Token(Token.TokenType.ALTERNATION);
								break;
					case '-':   position = i + 1;
								result = new Token(Token.TokenType.EXCEPTION);
								break;
					case '*': 	position = i + 1;
								result = new Token(Token.TokenType.REPETITION);
								break;
				}
				if (result == null) {throw new UndefinedSymbolException();}
				return result;
				//if (inputText.charAt(i) != '"') {
				//	position = i + 1;
				//	return new Token(Token.TokenType.IDENTIFIER, "f");
				//}
			}

		}
		return null;
	}

	@Override
	public boolean hasNextToken() {
		for (int i = position; i < inputText.length(); i++) {
			if (inputText.charAt(i) != WHITE_SPACE) {
				return true;
			}
		}
		return false;
	}
}
