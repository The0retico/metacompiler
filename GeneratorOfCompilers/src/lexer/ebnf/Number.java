package lexer.ebnf;

/**
 * Number token in EBNF syntax for repetitions.
 * 
 * @author The0retico
 * 
 */
class Number implements IToken {

	/**
	 * Number value of this token.
	 */
	private final int value;

	/**
	 * @param number
	 *            for this number
	 */
	Number(final int number) {
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
