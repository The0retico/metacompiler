package lexer;

public class UndefinedSymbolException extends Exception {

	private static final long serialVersionUID = 1L;

	public UndefinedSymbolException(final char symbol) {
		super("Symbol '" + symbol + "' is undefined");
	}

}
