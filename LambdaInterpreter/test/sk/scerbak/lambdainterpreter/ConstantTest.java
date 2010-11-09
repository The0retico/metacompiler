package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static sk.scerbak.lambdainterpreter.Assertions.assertNotFree;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for built-in lambda constants.
 * 
 * @author The0retico
 * 
 */
public class ConstantTest {

	/**
	 * Fixture for this test.
	 */
	private IExpression fixture;

	/**
	 * Constant value for name of fixture.
	 */
	private final String value = "X";

	/**
	 * Prepare fixture for tests.
	 */
	@Before
	public final void setUp() {
		fixture = new Constant(value);
	}

	/**
	 * Constants should not bound variables.
	 */
	@Test
	public final void constantsHaveNoFreeVariables() {
		assertNotFree("x", fixture);
	}

	/**
	 * Constants cannot be decomposed, so the only subterm is the constant.
	 */
	@Test
	public final void constantIsItsOnlySubterm() {
		final List<IExpression> subterms = fixture.subterm();
		assertNotNull(fixture + " should have subterms", subterms);
		assertEquals(1, subterms.size());
		assertEquals(fixture.toString(), subterms.get(0).toString());
	}

	/**
	 * Constants cannot be substituted.
	 */
	@Test
	public final void constantsShouldNotBeSubstituted() {
		final IExpression substituted = fixture.substitute("y", new Mock("M"));
		assertEquals(fixture.toString(), substituted.toString());
	}

	/**
	 * Constants can not be reduced.
	 */
	@Test
	public final void constantsAreNotReducible() {
		assertFalse(fixture + " should not be reducible", fixture.isReducible());
	}

	/**
	 * Constants should be reduced to themselves - they cannot be reduced
	 * further.
	 */
	@Test
	public final void constantsAreReducedToThemselves() {
		final IExpression reduced = fixture.oneStepBetaReduce();
		assertEquals("Should be reduced to itself", fixture.toString(),
				reduced.toString());
	}

	/**
	 * Constants cannot be reduced further, so they are in their normal form.
	 * Consider subclassing a constant to create reducible constants.
	 */
	@Test
	public final void constantsAreInNormalForm() {
		final IExpression normalForm = fixture.normalForm();
		assertEquals("Should be its normal form", fixture.toString(),
				normalForm.toString());
	}
}
