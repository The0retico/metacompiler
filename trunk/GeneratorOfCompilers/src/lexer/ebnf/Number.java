package lexer.ebnf;

/**
 * Number token in EBNF syntax for repetitions.
 * 
 * @author The0retico
 * 
 */
public class Number implements IToken {

	/**
	 * Number value of this token.
	 */
	private final int number;

	/**
	 * @param value
	 *            for this number
	 */
	Number(final int value) {
		number = value;
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
		if (number != other.number) {
			return false;
		}
		return true;
	}

	@Override
	public final int getLength() {
		return String.valueOf(number).length();
	}

	@Override
	public final String getValue() {
		return String.valueOf(number);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		return result;
	}

}
