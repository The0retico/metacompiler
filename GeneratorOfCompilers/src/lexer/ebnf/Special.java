package lexer.ebnf;

/**
 * Token with special content for extending EBNF.
 * 
 * @author martinsarvas
 * 
 */
public class Special implements IToken {

	/**
	 * Value of this Special sequence string.
	 */
	private final String value;

	/**
	 * @param string
	 *            value for this Special sequence string
	 */
	Special(final String string) {
		value = string;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
}
