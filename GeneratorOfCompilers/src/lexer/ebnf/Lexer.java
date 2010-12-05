package lexer.ebnf;

import lexer.ebnf.Keyword.UndefinedSymbolException;

/**
 * Lexer/scanner for the metagrammar language (EBNF).
 * 
 * @author sarvasmartin
 * 
 */
public class Lexer {
	public enum LexerState {
		READSTRING, READNUMBER, READIDENTIFIER, READSYMBOL
	}

	/**
	 * Text to be scanned for tokens.
	 */
	private final String inputText;

	private final LexerState state = LexerState.READSYMBOL;

	/**
	 * Current position of the scanner in the inputText.
	 */
	private int position;

	/**
	 * Constant for white-space characters.
	 */
	private static final char WHITE_SPACE = ' ';

	/**
	 * @param input
	 *            text to be scanned
	 */
	public Lexer(final String input) {
		inputText = input;
		position = 0;
	}

	/**
	 * @return the next symbol in the InputText or throw an exception otherwise
	 * @throws StringIndexOutOfBoundsException
	 *             if there are no more symbols in the inputText
	 */
	private char findNextSymbol() throws StringIndexOutOfBoundsException {
		while (position < inputText.length()
				&& inputText.charAt(position) == WHITE_SPACE) {
			position++;
		}
		return inputText.charAt(position);
	}

	/**
	 * @return next token in the inputText
	 * @throws UndefinedSymbolException
	 *             if there is not a valid token
	 */
	public final Keyword getNextToken() throws UndefinedSymbolException {
		final char nextSymbol = findNextSymbol();
		position++;
		return Keyword.create(nextSymbol);
	}

	/**
	 * @return true if the inputText contains another Token, false otherwise
	 */
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
