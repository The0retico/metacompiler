package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LambdaAssert {

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
	public static void assertFree(final String label,
			final IExpression expression) {
		assertTrue(label + " should be free in " + expression,
				expression.free(label));
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
	public static void assertNotFree(final String variable,
			final IExpression expression) {
		assertFalse(variable + " should not be free in " + expression,
				expression.free(variable));
	}

}
