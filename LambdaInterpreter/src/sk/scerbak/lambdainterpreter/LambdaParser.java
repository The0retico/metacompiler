package sk.scerbak.lambdainterpreter;

import sk.scerbak.utils.StringUtils;
import sk.scerbak.utils.StringUtils.StringCase;

/**
 * @author The0retico lambda expression root interpreter with factory method
 */
class LambdaParser implements ILambdaSyntax {

	@Override
	public String toString(final ILambdaExpression expression) {
		return expression.toString();
	}

	@Override
	public ILambdaExpression fromString(final String input) {
		if (input.isEmpty()) {
			throw new IllegalArgumentException("empty string");
		}
		ILambdaExpression result = null;
		if (input.charAt(0) == '(' && input.charAt(input.length() - 1) == ')') {
			final int indexOfPipe = input.indexOf('|');
			if (indexOfPipe != -1) {
				final String variableLabel = input.substring(1, indexOfPipe);
				if (StringUtils.caseOf(variableLabel) == StringCase.LOWER) {
					final ILambdaExpression bodyExpression = fromString(input
							.substring(indexOfPipe + 1, input.length() - 1));
					result = new LambdaAbstraction(variableLabel,
							bodyExpression);
				} else {
					throw new IllegalArgumentException(variableLabel + '|');
				}
			} else if (input.indexOf(' ') != -1) {
				int index = 1;
				int counter = 0;
				char current = input.charAt(index);
				while (current != ' ' || counter != 0) {
					index++;
					current = input.charAt(index);
					if (current == '(') {
						counter++;
					} else if (current == ')') {
						counter--;
					}
				}
				final ILambdaExpression function = fromString(input.substring(
						1, index));
				final ILambdaExpression argument = fromString(input.substring(
						index + 1, input.length() - 1));
				result = new LambdaApplication(function, argument);
			} else {
				throw new IllegalArgumentException(input.substring(1,
						input.length() - 1));
			}
		} else {
			final StringCase inputCase = StringUtils.caseOf(input);
			if (inputCase == StringCase.UPPER) {
				result = new LambdaConstant(input);
			} else if (inputCase == StringCase.LOWER) {
				result = new LambdaVariable(input);
			} else if (StringUtils.isInteger(input)) {
				final Integer value = Integer.valueOf(input);
				result = new LambdaInteger(value);
			} else {
				throw new IllegalArgumentException(input);
			}
		}
		return result;
	}
}
