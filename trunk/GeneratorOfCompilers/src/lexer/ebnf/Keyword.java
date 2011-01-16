package lexer.ebnf;

/**
 * Enumerated type of EBNF tokens.
 * 
 * @author sarvasmartin
 */
class Keyword extends Token implements IToken {
	enum Type {
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
		private Type(final String value) {
			symbol = value;
		}

		public int getLength() {
			return symbol.length();
		}

		public String getValue() {
			return symbol;
		}
	}

	public static Type[] values() {
		return Type.values();
	}

	private final Type keywordType;

	public Keyword(final Type type, final int line, final int column) {
		super(line, column);
		keywordType = type;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Keyword)) {
			return false;
		}
		final Keyword other = (Keyword) obj;
		if (keywordType != other.keywordType) {
			return false;
		}
		return true;
	}

	@Override
	public int getLength() {
		return keywordType.getLength();
	}

	@Override
	public String getValue() {
		return keywordType.getValue();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (keywordType == null ? 0 : keywordType.hashCode());
		return result;
	}

}
