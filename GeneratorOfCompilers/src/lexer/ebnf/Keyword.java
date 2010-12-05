package lexer.ebnf;

/**
 * Enumerated type of EBNF tokens.
 * 
 * @author sarvasmartin
 */
public enum Keyword {

	/**
	 * Grammar rule definition token.
	 */
	DEFINITION("="),

	/**
	 * Concatenation token for different parts of a single grammar rule.
	 */
	CONCATENATION(","),

	/**
	 * Termination of a grammar rule.
	 */
	TERMINATION(";"),

	/**
	 * Alternation for different rules for a single non-terminal.
	 */
	ALTERNATION("|"),

	/**
	 * Exception token excludes symbols in a grammar rule.
	 */
	EXCEPTION("-"),

	/**
	 * Repetition token used to explicitly bound repetition count.
	 */
	REPETITION("*"),

	/**
	 * Left option bracket token.
	 */
	LEFT_OPTION("["),

	/**
	 * Right option bracket token.
	 */
	RIGHT_OPTION("]"),

	/**
	 * Left repetition bracket token.
	 */
	LEFT_REPETITION("{"),

	/**
	 * Right repetition bracket token.
	 */
	RIGHT_REPETITION("}"),

	/**
	 * Left grouping bracket token.
	 */
	LEFT_GROUPING("("),

	/**
	 * Right grouping bracket token.
	 */
	RIGHT_GROUPING(")");

	/**
	 * Exception to be thrown when lexer input contains a non whitespace symbol,
	 * which cannot be scanned as a token.
	 * 
	 * @author The0retico
	 * 
	 */
	public static class UndefinedSymbolException extends Exception {

		/**
		 * Needed for serialization, because exception should be serializable.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * @param symbol
		 *            which cannot be scanned
		 */
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
	public static Keyword create(final char symbol)
			throws UndefinedSymbolException {
		Keyword result = null;
		for (final Keyword keyword : Keyword.values()) {
			if (keyword.getValue().equals(String.valueOf(symbol))) {
				result = keyword;
			}
		}
		if (result == null) {
			throw new UndefinedSymbolException(symbol);
		}
		return result;
	}

	/**
	 * String value of this token.
	 */
	private final String symbol;

	/**
	 * @param value
	 *            for this token
	 */
	Keyword(final String value) {
		symbol = value;
	}

	/**
	 * @return the symbol
	 */
	public String getValue() {
		return symbol;
	}

}
