package lexer;

/**
 * Enumerated type of EBNF tokens.
 * 
 * @author sarvasmartin
 */
import lexer.EBNFLexer.LexerState;

public enum EBNFToken {
	DEFINITION, CONCATENATION, TERMINATION, ALTERNATION, EXCEPTION, IDENTIFIER, REPETITION,
	NUMBER, LTOPTION, RTOPTION, LTREP, RTREP, LTGROUP, RTGROUP;

	public static class UndefinedSymbolException extends Exception {
		private static final long serialVersionUID = 1L;

		public UndefinedSymbolException(final char symbol) {
			super("Symbol '" + symbol + "' is undefined");
		}
	}

	/**
	 * @param symbol
	 *            character from which a new token should be created
	 * @return new token from the symbol
	 * @throws UndefinedSymbolException
	 *             if the symbol is not a valid token
	 */
	public static EBNFToken create(final char symbol)
			throws UndefinedSymbolException {
		EBNFToken result = null;
		switch (symbol) {
		case '=':
			result = DEFINITION;
			break;
		case ',':
			result = CONCATENATION;
			break;
		case ';':
			result = TERMINATION;
			break;
		case '|':
			result = ALTERNATION;
			break;
		case '-':
			result = EXCEPTION;
			break;
		case '[':
			result = LTOPTION;
			break;
		case ']':
			result = RTOPTION;
			break;
		case '{':
			result = LTREP;
			break;
		case '}':
			result = RTREP;
			break;
		case '(':
			result = LTGROUP;
			break;
		case ')':
			result = RTGROUP;
			break;
		
		case '*':
			result = REPETITION;
			break;
		default:
			throw new UndefinedSymbolException(symbol);
		}
		return result;
	}

}
