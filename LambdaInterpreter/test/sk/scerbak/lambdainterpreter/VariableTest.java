package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static sk.scerbak.lambdainterpreter.Assertions.assertFree;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for lambda variables.
 * 
 * @author The0retico
 * 
 */
public class VariableTest {

	/**
	 * Fixture for this test.
	 */
	private IExpression fixture;

	/**
	 * Name of the fixture variable.
	 */
	private final String variableLabel = "x";

	/**
	 * Simplest example of a variable in a lambda expression.
	 */
	private final IExpression variableY = new Variable("y");

	private final IExpression variableX = new Variable("x");

	/**
	 * Variables cannot be reduced further, so they are in their normal form.
	 */
	@Test
	public final void constantsAreInNormalForm() {
		final IExpression normalForm = fixture.normalForm();
		assertEquals("Should be its normal form", fixture.toString(),
				normalForm.toString());
	}

	@Test
	public final void equalsIfEqualLabel() {
		assertTrue(variableX.equals(fixture));
		assertFalse(variableY.equals(fixture));
	}

	/**
	 * Set up fixture for this test.
	 */
	@Before
	public final void setUp() {
		fixture = new Variable(variableLabel);
	}

	/**
	 * Variable has no other subterms then itself.
	 */
	@Test
	public final void variableIsItsOwnSubterm() {
		final List<IExpression> subterms = fixture.subterm();
		assertNotNull(fixture + " should have subterms", subterms);
		assertEquals(1, subterms.size());
		assertEquals(fixture, subterms.get(0));
	}

	/**
	 * Variables should be reduced to themselves - they cannot be reduced
	 * further.
	 */
	@Test
	public final void variablesAreReducedToThemselves() {
		final IExpression reduced = fixture.oneStepBetaReduce();
		assertEquals("Should be reduced to itself", fixture.toString(),
				reduced.toString());
	}

	/**
	 * When substituting variable with variable with different label, no
	 * substitution should happen.
	 */
	@Test
	public final void variablesWithDifferentNamesAreNotSubstituted() {
		final IExpression substituted = fixture.substitute("y", variableY);
		assertNotNull(substituted);
		assertTrue(substituted instanceof Variable);
		assertEquals(fixture, substituted);
	}

	/**
	 * When substitution variable has same label as this, this should be
	 * substituted.
	 */
	@Test
	public final void variablesWithSameNameAreSubstituted() {
		final IExpression substituted = fixture.substitute(variableLabel,
				variableY);
		assertNotNull(fixture + " should be substituted", substituted);
		assertTrue(fixture + "should be substituted for a variable",
				substituted instanceof Variable);
		assertEquals(variableY, substituted);
	}

	/**
	 * Lambda variable toString() is its label.
	 */
	@Test
	public final void variableToStringIsItsLabel() {
		assertEquals(variableLabel, Printer.toString(fixture));
	}

	/**
	 * Every lambda variable at its own is free.
	 */
	@Test
	public final void variableWithoutContextIsFree() {
		assertFree(variableLabel, fixture);
	}
}
