package lexer.ebnf;

import java.io.IOException;

/**
 * Identifier token for grammar rules in EBNF.
 * 
 * @author The0retico
 * 
 */
class Identifier extends Token implements IToken {

	/**
	 * Name of this identifier.
	 */
	private final String name;

	/**
	 * @param string
	 *            name for this identifier
	 * @param line
	 *            number on which this token starts in input
	 * @param column
	 *            number - position in line - where this token starts in input
	 */
	Identifier(final String string, final int line, final int column) {
		super(line, column);
		name = string;
	}

	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Identifier)) {
			return false;
		}
		final Identifier other = (Identifier) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public final int getLength() {
		return name.length();
	}

	@Override
	public final String getValue() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (name == null ? 0 : name.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Identifier [name=" + name + "]";
	}

	/**
	 * @param reader
	 *            TODO
	 * @return next scanned identifier token
	 * @throws IOException
	 *             if I/O error occours
	 */
	static Identifier scanFrom(
			final LineAndColumnNumberReader reader) throws IOException {
		int nextChar = (char) reader.read();
		final StringBuilder value = new StringBuilder();
		while (nextChar != -1 && Character.isJavaIdentifierPart(nextChar)) {
			value.append((char) nextChar);
			nextChar = reader.read();
		}
		return new Identifier(value.toString(), reader.getLineNumber(),
				reader.getColumnNumber());
	}

	/**
	 * @param reader
	 *            TODO
	 * @return true if next token is an identifier, false otherwise.
	 * @throws IOException
	 *             if I/O error occours
	 */
	static boolean isNextIn(
			final LineAndColumnNumberReader reader) throws IOException {
		final char nextSymbol = (char) Lexer.peek(reader);
		return Character.isJavaIdentifierStart(nextSymbol);
	}

}
