package lexer.ebnf;

/**
 * Token for terminal strings in EBNF.
 * 
 * @author martinsarvas
 * 
 */
public class Terminal implements IToken {

	/**
	 * Value of this Terminal string.
	 */
	private final String value;

	/**
	 * @param string
	 *            value for this Terminal string
	 */
	Terminal(final String string) {
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

}
