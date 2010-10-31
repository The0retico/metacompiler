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
	 * @param opening
	 *            character after which result starts
	 * @param closing
	 *            character before which result ends
	 * @return extracted substring within opening and closing characters
	 */
	public static String substringOfWithin(final String string,
			final char opening, final char closing) {
		final int openingIndex = string.indexOf(opening);
		final int closingIndex = string.lastIndexOf(closing);
		if (openingIndex == -1 || openingIndex >= closingIndex) {
			throw new IllegalArgumentException(string + opening + closing);
		}
		return string.substring(openingIndex, closingIndex);
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

}
