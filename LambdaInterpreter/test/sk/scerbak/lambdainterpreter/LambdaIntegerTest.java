package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

/**
 * Unit test for lambda integers.
 * 
 * @author The0retico
 * 
 */
public class LambdaIntegerTest {

	/**
	 * Lambda integer representing number 1.
	 */
	private final LambdaInteger one = new LambdaInteger(1);
	/**
	 * Lambda integer representing number 0.
	 */
	private final ILambdaExpression zero = new LambdaInteger(0);

	/**
	 * Integers are constants as well, so they bind no variables.
	 */
	@Test
	public final void integersBindNoVariables() {
		assertTrue("Integer should not bind any variables", one.free("x"));
	}

	/**
	 * Integers are not compound, so they have one subterm, itself.
	 */
	@Test
	public final void integerIsItsOwnSubterm() {
		final List<ILambdaExpression> subterms = one.subterm();
		assertNotNull(subterms);
		assertEquals(1, subterms.size());
		assertEquals(one, subterms.get(0));
	}

	/**
	 * Integers are not substituted.
	 */
	@Test
	public final void integersAreNotSubstituted() {
		final ILambdaExpression substituted = one.substitute("1", zero);
		assertNotNull(substituted);
		assertTrue(substituted instanceof LambdaInteger);
		assertEquals(one, substituted);
	}

	/**
	 * Two lambda integers are structuraly equal if they have same value.
	 */
	@Test
	public final void integersWithSameValueAreEqual() {
		assertTrue(one.equals(one));
		assertTrue(one.equals(new LambdaInteger(1)));
		assertFalse(one.equals(zero));
		assertFalse(zero.equals(one));
	}

	/**
	 * Integers cannot be reduced using beta reduction.
	 */
	@Test
	public final void integersAreReducedToThemselves() {
		assertEquals(one, one.oneStepBetaReduce());
		assertEquals(new LambdaInteger(1), one.oneStepBetaReduce());
	}

	/**
	 * Integers cannot be reduced using beta reduction, so they are in normal
	 * form.
	 */
	@Test
	public final void integersAreInNormalForm() {
		assertEquals(one, one.normalForm());
		assertEquals(new LambdaInteger(1), one.normalForm());
	}
}
