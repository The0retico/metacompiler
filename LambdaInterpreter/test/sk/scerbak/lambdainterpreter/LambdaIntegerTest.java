package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for lambda integers.
 * 
 * @author The0retico
 * 
 */
public class LambdaIntegerTest {

	/**
	 * Lambda integer representing number 1.
	 */
	private final LambdaInteger one = new LambdaInteger(1);

	/**
	 * Integers are constants as well, so they bind no variables.
	 */
	@Test
	public final void integersBindNoVariables() {
		assertTrue("Integer should not bind any variables", one.free("x"));
	}
}
