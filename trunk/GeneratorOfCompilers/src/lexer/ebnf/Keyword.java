package lexer.ebnf;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Enumerated type of EBNF tokens.
 * 
 * @author sarvasmartin
 */
public class Keyword extends Token implements IToken {
	public enum Type {
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

	/**
	 * @param reader
	 *            TODO
	 * @return true if next token is a keyword, false otherwise
	 * @throws IOException
	 *             if I/O error occours
	 */
	static boolean isNextIn(final LineAndColumnNumberReader reader)
			throws IOException {
		final Type[] keywordTypes = values();
		Arrays.sort(keywordTypes, new Comparator<Type>() {

			@Override
			public int compare(final Type first, final Type second) {
				return second.getLength() - first.getLength();
			}
		});
		boolean found = false;
		for (int index = 0; index < keywordTypes.length && !found; index++) {
			final Type keywordType = keywordTypes[index];
			final int keywordLength = keywordType.getLength();
			reader.mark(keywordLength);
			final char[] nextToken = new char[keywordLength];
			if (reader.read(nextToken, 0, keywordLength) == keywordLength) {
				found = keywordType.getValue()
						.equals(String.valueOf(nextToken));
			}
			reader.reset();
		}
		return found;
	}

	/**
	 * @param reader
	 *            TODO
	 * @return a keyword as a next token
	 * @throws IOException
	 *             if I/O error occours
	 */
	static Keyword scanFrom(final LineAndColumnNumberReader reader)
			throws IOException {
		final Type[] keywordTypes = values();
		Type result = null;
		int index = 0;
		for (; index < keywordTypes.length && result == null; index++) {
			final Type keywordType = keywordTypes[index];
			final int keywordLength = keywordType.getLength();
			reader.mark(keywordLength);
			final char[] nextToken = new char[keywordLength];
			if (reader.read(nextToken, 0, keywordLength) == keywordLength) {
				if (keywordType.getValue().equals(String.valueOf(nextToken))) {
					result = keywordType;
				} else {
					reader.reset();
				}
			}
		}
		return new Keyword(result, reader.getLineNumber(),
				reader.getColumnNumber());
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
