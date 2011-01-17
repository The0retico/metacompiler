package ebnf.lexer;

import java.io.IOException;

/**
 * Token for terminal strings in EBNF.
 * 
 * @author martinsarvas
 * 
 */
public class Terminal extends Token implements IToken {

	/**
	 * @param reader
	 *            TODO
	 * @return true if next token is a Terminal string, false otherwise
	 * @throws IOException
	 *             if I/O error occours
	 */
	static boolean isNextIn(final LineAndColumnNumberReader reader)
			throws IOException {
		final char currentSymbol = (char) Lexer.peek(reader);
		return currentSymbol == '\'' || currentSymbol == '"';
	}

	/**
	 * @param reader
	 *            TODO
	 * @return Terminal string as the next token.
	 * @throws IOException
	 *             if I/O error occours
	 */
	static Terminal scanFrom(final LineAndColumnNumberReader reader)
			throws IOException {
		final char quote = (char) reader.read();
		int nextChar = reader.read();
		final StringBuilder nextToken = new StringBuilder();
		while (nextChar != -1 && nextChar != quote) {
			nextToken.append((char) nextChar);
			nextChar = reader.read();
		}
		final Terminal result = new Terminal(nextToken.toString(),
				reader.getLineNumber(), reader.getColumnNumber());
		return result;
	}

	/**
	 * Value of this Terminal string.
	 */
	private final String value;

	/**
	 * @param string
	 *            value for this Terminal string
	 */
	public Terminal(final String string, final int line, final int column) {
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
		if (!(obj instanceof Terminal)) {
			return false;
		}
		final Terminal other = (Terminal) obj;
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
		return "Terminal [value=" + value + "]";
	}

}
