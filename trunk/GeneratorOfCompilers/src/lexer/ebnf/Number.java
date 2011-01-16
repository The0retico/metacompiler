package lexer.ebnf;

import java.io.IOException;

/**
 * Number token in EBNF syntax for repetitions.
 * 
 * @author The0retico
 * 
 */
class Number extends Token implements IToken {

	/**
	 * @param reader
	 *            TODO
	 * @return true if next token is a number, false otherwise
	 * @throws IOException
	 *             if I/O error occours
	 */
	static boolean isNextIn(final LineAndColumnNumberReader reader)
			throws IOException {
		final char nextSymbol = (char) Lexer.peek(reader);
		return Character.isDigit(nextSymbol);
	}

	/**
	 * @param reader
	 *            TODO
	 * @return number as the next token.
	 * @throws IOException
	 *             if I/O error occours
	 */
	static Number scanFrom(final LineAndColumnNumberReader reader)
			throws IOException {
		int nextChar = reader.read();
		final StringBuilder nextToken = new StringBuilder();
		while (nextChar != -1 && Character.isDigit((char) nextChar)) {
			nextToken.append((char) nextChar);
			nextChar = reader.read();
		}
		final int intValue = Integer.parseInt(nextToken.toString());
		final Number result = new Number(intValue, reader.getLineNumber(),
				reader.getColumnNumber());
		return result;
	}

	/**
	 * Number value of this token.
	 */
	private final int value;

	/**
	 * @param number
	 *            for this number
	 */
	Number(final int number, final int line, final int column) {
		super(line, column);
		value = number;
	}

	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Number)) {
			return false;
		}
		final Number other = (Number) obj;
		if (value != other.value) {
			return false;
		}
		return true;
	}

	@Override
	public final int getLength() {
		return String.valueOf(value).length();
	}

	@Override
	public final String getValue() {
		return String.valueOf(value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}

}
