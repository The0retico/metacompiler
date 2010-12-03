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

	private void findNextSymbol() {
		while (inputText.charAt(position) == WHITE_SPACE
				&& position < inputText.length()) {
			position++;
		}
	}

	/**
	 * @return the inputText
	 */
	public String getInputText() {
		return inputText;
	}

	@Override
	public final Token getNextToken() throws UndefinedSymbolException {
		findNextSymbol();
		final char nextSymbol = inputText.charAt(position);
		return Token.create(nextSymbol);
	}

	@Override
	public boolean hasNextToken() {
		findNextSymbol();
		return position < inputText.length();
	}
}
