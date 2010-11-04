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
		ILambdaExpression result = null;
		if (StringUtils.isInteger(input)) {
			result = parseInteger(input);
		} else if (input.length() == 1) {
			final Character symbol = input.charAt(0);
			if (Character.isLowerCase(symbol)) {
				result = parseVariable(input);
			} else {
				result = parseConstant(input);
			}
		} else {
			final int openingIndex = input.indexOf('(');
			final int closingIndex = input.lastIndexOf(')');
			if (openingIndex != -1 && closingIndex != -1) {
				ILambdaExpression result1 = result;
				if (openingIndex == -1 || openingIndex >= closingIndex) {
					throw new IllegalArgumentException(input);
				}
				final String unpackedString = input.substring(openingIndex + 1,
						closingIndex);
				if (!unpackedString.isEmpty()) {
					final int indexOfPipe = unpackedString.indexOf('|');
					final int indexOfSpace = unpackedString.indexOf(' ');
					if (indexOfPipe != -1) {
						result1 = parseAbstraction(unpackedString);
					} else if (indexOfSpace == -1) {
						throw new IllegalArgumentException(unpackedString);
					} else {
						result1 = parseApplication(unpackedString);
					}
				}
				if (result1 == null) {
					throw new IllegalArgumentException(input);
				}
				result = result1;
			}
		}
		return result;
	}

	private ILambdaExpression parseConstant(String input) {
		return new LambdaConstant(input.substring(0, 1));
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
