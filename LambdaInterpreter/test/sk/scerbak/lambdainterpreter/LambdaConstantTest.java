package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

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

	/**
	 * Constants cannot be decomposed, so the only subterm is the constant.
	 */
	@Test
	public final void constantIsItsOnlySubterm() {
		final List<ILambdaExpression> subterms = constantY.subterm();
		assertNotNull(subterms);
		assertEquals(1, subterms.size());
		assertEquals(constantY, subterms.get(0));
	}
}
