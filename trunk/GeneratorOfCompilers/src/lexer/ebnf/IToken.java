package lexer.ebnf;

/**
 * Interface to handle EBNF tokens uniformly.
 * 
 * @author The0retico
 * 
 */
public interface IToken {

	/**
	 * @return position in a line in input where this token starts
	 */
	int getColumnNumber();

	/**
	 * @return length of this token in string representation
	 */
	int getLength();

	/**
	 * @return the number of line on which this token starts in input
	 */
	int getLineNumber();

	/**
	 * @return string value of this token
	 */
	String getValue();
}
