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
		if (isWithin(input, '(', ')')) {
			result = parseCompoundExpression(input);
		} else {
			result = parseSymbol(input);
		}
		return result;
	}

	private ILambdaExpression parseCompoundExpression(final String input) {
		ILambdaExpression result = null;
		int index = indexOfTopLevelDelimiter(input);
		final char delimiter = input.charAt(index);
		if (delimiter == ' ') {
			result = parseApplication(input, index);
		} else if (delimiter == '|') {
			result = parseAbstraction(input, index);
		} else {
			throw new IllegalArgumentException(input);
		}
		return result;
	}

	private boolean isWithin(final String input, final char opening,
			final char closing) {
		final char firstChar = input.charAt(0);
		final int lastIndex = input.length() - 1;
		final char lastChar = input.charAt(lastIndex);
		return firstChar == opening && lastChar == closing;
	}

	private int indexOfTopLevelDelimiter(final String input) {
		int index = 0;
		int counter = 0;
		char current;
		do {
			index++;
			current = input.charAt(index);
			if (current == '(') {
				counter++;
			} else if (current == ')') {
				counter--;
			}
		} while (!(counter == 0 && (current == ' ' || current == '|')));
		return index;
	}

	private ILambdaExpression parseApplication(final String input, int index) {
		ILambdaExpression result;
		final ILambdaExpression function = fromString(input.substring(1, index));
		final ILambdaExpression argument = fromString(input.substring(
				index + 1, input.length() - 1));
		result = new LambdaApplication(function, argument);
		return result;
	}

	private ILambdaExpression parseAbstraction(final String input, int index) {
		ILambdaExpression result = null;
		final String variableLabel = input.substring(1, index);
		if (StringUtils.caseOf(variableLabel) == StringCase.LOWER) {
			final ILambdaExpression bodyExpression = fromString(input
					.substring(index + 1, input.length() - 1));
			result = new LambdaAbstraction(variableLabel, bodyExpression);
		} else {
			throw new IllegalArgumentException(variableLabel + '|');
		}
		return result;
	}

	private ILambdaExpression parseSymbol(final String input) {
		ILambdaExpression result = null;
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
		return result;
	}
}
