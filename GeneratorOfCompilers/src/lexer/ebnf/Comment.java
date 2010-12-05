package lexer.ebnf;

public class Comment implements IToken {

	/**
	 * Value of this Comment.
	 */
	private final String value;

	/**
	 * @param string
	 *            value for this Comment
	 */
	Comment(final String string) {
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
		if (!(obj instanceof Comment)) {
			return false;
		}
		final Comment other = (Comment) obj;
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
	public int getLength() {
		return value.length();
	}

	@Override
	public String getValue() {
		return value;
	}

}
