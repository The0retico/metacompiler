package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FromStringTest {

	private void assertIntegerFromString(int value, String string) {
		final LambdaExpression number = LambdaExpression.fromString(string);
		assertTrue(number instanceof LambdaInteger);
		assertEquals(value, number.evaluate());
	}

	@Test
	public void integerFromString() {
		assertIntegerFromString(0, "0");
	}

	@Test
	public void bigNumberFromString() {
		assertIntegerFromString(999999999, "999999999");
	}

	@Test(expected = IllegalArgumentException.class)
	public void nothingFromEmptyString() {
		LambdaExpression.fromString("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void nothingFromParenthesisInString() {
		LambdaExpression.fromString("(0)");
	}

	@Test
	public void abstractionFromString() {
		final LambdaExpression expression = LambdaExpression
				.fromString("(x|0)");
		assertTrue(expression instanceof LambdaAbstraction);
	}
}
