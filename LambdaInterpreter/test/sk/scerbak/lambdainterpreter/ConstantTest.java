package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
	public final void constantsBindNoVariables() {
		assertTrue("Constants should not bind variables", fixture.free("x"));
	}

	/**
	 * Constants cannot be decomposed, so the only subterm is the constant.
	 */
	@Test
	public final void constantIsItsOnlySubterm() {
		final List<IExpression> subterms = fixture.subterm();
		assertNotNull(subterms);
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

}
