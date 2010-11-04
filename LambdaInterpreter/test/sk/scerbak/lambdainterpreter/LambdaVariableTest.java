package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
	private final LambdaVariable variableX = new LambdaVariable("x");

	/**
	 * Simplest example of a variable in a lambda expression.
	 */
	private final LambdaVariable variableY = new LambdaVariable("y");

	/**
	 * Every lambda variable at its own is free.
	 */
	@Test
	public final void variableWithoutContextIsFree() {
		assertTrue("Variable appears to be bound", variableX.free("x"));
	}

	/**
	 * Variable has no other subterms then itself.
	 */
	@Test
	public final void variableIsItsOwnSubterm() {
		final List<ILambdaExpression> subterms = variableX.subterm();
		assertNotNull(subterms);
		assertEquals(1, subterms.size());
		assertEquals(variableX, subterms.get(0));
	}

	/**
	 * When substitution variable has same label as this, this should be
	 * substituted.
	 */
	@Test
	public final void variablesWithSameNameAreSubstituted() {
		final ILambdaExpression substituted = variableX.substitute("x",
				variableY);
		assertNotNull(substituted);
		assertTrue(substituted instanceof LambdaVariable);
		assertEquals(variableY, substituted);
	}

	/**
	 * When substituting variable with variable with different label, no
	 * substitution should happen.
	 */
	@Test
	public final void variablesWithDifferentNamesAreNotSubstituted() {
		final ILambdaExpression substituted = variableX.substitute("y",
				variableY);
		assertNotNull(substituted);
		assertTrue(substituted instanceof LambdaVariable);
		assertEquals(variableX, substituted);
	}

	/**
	 * Two variables without context (not in lambda abstractions) are
	 * structuraly equal.
	 */
	@Test
	public final void variablesWithSameNamesAreEqual() {
		assertTrue(variableX.equals(variableX));
		assertFalse(variableX.equals(variableY));
		assertFalse(variableY.equals(variableX));
	}

	/**
	 * Lambda variable toString() is its label.
	 */
	@Test
	public final void variableToStringIsItsLabel() {
		assertEquals("x", variableX.toString());
	}
}
