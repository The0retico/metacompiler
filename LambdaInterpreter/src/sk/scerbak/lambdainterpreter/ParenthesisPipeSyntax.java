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
		final String variable = unpackedString.substring(0, delimiterIndex);
		final String expression = StringUtils.substringOfAfter(unpackedString,
				delimiterIndex);
		return new LambdaAbstraction(variable, fromString(expression));
	}

	@Override
	public String toString(final ILambdaExpression expression) {
		return expression.toString();
	}

	@Override
	public ILambdaExpression fromString(final String input) {
		if (StringUtils.isInteger(input)) {
			return parseInteger(input);
		}
		if (input.length() == 1) {
			return parseVariable(input);
		}
		final String unpackedString = StringUtils.substringOfWithin(input, '(',
				')');
		if (!unpackedString.isEmpty()) {
			final int indexOfPipe = unpackedString.indexOf('|');
			final int indexOfSpace = unpackedString.indexOf(' ');
			if (indexOfPipe != -1) {
				return parseAbstraction(unpackedString);
			} else if (indexOfSpace == -1) {
				throw new IllegalArgumentException(unpackedString);
			} else {
				return parseApplication(unpackedString);
			}
		}
		throw new IllegalArgumentException(input);
	}

	private ILambdaExpression parseVariable(String input) {
		return new LambdaVariable(input.substring(0, 1));
	}

	private ILambdaExpression parseApplication(String unpackedString) {
		final int indexOfSpace = unpackedString.indexOf(' ');
		final String function = unpackedString.substring(0, indexOfSpace);
		final String argument = StringUtils.substringOfAfter(unpackedString,
				indexOfSpace);
		return new LambdaApplication(fromString(function), fromString(argument));
	}

}
