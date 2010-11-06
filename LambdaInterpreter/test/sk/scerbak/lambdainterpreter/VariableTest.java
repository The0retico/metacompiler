package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
	 * Set up fixture for this test.
	 */
	@Before
	public final void setUp() {
		fixture = new Variable(variableLabel);
	}

	/**
	 * Simplest example of a variable in a lambda expression.
	 */
	private final Variable variableY = new Variable("y");

	/**
	 * Lambda variable toString() is its label.
	 */
	@Test
	public final void variableToStringIsItsLabel() {
		assertEquals(variableLabel, fixture.toString());
	}

	/**
	 * Every lambda variable at its own is free.
	 */
	@Test
	public final void variableWithoutContextIsFree() {
		assertTrue("Variable appears to be bound", fixture.free(variableLabel));
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
	 * When substitution variable has same label as this, this should be
	 * substituted.
	 */
	@Test
	public final void variablesWithSameNameAreSubstituted() {
		final IExpression substituted = fixture.substitute(variableLabel,
				variableY);
		assertNotNull(fixture + " should be substituted", substituted);
		assertTrue(substituted instanceof Variable);
		assertEquals(variableY, substituted);
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

}
