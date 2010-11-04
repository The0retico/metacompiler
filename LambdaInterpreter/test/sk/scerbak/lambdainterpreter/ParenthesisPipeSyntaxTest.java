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
public class ParenthesisPipeSyntaxTest {

	/**
	 * Lambda expressions syntax implementation.
	 */
	private final ILambdaSyntax syntax = new ParenthesisPipeSyntax();

	/**
	 * Fixture containing lambda representation of 1.
	 */
	private final LambdaInteger one = new LambdaInteger(1);

	/**
	 * String containing 1.
	 */
	private final String oneString = "1";

	/**
	 * Integers are printed as numbers.
	 */
	@Test
	public final void oneToStringIs1() {
		assertEquals(one + " should be printed as " + oneString, oneString,
				syntax.toString(one));
	}

	/**
	 * Integer should be parsed as a LambdaInteger.
	 */
	@Test
	public final void parseInteger() {
		final ILambdaExpression expression = syntax.fromString(oneString);
		assertNotNull(oneString + " should be parsed successfully", expression);
		assertTrue(oneString + " should be parsed as Integer",
				expression instanceof LambdaInteger);
		assertEquals(one + " should be parsed as " + oneString, oneString,
				one.toString());
	}

	/**
	 * Lowercase letter should be parsed as LambdaVariable.
	 */
	@Test
	public final void parseVariable() {
		final ILambdaExpression expression = syntax.fromString("x");
		assertNotNull("x should be parsed successfully", expression);
		assertTrue("x should be parsed as LambdaVariable",
				expression instanceof LambdaVariable);
		assertEquals("Parsing error", new LambdaVariable("x"), expression);
	}

	/**
	 * Uppercase letter should be parsed as LambdaConstant.
	 */
	@Test
	public final void parseConstant() {
		final ILambdaExpression expression = syntax.fromString("Y");
		assertNotNull("Y should be parsed successfully", expression);
		assertTrue("Y should be parsed as LambdaVariable",
				expression instanceof LambdaConstant);
		assertEquals("Parsing error", new LambdaConstant("Y"), expression);
	}

	/**
	 * Abstractions are enclosed in parenthesis and have pipe between thir
	 * variable and body.
	 */
	@Test
	public final void parseAbstraction() {
		final LambdaAbstraction identity = new LambdaAbstraction("x",
				new LambdaInteger(1));
		final ILambdaExpression expression = syntax.fromString("(x|1)");
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
	public final void parseApplication() {
		final ILambdaExpression expression = syntax.fromString("(x y)");
		assertNotNull(expression);
		assertTrue(expression instanceof LambdaApplication);
		final LambdaApplication other = (LambdaApplication) expression;
		final LambdaApplication applyYtoX = new LambdaApplication(
				new LambdaVariable("x"), new LambdaVariable("y"));
		assertEquals(other + " should be " + applyYtoX, applyYtoX, other);
	}

}
