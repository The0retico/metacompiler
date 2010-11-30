package lexer;

public class Token {
	private final String typeOfToken;
	private String nameOfToken;
	private final int stored;

	public Token(final String type) {
		typeOfToken = type;
		stored = 0;
	}

	public Token(final String type, final String name) {
		typeOfToken = type;
		nameOfToken = name;
		stored = 0;
	}

}
