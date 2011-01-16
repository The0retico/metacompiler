package lexer.ebnf;

/**
 * Common superclass for ITokens implementing common features like line and
 * column numbers.
 * 
 * @author The0retico
 * 
 */
public abstract class Token implements IToken {

	protected final int lineNumber;
	protected final int columnNumber;

	public Token(final int line, final int column) {
		lineNumber = line;
		columnNumber = column;
	}

	@Override
	public int getColumnNumber() {
		return columnNumber;
	}

	@Override
	public int getLineNumber() {
		return lineNumber;
	}

}