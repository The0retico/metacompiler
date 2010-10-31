package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for LambdaAbstraction class.
 * 
 * @author The0retico
 * 
 */
public class LambdaAbstractionTest {

	/**
	 * Simple lambda expression for identity function (aka I).
	 */
	private final LambdaAbstraction identity = new LambdaAbstraction("x",
			new LambdaVariable("x"));

	/**
	 * Compound lambda abstraction representing 0 in Church numbering.
	 */
	private final LambdaAbstraction zero = new LambdaAbstraction("f", identity);

	/**
	 * Variable bound in this abstraction is not free.
	 */
	@Test
	public final void boundVariableIsNotFree() {
		assertFalse("Identity should bound its argument", identity.free("x"));
	}

	/**
	 * Variable which is not bound in this abstraction is free.
	 */
	@Test
	public final void variableNotBoundIsFree() {
		assertTrue("Variable should not be bound", identity.free("y"));
	}

	/**
	 * Variable in compound abstractions can be bound in any of them.
	 */
	@Test
	public final void variableBoundElsewhereIsNotFree() {
		assertFalse("Variable should be bound also by subterm", zero.free("x"));
	}
}
