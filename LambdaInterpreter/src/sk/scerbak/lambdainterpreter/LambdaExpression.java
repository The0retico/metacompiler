package sk.scerbak.lambdainterpreter;

public abstract class LambdaExpression {
	public abstract int evaluate();

	public static LambdaExpression fromString(String string) {
		if (isInteger(string)) {
			return new LambdaInteger(Integer.parseInt(string));
		}
		final String unpackedString = substringOfWithin(string, '(', ')');
		if (!unpackedString.isEmpty()) {
			final int delimiterIndex = unpackedString.indexOf("|");
			if (delimiterIndex == -1) {
				throw new IllegalArgumentException(unpackedString);
			}
			final String variable = unpackedString.substring(delimiterIndex);
			final String expression = substringOfAfter(unpackedString,
					delimiterIndex);
			return new LambdaAbstraction(variable, fromString(expression));
		}
		throw new IllegalArgumentException(string);
	}

	private static String substringOfWithin(String string, char opening,
			char closing) {
		final int openingIndex = string.indexOf(opening);
		final int closingIndex = string.lastIndexOf(closing);
		if (openingIndex == -1 || openingIndex >= closingIndex) {
			throw new IllegalArgumentException(string + opening + closing);
		}
		return string.substring(openingIndex, closingIndex);
	}

	private static String substringOfAfter(final String string, final int index) {
		return string.substring(index + 1, string.length());
	}

	private static boolean isInteger(String string) {
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
