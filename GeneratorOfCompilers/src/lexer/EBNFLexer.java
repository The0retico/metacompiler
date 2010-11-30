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
	public final Token getNextToken() {
		for (int i = position; i < inputText.length(); i++) {
			if (inputText.charAt(i) != WHITE_SPACE) {
				if (inputText.charAt(i) == '=') {
					position = i + 1;
					return new Token("Definition");
				}
				if (inputText.charAt(i) == ',') {
					position = i + 1;
					return new Token("Concatenation");
				}
				if (inputText.charAt(i) == ';') {
					position = i + 1;
					return new Token("Termination");
				}
				if (inputText.charAt(i) == '|') {
					position = i + 1;
					return new Token("Alternation");
				}
				if (inputText.charAt(i) != '"') {
					position = i + 1;
					return new Token("Identifier", "f");
				}
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
