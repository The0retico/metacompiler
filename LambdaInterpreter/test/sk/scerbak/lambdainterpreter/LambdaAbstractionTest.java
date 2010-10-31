package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

/**
 * Unit tests for LambdaAbstraction class.
 * 
 * @author The0retico
 * 
 */
public class LambdaAbstractionTest {

	/**
	 * Helper used in fixtures and tests.
	 */
	private final LambdaVariable variableX = new LambdaVariable("x");

	/**
	 * Simple lambda expression for identity function (aka I).
	 */
	private final LambdaAbstraction identity = new LambdaAbstraction("x",
			variableX);

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

	/**
	 * Abstractions subterms are the abstraction itself and all subterms of its
	 * body.
	 */
	@Test
	public final void itselfAndBodySubterms() {
		List<ILambdaExpression> subterms = zero.subterm();
		assertNotNull(subterms);
		final int numberOfZeroSubterms = 3;
		assertEquals(numberOfZeroSubterms, subterms.size());
		assertEquals(zero, subterms.get(0));
		assertEquals(identity, subterms.get(1));
		assertEquals(variableX, subterms.get(2));
	}
}
