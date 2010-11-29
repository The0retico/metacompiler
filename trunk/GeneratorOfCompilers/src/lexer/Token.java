package lexer;

public class Token {
	public String typeOfToken;
	public String nameOfToken;
	public int stored;
	
	public Token(String type, String name){
		typeOfToken = type;
		nameOfToken = name;
		stored = 0;
	}
	public Token(String type){
		typeOfToken = type;
		stored = 0;
	}

}
