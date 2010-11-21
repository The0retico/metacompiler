package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.hasItems;

/**
 * Utility class containing unit test assertions for lambda expressions.
 * 
 * @author The0retico
 * 
 */
final class Assertions {

	/**
	 * @param mock
	 *            object on which methods in methodNames should have been called
	 * @param methodNames
	 *            list of methods that should be called on mock
	 */
	public static void assertCalled(final Mock mock,
			final String... methodNames) {
		assertThat(mock.getMethodCalls(), hasItems(methodNames));
	}

	/**
	 * Assertion for tests for free variables in lambda expressions.
	 * 
	 * @param label
	 *            name to check if free
	 * @param expression
	 *            which should be tested to have variable among its free
	 *            variables
	 * 
	 */
	static void assertFree(final String label, final IExpression expression) {
		assertTrue(label + " should be free in " + expression,
				expression.free(label));
	}

	/**
	 * @param expected
	 *            lambda expression in normal form
	 * @param actuallambda
	 *            expression of which normal form should be equal to expected
	 * 
	 */
	static void assertNormalizes(final IExpression expected,
			final IExpression actual) {
		final IExpression normalForm = actual.normalForm();
		assertNotNull(actual + "should have normal form", normalForm);
		assertEquals("Normal form of " + actual, expected, normalForm);
	}

	/**
	 * Assertion for testing normalization of lambda expressions.
	 * 
	 * @param expected
	 *            string representation of the resulting normal form.
	 * @param expression
	 *            which should be normalized
	 * 
	 */
	static void assertNormalizesString(final String expected,
			final IExpression expression) {
		final IExpression normalForm = expression.normalForm();
		assertNotNull(expression + "should have normal form", normalForm);
		assertEquals(expected, normalForm.toString());
	}

	/**
	 * Assertion for test checking for variable not being free in lambda
	 * expression.
	 * 
	 * @param variable
	 *            name to be checked if free
	 * @param expression
	 *            where variable should be free
	 * 
	 */
	static void assertNotFree(final String variable,
			final IExpression expression) {
		assertFalse(variable + " should not be free in " + expression,
				expression.free(variable));
	}

	/**
	 * @param expected
	 *            one step beta reduced actual lambda expression
	 * @param actual
	 *            lambda expression of which one step beta reduction should be
	 *            equal to expected
	 * 
	 */
	static void assertReduces(final IExpression expected,
			final IExpression actual) {
		final IExpression oneStepBetaReeduced = actual.oneStepBetaReduce();
		assertNotNull(actual + "should be beta reducible", oneStepBetaReeduced);
		assertEquals("One step beta reduction of " + actual, expected,
				oneStepBetaReeduced);
	}

	/**
	 * Assertion for substitution.
	 * 
	 * @param expected
	 *            string representation of substituted expression.
	 * @param expression
	 *            to which we will substitute.
	 * @param variable
	 *            label which should be substituted.
	 * @param substituent
	 *            TODO
	 */
	static void assertSubstitutes(final IExpression expected,
			final IExpression expression, final String variable,
			final IExpression substituent) {
		final IExpression substituted = expression.substitute(variable,
				substituent);
		assertNotNull(expression + " should be substituted", substituted);
		assertEquals(expected, substituted);
	}

	/**
	 * Utility class should not be instantiated.
	 */
	private Assertions() {

	}

}
