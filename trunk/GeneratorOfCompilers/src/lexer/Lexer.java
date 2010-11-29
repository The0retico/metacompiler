package lexer;

public interface Lexer {

	boolean hasNextToken();

	Token getNextToken();

}
