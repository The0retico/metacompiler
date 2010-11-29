package sk.scerbak.utility;

/**
 * @author The0retico Utility/Library class with methods on String
 */
/**
 * @author The0retico
 * 
 */
public final class StringUtility {

	/**
	 * Utility/Library classes should not be instantiated.
	 */
	private StringUtility() {

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

	/**
	 * Enumeration representing possible case of strings.
	 * 
	 * @author The0retico
	 * 
	 */
	public enum StringCase {
		/**
		 * Case of a string is upper if each of its characters is a upper-case
		 * letter.
		 */
		UPPER,
		/**
		 * Case of a string is lower if each of its characters is a lower-case
		 * letter.
		 */
		LOWER,
		/**
		 * Case of a string is undefined if above cases do not apply.
		 */
		UNDEFINED
	}

	/**
	 * Dedicates if every character in a string is upper-case letter.
	 * 
	 * @param input
	 *            string
	 * @return true if input contains only upper-case letters, false otherwise
	 */
	public static StringCase caseOf(final String input) {
		Character nextChar = input.charAt(0);
		StringCase current = caseOf(nextChar);
		for (int index = 1; current != StringCase.UNDEFINED
				&& index < input.length() - 1; index++) {
			nextChar = input.charAt(index);
			if (current != caseOf(nextChar)) {
				current = StringCase.UNDEFINED;
			}
		}
		return current;
	}

	/**
	 * @param character
	 *            to be examined
	 * @return case of this character
	 */
	private static StringCase caseOf(final char character) {
		StringCase result;
		if (Character.isLetter(character)) {
			if (Character.isUpperCase(character)) {
				result = StringCase.UPPER;
			} else {
				result = StringCase.LOWER;
			}
		} else {
			result = StringCase.UNDEFINED;
		}
		return result;
	}

	/**
	 * @param input
	 *            string
	 * @param opening
	 *            first character
	 * @param closing
	 *            last character
	 * @return true if input starts with opening and ends with closing, false
	 *         otherwise
	 */
	public static boolean isWithin(final String input, final char opening,
			final char closing) {
		final char firstChar = input.charAt(0);
		final int lastIndex = input.length() - 1;
		final char lastChar = input.charAt(lastIndex);
		return firstChar == opening && lastChar == closing;
	}
}
