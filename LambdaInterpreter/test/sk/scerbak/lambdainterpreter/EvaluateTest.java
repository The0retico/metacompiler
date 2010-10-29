package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EvaluateTest {

	@Test
	public void integerEvaluation() {
		assertEquals(0, new LambdaInteger(0).evaluate());
	}

	@Test
	public void maximumIntegerEvaluation() {
		final int maximum = Integer.MAX_VALUE;
		assertEquals(maximum, new LambdaInteger(maximum).evaluate());
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeNumberEvaluation() {
		new LambdaInteger(-1).evaluate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void minimumIntegerEvaluation() {
		final int minimum = Integer.MIN_VALUE;
		new LambdaInteger(minimum).evaluate();
	}

}
