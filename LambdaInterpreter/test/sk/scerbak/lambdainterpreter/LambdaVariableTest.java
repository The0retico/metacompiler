package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for lambda variables.
 * 
 * @author The0retico
 * 
 */
public class LambdaVariableTest {

	/**
	 * Simplest example of a variable in a lambda expression.
	 */
	private final LambdaVariable simple = new LambdaVariable("x");

	/**
	 * Every lambda variable at its own is free.
	 */
	@Test
	public final void variableWithoutContextIsFree() {
		assertTrue("Variable appears to be bound", simple.free("x"));
	}
}
