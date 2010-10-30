package sk.scerbak.lambdainterpreter;

import sk.scerbak.utils.StringUtils;

/**
 * @author The0retico lambda expression root interpreter with factory method
 */
public abstract class AbstractLambdaExpression {
	/**
	 * @return value of this lambda expression
	 */
	public abstract int evaluate();

	/**
	 * @param string
	 *            input containing lambda expressions
	 * @return root of object model representing lambda expressions from string
	 */
	public static AbstractLambdaExpression fromString(final String string) {
		if (StringUtils.isInteger(string)) {
			return parseLambdaInteger(string);
		}
		final String unpackedString = StringUtils.substringOfWithin(string,
				'(', ')');
		if (!unpackedString.isEmpty()) {
			return parseLambdaAbstraction(unpackedString);
		}
		throw new IllegalArgumentException(string);
	}

	/**
	 * @param string
	 * @return object representation of lambda integer from string
	 */
	private static LambdaInteger parseLambdaInteger(final String string) {
		return new LambdaInteger(Integer.parseInt(string));
	}

	/**
	 * @param unpackedString
	 *            input containing lambda abstraction without parenthesis
	 * @return object represetnation of unpackedString
	 */
	private static LambdaAbstraction parseLambdaAbstraction(
			final String unpackedString) {
		final int delimiterIndex = unpackedString.indexOf('|');
		if (delimiterIndex == -1) {
			throw new IllegalArgumentException(unpackedString);
		}
		final String variable = unpackedString.substring(delimiterIndex);
		final String expression = StringUtils.substringOfAfter(unpackedString,
				delimiterIndex);
		return new LambdaAbstraction(variable, fromString(expression));
	}
}
