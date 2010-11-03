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

	private final int numberOfZeroSubterms = 3;

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
		final List<ILambdaExpression> subterms = zero.subterm();
		assertNotNull("The expression itself is always its subterm", subterms);
		assertEquals("Zero should have " + numberOfZeroSubterms,
				numberOfZeroSubterms, subterms.size());
		assertEquals("Zero should contain itself as a subterm", zero,
				subterms.get(0));
		assertEquals("Zero should contain identity as its subterm", identity,
				subterms.get(1));
		assertEquals("Zero should contain variableX as its subterm", variableX,
				subterms.get(2));
	}

	/**
	 * Substitution for variable bound by lambda abstraction has no effect.
	 */
	@Test
	public final void boundVariablesCannotBeSubstituted() {
		final ILambdaExpression substituted = identity
				.substitute("x", identity);
		assertNotNull("Abstraction substitution should be successful",
				substituted);
		assertTrue("Abstraction should be substituted for abstraction",
				substituted instanceof LambdaAbstraction);
		assertEquals(substituted + " should be " + identity, identity,
				substituted);
	}

	// /**
	// * Substition of a variable bound in body of this lambda abstraction
	// should
	// * substitute the body.
	// */
	// @Test
	// public final void variableBoundInBodySubstitution() {
	// final ILambdaExpression substituted = zero.substitute("x", identity);
	// assertNotNull(substituted);
	// assertTrue(substituted instanceof LambdaAbstraction);
	// final LambdaAbstraction other = (LambdaAbstraction) substituted;
	// final LambdaAbstraction one = new LambdaAbstraction("f",
	// new LambdaAbstraction("x", identity));
	// assertTrue(other + " should be " + one, one.equals(other));
	// }

	/**
	 * Two lambda abstractions are structuraly equivalent, if their varialbes
	 * and bodies are equivalent.
	 */
	@Test
	public final void abstractionsEqualIfTheirVariableAndBodyDo() {
		assertEquals(zero + " should be " + zero, zero, zero);
		assertFalse(zero.equals(identity));
		assertFalse(identity.equals(zero));
		assertEquals(identity, new LambdaAbstraction("x", variableX));
	}
}
