package lexer;

import java.util.Map;

import lexer.EBNFToken.UndefinedSymbolException;

public class EBNFLexer {
	private final String inputText;
	private Map<Integer, EBNFToken> tableOfSymbols;

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
	public final String getInputText() {
		return inputText;
	}

	public final EBNFToken getNextToken() throws UndefinedSymbolException {
		findNextSymbol();
		final char nextSymbol = inputText.charAt(position);
		return EBNFToken.create(nextSymbol);
	}

	public final boolean hasNextToken() {
		findNextSymbol();
		return position < inputText.length();
	}
}
