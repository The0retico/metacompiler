package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Unit tests for parsing and printing lambda expressions in (x|x) syntax.
 * 
 * @author The0retico
 * 
 */
@RunWith(value = Parameterized.class)
public class ParserTest {

	/**
	 * Lambda expression fixture parameter for this test.
	 */
	private final IExpression fixture;

	/**
	 * String containing lambda expression as a parameter for this test.
	 */
	private final String expression;

	/**
	 * @return parameters for this test.
	 */
	@Parameters
	public static Collection<Object[]> stringExpressions() {
		final Object[][] expressions = new Object[][] { { "1" }, { "Y" },
				{ "x" }, { "(x|1)" }, { "(x y)" }, { "((x|x) y)" },
				{ "(x z y)" }, { "(PLUS 1 3 5 7 11 13)" }, { "(a b (c d))" } };
		return Arrays.asList(expressions);
	}

	/**
	 * @param stringExpression
	 *            expression containing lambda expression as a parameter for
	 *            this test
	 */
	public ParserTest(final String stringExpression) {
		this.expression = stringExpression;
		this.fixture = Parser.fromString(expression);
	}

	/**
	 * Test if expression has been parsed correctly to fixture.
	 */
	@Test
	public final void parseExpression() {
		assertNotNull(expression + " should be parsed to an expression",
				fixture);
		assertEquals(expression, fixture.toString());
	}

	/**
	 * Variable label in an abstraction should be all lower-case.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void fromMalformedAbstraction() {
		Parser.fromString("(y|(X|X))");
	}

	/**
	 * Empty string cannot be parsed to lambda expression.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void fromEmptyString() {
		Parser.fromString("");
	}

	/**
	 * Camel case is not allowed.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void fromCamelCaseString() {
		Parser.fromString("(camelCase x)");
	}

}
