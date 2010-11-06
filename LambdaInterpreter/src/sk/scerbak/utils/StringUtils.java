package sk.scerbak.utils;


/**
 * @author The0retico Utility/Library class with methods on String
 */
public final class StringUtils {

	/**
	 * Utility/Library classes should not be instantiated.
	 */
	private StringUtils() {

	}

	/**
	 * @param string
	 *            input
	 * @param index
	 *            after which will start the result
	 * @return substring of string starting after index
	 */
	public static String substringOfAfter(final String string, final int index) {
		return string.substring(index + 1, string.length());
	}

	/**
	 * @param string
	 *            input containing an integer
	 * @return false if string cannot be parsed to an Integer, true otherwise
	 */
	public static boolean isInteger(final String string) {
		boolean result = true;
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException e) {
			result = false;
		}
		return result;
	}

	public enum StringCase {
		UPPER, LOWER, UNDEFINED
	}

	/**
	 * Dedicates if every character in a string is upper-case letter.
	 * 
	 * @param input
	 *            string
	 * @return true if input contains only upper-case letters, false otherwise
	 */
	public static StringCase caseOf(final String input) {
		int index = 0;
		char nextChar = input.charAt(index);
		StringCase current = caseOf(nextChar);
		while (current != StringCase.UNDEFINED && index < input.length() - 1) {
			index++;
			nextChar = input.charAt(index);
			current = current == caseOf(nextChar) ? current
					: StringCase.UNDEFINED;
		}
		return current;
	}

	private static StringCase caseOf(char current) {
		StringCase result;
		if (!Character.isLetter(current)) {
			result = StringCase.UNDEFINED;
		} else {
			result = Character.isUpperCase(current) ? StringCase.UPPER
					: StringCase.LOWER;
		}
		return result;
	}
}
