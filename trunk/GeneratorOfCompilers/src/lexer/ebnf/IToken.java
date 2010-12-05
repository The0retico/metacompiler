package lexer.ebnf;

/**
 * Interface to handle EBNF tokens uniformly.
 * 
 * @author The0retico
 * 
 */
public interface IToken {

	/**
	 * @return length of this token in string representation
	 */
	int getLength();

	/**
	 * @return string value of this token
	 */
	String getValue();
}
