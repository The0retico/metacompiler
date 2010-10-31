package sk.scerbak.lambdainterpreter;

import sk.scerbak.utils.StringUtils;

/**
 * @author The0retico lambda expression root interpreter with factory method
 */
class ParenthesisPipeSyntax implements ILambdaSyntax {

	/**
	 * @param string
	 *            input containing integer constant
	 * @return object representation of lambda integer from string
	 */
	private static LambdaInteger parseInteger(final String string) {
		return new LambdaInteger(Integer.parseInt(string));
	}

	/**
	 * @param unpackedString
	 *            input containing lambda abstraction without parenthesis
	 * @return object represetnation of unpackedString
	 */
	private LambdaAbstraction parseAbstraction(final String unpackedString) {
		final int delimiterIndex = unpackedString.indexOf('|');
		if (delimiterIndex == -1) {
			throw new IllegalArgumentException(unpackedString);
		}
		final String variable = unpackedString.substring(delimiterIndex);
		final String expression = StringUtils.substringOfAfter(unpackedString,
				delimiterIndex);
		return new LambdaAbstraction(variable, fromString(expression));
	}

	@Override
	public String toString(final ILambdaExpression expression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILambdaExpression fromString(final String input) {
		if (StringUtils.isInteger(input)) {
			return parseInteger(input);
		}
		final String unpackedString = StringUtils.substringOfWithin(input, '(',
				')');
		if (!unpackedString.isEmpty()) {
			return parseAbstraction(unpackedString);
		}
		throw new IllegalArgumentException(input);
	}

}
