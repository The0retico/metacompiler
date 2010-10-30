package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author The0retico Test parsing of lambda expressions.
 */
public class FromStringTest {

	/**
	 * Large Integer to test correct parsing of multiple char numbers.
	 */
	private static final Integer BIG_NUMBER = 999999999;
	/**
	 * Simple lambda abstraction String representation.
	 */
	private static final String ABSTRACTION = "(x|0)";

	/**
	 * @param value
	 *            integer to be tested
	 */
	private void assertIntegerFromString(final int value) {
		final String string = String.valueOf(value);
		final AbstractLambdaExpression number = AbstractLambdaExpression.fromString(string);
		assertTrue(value + " not recognized as an integer",
				number instanceof LambdaInteger);
		assertEquals("Lambda expression evaluated incorrectly", value,
				number.evaluate());
	}

	/**
	 * Test simple integer parsing.
	 */
	@Test
	public void integerFromString() {
		assertIntegerFromString(0);
	}

	/**
	 * Test parsing of multiple character numbers.
	 */
	@Test
	public void bigNumberFromString() {
		assertIntegerFromString(BIG_NUMBER);
	}

	/**
	 * Test handling of empty string by throwing an exception.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void nothingFromEmptyString() {
		AbstractLambdaExpression.fromString("");
	}

	/**
	 * Test handling of inappropriate parenthesis placement.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void nothingFromParenthesisInString() {
		AbstractLambdaExpression.fromString("(0)");
	}

	/**
	 * Test parsing of a simple lambda abstraction.
	 */
	@Test
	public void abstractionFromString() {
		final AbstractLambdaExpression expression = AbstractLambdaExpression
				.fromString(ABSTRACTION);
		assertTrue(ABSTRACTION + " is not a lambda expression",
				expression instanceof LambdaAbstraction);
	}
}
