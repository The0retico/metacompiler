package lexer;

import java.util.HashMap;
import java.util.Map;

import lexer.EBNFToken.UndefinedSymbolException;

public class EBNFLexer {
	private final String inputText;
	private final Map<Integer, EBNFToken> tableOfSymbols;

	private int position;
	private static final char WHITE_SPACE = ' ';

	public EBNFLexer(final String input) {
		inputText = input;
		position = 0;
		tableOfSymbols = new HashMap<Integer, EBNFToken>();
	}

	private void findNextSymbol() {
		while (position < inputText.length() 
				&& inputText.charAt(position) == WHITE_SPACE) {
			position++;
		}
	}

	public final EBNFToken getNextToken() throws UndefinedSymbolException {
		findNextSymbol();
		final char nextSymbol = inputText.charAt(position);
		position++;
		return EBNFToken.create(nextSymbol);
	}

	public final boolean hasNextToken() {
		findNextSymbol();
		return position < inputText.length();
	}
}
