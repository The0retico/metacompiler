package ebnf.lexer;

import java.io.IOException;

/**
 * Token with special content for extending EBNF.
 * 
 * @author martinsarvas
 * 
 */
class Special extends Token implements IToken {

	/**
	 * @param reader
	 *            TODO
	 * @return true if next token is a Special sequence string, false otherwise
	 * @throws IOException
	 *             if I/O error occours
	 */
	static boolean isNextIn(final LineAndColumnNumberReader reader)
			throws IOException {
		final char nextSymbol = (char) Lexer.peek(reader);
		return nextSymbol == '?';
	}

	/**
	 * @param reader
	 *            TODO
	 * @return Special sequence string as the next token.
	 * @throws IOException
	 *             if I/O error occours
	 */
	static Special scanFrom(final LineAndColumnNumberReader reader)
			throws IOException {
		reader.skip(1);
		int nextChar = reader.read();
		final StringBuilder nextToken = new StringBuilder();
		while (nextChar != -1 && (char) nextChar != '?') {
			nextToken.append((char) nextChar);
			nextChar = reader.read();
		}
		final Special result = new Special(nextToken.toString(),
				reader.getLineNumber(), reader.getColumnNumber());
		return result;
	}

	/**
	 * Value of this Special sequence string.
	 */
	private final String value;

	/**
	 * @param string
	 *            value for this Special sequence string
	 */
	Special(final String string, final int line, final int column) {
		super(line, column);
		value = string;
	}

	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Special)) {
			return false;
		}
		final Special other = (Special) obj;
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	@Override
	public final int getLength() {
		return value.length();
	}

	@Override
	public final String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (value == null ? 0 : value.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Special [value=" + value + "]";
	}

}
