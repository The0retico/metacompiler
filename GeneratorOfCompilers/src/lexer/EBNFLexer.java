package lexer;

import java.util.HashMap;
import java.util.Map;

import lexer.EBNFToken.UndefinedSymbolException;

/**
 * Lexer/scanner for the metagrammar language (EBNF).
 * 
 * @author sarvasmartin
 * 
 */
public class EBNFLexer {
	public enum LexerState {
		READSTRING, READNUMBER, READIDENTIFIER, READSYMBOL
	}

	/**
	 * Text to be scanned for tokens.
	 */
	private final String inputText;

	private final Map<Integer, EBNFToken> tableOfSymbols;

	/**
	 * Current position of the scanner in the inputText.
	 */
	private final LexerState state = LexerState.READSYMBOL;
	private int position;

	/**
	 * Constant for white-space characters.
	 */
	private static final char WHITE_SPACE = ' ';

	/**
	 * @param input
	 *            text to be scanned
	 */
	public EBNFLexer(final String input) {
		inputText = input;
		position = 0;
		tableOfSymbols = new HashMap<Integer, EBNFToken>();
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
	public final EBNFToken getNextToken() throws UndefinedSymbolException {
		final char nextSymbol = findNextSymbol();
		position++;
		return EBNFToken.create(nextSymbol);
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
