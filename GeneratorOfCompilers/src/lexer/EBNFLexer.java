package lexer;

import java.util.HashMap;
import java.util.Map;

import lexer.EBNFToken.UndefinedSymbolException;

public class EBNFLexer {
	public enum LexerState {READSTRING, READNUMBER, READIDENTIFIER, READSYMBOL}
	private final String inputText;
	private final Map<Integer, EBNFToken> tableOfSymbols;

	private LexerState state = LexerState.READSYMBOL;
	private int position;
	private static final char WHITE_SPACE = ' ';

	public EBNFLexer(final String input) {
		inputText = input;
		position = 0;
		tableOfSymbols = new HashMap<Integer, EBNFToken>();
	}

	private char findNextSymbol() {
		while (position < inputText.length()
				&& inputText.charAt(position) == WHITE_SPACE) {
			position++;
		}
		return inputText.charAt(position);
	}

	public final EBNFToken getNextToken() throws UndefinedSymbolException {
		final char nextSymbol = findNextSymbol();
		position++;
		return EBNFToken.create(nextSymbol);
	}

	public final boolean hasNextToken() {
		boolean result = true;
		try {
			findNextSymbol();
		} catch (final StringIndexOutOfBoundsException e) {
			result = false;
		}
		return result;
	}
}
