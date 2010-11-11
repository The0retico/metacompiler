package sk.scerbak.lambdainterpreter;

import sk.scerbak.utility.StringUtility;
import sk.scerbak.utility.StringUtility.StringCase;

/**
 * Parser and printer for lambda expressions.
 * 
 * @author The0retico lambda expression root interpreter with factory method
 */
/**
 * @author The0retico
 *
 */
/**
 * @author The0retico
 * 
 */
public final class Parser {

	/**
	 * Terminal for right parenthesis for enclosed expressions.
	 */
	private static final char RIGHT_CHAR = ')';
	/**
	 * Terminal for left parenthesis for enclosed expressions.
	 */
	private static final char LEFT_CHAR = '(';
	/**
	 * Terminal delimiter for application.
	 */
	private static final char APPLICATION_CHAR = ' ';
	/**
	 * Terminal delimiter for abstraction.
	 */
	private static final char ABSTRACTION_CHAR = '|';

	/**
	 * This is a Utility/Library class, which offers static methods, but cannot
	 * be instantiated.
	 */
	private Parser() {

	}

	/**
	 * @param input
	 *            string containing lambda expressions
	 * @return lambda expression (AST) representing the input
	 */
	public static IExpression fromString(final String input) {
		if (input.isEmpty()) {
			throw new IllegalArgumentException("empty string");
		}
		IExpression result = null;
		if (StringUtility.isWithin(input, LEFT_CHAR, RIGHT_CHAR)) {
			result = parseCompoundExpression(input);
		} else {
			result = parseSymbol(input);
		}
		return result;
	}

	/**
	 * @param input
	 *            string containing lambda expression enclosed in parenthesis
	 * @return expression on the input
	 */
	private static IExpression parseCompoundExpression(final String input) {
		IExpression result = null;
		final int index = indexOfTopLevelDelimiter(input, 1);
		final char delimiter = input.charAt(index);
		if (delimiter == APPLICATION_CHAR) {
			result = parseApplication(input, index);
		} else if (delimiter == ABSTRACTION_CHAR) {
			result = parseAbstraction(input, index);
		} else {
			throw new IllegalArgumentException(input);
		}
		return result;
	}

	/**
	 * @param input
	 *            string containing lambda expression enclosed in parenthesis
	 * @param fromIndex
	 *            TODO
	 * @return index of abstraction or application delimiter, which is on the
	 *         top level in teh expression
	 */
	private static int indexOfTopLevelDelimiter(final String input,
			final int fromIndex) {
		int index = fromIndex - 1;
		int counter = 0;
		char current;
		do {
			index++;
			current = input.charAt(index);
			counter = countLevelAt(counter, current);
		} while (index < input.length() - 1
				&& !(counter == 0 && isDelimiter(current)));
		return isDelimiter(current) ? index : -1;
	}

	/**
	 * @param character
	 *            which should be tested if application or abstraction delimiter
	 * @return true if character is either abstraction or application delimiter
	 */
	private static boolean isDelimiter(final char character) {
		return character == APPLICATION_CHAR || character == ABSTRACTION_CHAR;
	}

	/**
	 * @param counter
	 *            determining current level
	 * @param current
	 *            character to be considered when counting levels
	 * @return current level
	 */
	private static int countLevelAt(final int counter, final char current) {
		int level = counter;
		if (current == LEFT_CHAR) {
			level++;
		} else if (current == RIGHT_CHAR) {
			level--;
		}
		return level;
	}

	/**
	 * @param input
	 *            string containing lambda application
	 * @param index
	 *            of the top level application delimiter in input
	 * @return application representing the input
	 */
	private static IExpression parseApplication(final String input,
			final int index) {
		IExpression function = fromString(input.substring(1, index));
		int currentIndex = index;
		int nextIndex = indexOfApplicationDelimiter(input, currentIndex);
		IExpression argument = null;
		while (nextIndex != -1 && nextIndex != currentIndex) {
			final String nextInput = input.substring(currentIndex + 1,
					nextIndex);
			argument = fromString(nextInput);
			function = new Application(function, argument);
			currentIndex = nextIndex;
			nextIndex = indexOfApplicationDelimiter(input, currentIndex);
		}
		argument = fromString(input.substring(currentIndex + 1,
				input.length() - 1));
		return new Application(function, argument);
	}

	/**
	 * @param input
	 *            where to search
	 * @param fromIndex
	 *            where search and level counting should start
	 * @return index of top level application delimiter starting at fromIndex
	 */
	private static int indexOfApplicationDelimiter(final String input,
			final int fromIndex) {
		int index;
		do {
			index = indexOfTopLevelDelimiter(input, fromIndex + 1);
		} while (index != -1 && input.charAt(index) != APPLICATION_CHAR);
		return index;
	}

	/**
	 * @param input
	 *            string containing lambda abstraction enclosed in parenthesis
	 * @param index
	 *            of the abstraction delimiter on the top level
	 * @return abstraction representing the input
	 */
	private static IExpression parseAbstraction(final String input,
			final int index) {
		IExpression result = null;
		final String variableLabel = input.substring(1, index);
		if (StringUtility.caseOf(variableLabel) == StringCase.LOWER) {
			final IExpression bodyExpression = fromString(input.substring(
					index + 1, input.length() - 1));
			result = new Abstraction(variableLabel, bodyExpression);
		} else {
			throw new IllegalArgumentException(variableLabel);
		}
		return result;
	}

	/**
	 * @param input
	 *            string containing some type of lambda symbol
	 * @return lambda expression corresponding to the symbol on input
	 */
	private static IExpression parseSymbol(final String input) {
		IExpression result = null;
		final StringCase inputCase = StringUtility.caseOf(input);
		if (inputCase == StringCase.UPPER) {
			result = new Constant(input);
		} else if (inputCase == StringCase.LOWER) {
			result = new Variable(input);
		} else if (StringUtility.isInteger(input)) {
			final Integer value = Integer.valueOf(input);
			result = new Natural(value);
		} else {
			throw new IllegalArgumentException(input);
		}
		return result;
	}
}