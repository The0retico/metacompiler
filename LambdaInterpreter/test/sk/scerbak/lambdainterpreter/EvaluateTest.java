package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author The0retico Test beta reduction of lambda expressions
 */
public class EvaluateTest {

	/**
	 * Integers should be evaluated to their values.
	 */
	@Test
	public void integerEvaluation() {
		assertEquals("Lambda expression not evaluated properly", 0,
				new LambdaInteger(0).evaluate());
	}

	/**
	 * Test evaluation of large numbers.
	 */
	@Test
	public void maximumIntegerEvaluation() {
		final int maximum = Integer.MAX_VALUE;
		assertEquals("Lambda expression not evaluated properly", maximum,
				new LambdaInteger(maximum).evaluate());
	}

	/**
	 * Negative integers are not allowed.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeNumberEvaluation() {
		new LambdaInteger(-1).evaluate();
	}

	/**
	 * Negative integers are not allowed.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void minimumIntegerEvaluation() {
		final int minimum = Integer.MIN_VALUE;
		new LambdaInteger(minimum).evaluate();
	}

}
