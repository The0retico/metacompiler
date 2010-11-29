package sk.scerbak.lambdainterpreter;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Unit tests for parser error reporting.
 * 
 * @author The0retico
 * 
 */
@RunWith(Parameterized.class)
public class ParserFailureTest {

	/**
	 * @return parameters for this test.
	 */
	@Parameters
	public static Collection<Object[]> stringExpressions() {
		final Object[][] expressions = new Object[][] { { "(y|(X|X))" },
				{ "" }, { "(camelCase x)" } };
		return Arrays.asList(expressions);
	}

	/**
	 * Parameter which contains an expression which should not succeed to be
	 * parsed.
	 */
	private final String expression;

	/**
	 * @param stringExpression
	 *            expression containing lambda expression as a parameter for
	 *            this test
	 */
	public ParserFailureTest(final String stringExpression) {
		this.expression = stringExpression;
	}

	/**
	 * Test if exception thrown when parsing fails.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void parserFailure() {
		Parser.fromString(expression);
	}
}
