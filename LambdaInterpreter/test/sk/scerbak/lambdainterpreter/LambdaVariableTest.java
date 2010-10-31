package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

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

	/**
	 * Variable has no other subterms then itself.
	 */
	@Test
	public final void variableIsItsOwnSubterm() {
		List<ILambdaExpression> subterms = simple.subterm();
		assertNotNull(subterms);
		assertEquals(1, subterms.size());
		assertEquals(simple, subterms.get(0));
	}
}
