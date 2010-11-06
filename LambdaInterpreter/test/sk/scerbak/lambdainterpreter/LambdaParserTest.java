package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for parsing and printing lambda expressions in (x|x) syntax.
 * 
 * @author The0retico
 * 
 */
public class LambdaParserTest {

	/**
	 * Lambda expressions syntax implementation.
	 */
	private final ILambdaSyntax parser = new LambdaParser();

	/**
	 * Integer should be parsed as a LambdaInteger.
	 */
	@Test
	public final void parseInteger() {
		final Integer value = 1;
		final ILambdaExpression expression = parser
				.fromString(value.toString());
		assertNotNull(value + " should be parsed to an expression", expression);
		assertEquals(value.toString(), expression.toString());
	}

	/**
	 * Upper-case identificators should be parsed as LambdaConstant.
	 */
	@Test
	public final void parseConstant() {
		final String value = "Y";
		final ILambdaExpression expression = parser.fromString(value);
		assertNotNull(value + " should be parsed to an expression", expression);
		assertEquals(value, expression.toString());
	}

	/**
	 * Lowercase letter should be parsed as LambdaVariable.
	 */
	@Test
	public final void parseVariable() {
		final String value = "x";
		final ILambdaExpression expression = parser.fromString(value);
		assertNotNull(value + " should be parsed as expression", expression);
		assertEquals(value, expression.toString());
	}

	/**
	 * Abstractions are enclosed in parenthesis and have pipe between thir
	 * variable and body.
	 */
	@Test
	public final void parseAbstraction() {
		final LambdaAbstraction identity = new LambdaAbstraction("x",
				new LambdaInteger(1));
		final ILambdaExpression expression = parser.fromString("(x|1)");
		assertNotNull(expression);
		assertTrue(expression instanceof LambdaAbstraction);
		final LambdaAbstraction other = (LambdaAbstraction) expression;
		assertEquals(identity, other);
	}

	/**
	 * Application is enclosed in parenthesis and function and argument are
	 * separated by space.
	 */
	@Test
	public final void parseApplicationWithAbstraction() {
		final ILambdaExpression expression = parser.fromString("((x|x) y)");
		assertNotNull(expression);
		assertEquals("((x|x) y)", expression.toString());
	}

	/**
	 * Application is enclosed in parenthesis and function and argument are
	 * separated by space.
	 */
	@Test
	public final void parseApplication() {
		final ILambdaExpression expression = parser.fromString("(x y)");
		assertNotNull(expression);
		assertEquals("(x y)", expression.toString());
	}

	/**
	 * Application is enclosed in parenthesis and function and argument are
	 * separated by space.
	 */
	@Test
	public final void parseNestedApplications() {
		final ILambdaExpression expression = parser.fromString("((x z) y)");
		assertNotNull(expression);
		assertEquals("((x z) y)", expression.toString());
	}
}
