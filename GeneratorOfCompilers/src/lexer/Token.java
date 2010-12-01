package lexer;

public class Token {
	public enum typeOfToken{DEFINITION, CONCATENATION, TERMINATION, ALTERNATION, EXCEPTION,IDENTIFIER };
	private typeOfToken mytype; 
	private String nameOfToken;
	private final int stored;

	public Token(final typeOfToken type) {
		mytype = type;
		stored = 0;
	}

	public Token(final typeOfToken type, final String name) {
		mytype = type;
		nameOfToken = name;
		stored = 0;
	}

	public typeOfToken getMytype() {
		return mytype;
	}

	public void setMytype(typeOfToken mytype) {
		this.mytype = mytype;
	}

	public String getNameOfToken() {
		return nameOfToken;
	}

	public void setNameOfToken(String nameOfToken) {
		this.nameOfToken = nameOfToken;
	}

}
