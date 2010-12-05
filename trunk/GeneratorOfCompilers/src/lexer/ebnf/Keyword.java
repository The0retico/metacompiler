package lexer.ebnf;

/**
 * Enumerated type of EBNF tokens.
 * 
 * @author sarvasmartin
 */
public enum Keyword implements IToken {

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

	@Override
	public int getLength() {
		return symbol.length();
	}

	@Override
	public String getValue() {
		return symbol;
	}

}
