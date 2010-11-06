package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for lambda integers.
 * 
 * @author The0retico
 * 
 */
public class LambdaIntegerTest extends LambdaFixtureTest {

	/**
	 * Constant integer value for fixture.
	 */
	private final Integer value = 1;

	@Before
	public final void setUp() {
		fixture = new LambdaInteger(value);
	}

	/**
	 * Integers are printed as numbers.
	 */
	@Test
	public final void toStringIsValueToString() {
		assertEquals(value.toString(), fixture.toString());
	}

	/**
	 * Integers are constants as well, so they bind no variables.
	 */
	@Test
	public final void integersBindNoVariables() {
		assertTrue("Integer should not bind any variables", fixture.free("x"));
	}

	/**
	 * Integers are not compound, so they have one subterm, itself.
	 */
	@Test
	public final void integerIsItsOwnSubterm() {
		final List<ILambdaExpression> subterms = fixture.subterm();
		assertNotNull(subterms);
		assertEquals(1, subterms.size());
		assertEquals(fixture, subterms.get(0));
	}

	/**
	 * Integers are not substituted.
	 */
	@Test
	public final void integersAreNotSubstituted() {
		final ILambdaExpression substituted = fixture.substitute(
				value.toString(), new LambdaMock("M"));
		assertEquals(fixture.toString(), substituted.toString());
	}

	/**
	 * Integers cannot be reduced using beta reduction.
	 */
	@Test
	public final void integersAreReducedToThemselves() {
		assertEquals(fixture, fixture.oneStepBetaReduce());
		assertEquals(new LambdaInteger(1), fixture.oneStepBetaReduce());
	}

	/**
	 * Integers cannot be reduced using beta reduction, so they are in normal
	 * form.
	 */
	@Test
	public final void integersAreInNormalForm() {
		assertEquals(fixture, fixture.normalForm());
		assertEquals(new LambdaInteger(1), fixture.normalForm());
	}
}
