package sk.scerbak.lambdainterpreter;

/**
 * @author The0retico Parser and printer for lambda expressions.
 */
public interface ILambdaSyntax {
	/**
	 * @param input
	 *            string containing lambda expressions in syntax of this
	 *            interpreter
	 * @return parsed lambda expression from input
	 */
	ILambdaExpression fromString(String input);

	/**
	 * @param expression
	 *            to be printed
	 * @return string representing expression in this syntax for lambda
	 *         expressions
	 */
	String toString(ILambdaExpression expression);
}
