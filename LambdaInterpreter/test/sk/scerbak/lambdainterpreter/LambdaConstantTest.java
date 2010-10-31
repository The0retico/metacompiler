package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for built-in lambda constants.
 * 
 * @author The0retico
 * 
 */
public class LambdaConstantTest {

	/**
	 * Simple constant.
	 */
	private final LambdaConstant constantY = new LambdaConstant("Y");

	/**
	 * Constants should not bound variables.
	 */
	@Test
	public final void constantsBindNoVariables() {
		assertTrue("Constants should not bind variables", constantY.free("x"));
	}

}
