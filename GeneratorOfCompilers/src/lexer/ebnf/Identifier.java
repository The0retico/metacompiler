package lexer.ebnf;

/**
 * Identifier token for grammar rules in EBNF.
 * 
 * @author The0retico
 * 
 */
public class Identifier implements IToken {

	/**
	 * Name of this identifier.
	 */
	private final String name;

	/**
	 * @param string
	 *            name for this identifier
	 */
	Identifier(final String string) {
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

}
