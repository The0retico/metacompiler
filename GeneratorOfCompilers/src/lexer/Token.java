package lexer;

public class Token {
	public enum TokenType {
		DEFINITION, CONCATENATION, TERMINATION, ALTERNATION, EXCEPTION, IDENTIFIER
	};

	private TokenType type;
	private String value;

	public Token(final TokenType myType) {
		type = myType;
	}

	/**
	 * @param myType
	 *            is a type of EBNF token
	 * @param myName
	 */
	public Token(final TokenType myType, final String myName) {
		type = myType;
		value = myName;
	}

	public TokenType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public void setType(final TokenType myType) {
		type = myType;
	}

	public void setValue(final String nameOfToken) {
		value = nameOfToken;
	}

}
