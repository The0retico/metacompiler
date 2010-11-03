package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

/**
 * Unit tests for lambda application.
 * 
 * @author The0retico
 * 
 */
public class LambdaApplicationTest {

	/**
	 * Lambda variable X, used in other constants.
	 */
	private final LambdaVariable variableX = new LambdaVariable("x");

	/**
	 * Lambda abstraction for identity (aka I).
	 */
	private final LambdaAbstraction identity = new LambdaAbstraction("x",
			variableX);

	/**
	 * Lambda abstraction constantly returning variable X.
	 */
	private final LambdaAbstraction constantX = new LambdaAbstraction("y",
			variableX);

	/**
	 * ConstantX written as application of itself to identity.
	 */
	private final LambdaApplication appliedXToI = new LambdaApplication(
			identity, constantX);

	/**
	 * If variable is not bound in function nor argument, it is free.
	 */
	@Test
	public final void variableNotBoundInApplicationIsFree() {
		assertTrue("Variable should not be bound", appliedXToI.free("z"));
	}

	/**
	 * Lambda applications itself and subterms of function and argument are
	 * their subterms.
	 */
	@Test
	public final void itselfAndFunctionSubtermsAndArgumentSubterms() {
		final List<ILambdaExpression> subterms = appliedXToI.subterm();
		assertNotNull("Application should be its own subterm", subterms);
		final ILambdaExpression[] expected = { appliedXToI, identity,
				variableX, constantX, variableX };
		assertListContentEquals(expected, subterms);
	}

	/**
	 * Assert contents of actual list contents equal contents of expected array.
	 * 
	 * @param <T>
	 *            type
	 * 
	 * @param expected
	 *            array
	 * 
	 * @param actual
	 *            list
	 */
	private <T> void assertListContentEquals(final T[] expected,
			final List<T> actual) {
		assertEquals(expected + " should have size of " + actual,
				expected.length, actual.size());
		for (int i = 0; i < expected.length; i++) {
			assertEquals(actual.get(i) + " should be " + expected[i],
					expected[i], actual.get(i));
		}
	}

	/**
	 * Substitution to lambda applications should substitute to function and
	 * argument.
	 */
	// @Test
	// public final void substituteToFunctionAndArgument() {
	// final ILambdaExpression substituted = appliedXToI.substitute("x",
	// new LambdaVariable("z"));
	// assertNotNull("Every application substitution should be sucessful",
	// substituted);
	// assertTrue("Application should be substituted for application",
	// substituted instanceof LambdaApplication);
	// final LambdaApplication other = (LambdaApplication) substituted;
	// assertTrue(other + " should be " + appliedXToI,
	// appliedXToI.equals(other));
	// }
}
