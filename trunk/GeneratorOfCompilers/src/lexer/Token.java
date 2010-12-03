package lexer;

public class Token {
	public enum TokenType {
		DEFINITION, CONCATENATION, TERMINATION, ALTERNATION, EXCEPTION, IDENTIFIER, REPETITION, NUMBER
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

	static Token create(final char symbol)
			throws UndefinedSymbolException {
		Token result = null;
		switch (symbol) {
		case '=':
			result = new Token(TokenType.DEFINITION);
			break;
		case ',':
			result = new Token(TokenType.CONCATENATION);
			break;
		case ';':
			result = new Token(TokenType.TERMINATION);
			break;
		case '|':
			result = new Token(TokenType.ALTERNATION);
			break;
		case '-':
			result = new Token(TokenType.EXCEPTION);
			break;
		case '*':
			result = new Token(TokenType.REPETITION);
			break;
		default:
			throw new UndefinedSymbolException(symbol);
		}
		return result;
	}

}
