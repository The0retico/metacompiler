package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static sk.scerbak.lambdainterpreter.Assertions.assertFree;
import static sk.scerbak.lambdainterpreter.Assertions.assertNormalizes;
import static sk.scerbak.lambdainterpreter.Assertions.assertNotFree;
import static sk.scerbak.lambdainterpreter.Assertions.assertSubstitutes;

import java.util.List;

import org.junit.Test;

/**
 * Proposed tests for functionality.
 * 
 * @author The0retico
 * 
 */
public class AcceptanceTests {

	/**
	 * Fixture for example1.
	 */
	private final IExpression fixture1 = Parser.fromString("(x|(y|(x z)))");

	/**
	 * Example 1 for free and subterm.
	 */
	@Test
	public final void example1() {
		assertNotFree("x", fixture1);
		assertFree("z", fixture1);
		final IExpression variableY = Parser.fromString("y");
		final List<IExpression> subterms = fixture1.subterm();
		assertFalse(subterms + " should not contain y",
				subterms.contains(variableY));
	}

	/**
	 * Fixture for example 2.
	 */
	private final IExpression fixture2 = Parser
			.fromString("(x|((y|y) (x (y|y))))");

	/**
	 * Example 2 for free and subterm.
	 */
	@Test
	public final void example2() {
		final List<IExpression> subterms = fixture2.subterm();
		int count = 0;
		final String identityY = "(y|y)";
		for (IExpression expression : subterms) {
			if (identityY.equals(expression.toString())) {
				count++;
			}
		}
		assertEquals(fixture2 + " should occure twice in " + identityY, 2,
				count);
	}

	/**
	 * Fixture for example 3.
	 */
	private final IExpression fixture3 = Parser.fromString("(w (x (y z)))");

	/**
	 * Example 3 of free and subterm.
	 */
	@Test
	public final void example3() {
		final List<IExpression> subterms = fixture3.subterm();
		final String subExpression = "(x (y z))";
		boolean contains = false;
		for (IExpression expression : subterms) {
			if (subExpression.equals(expression.toString())) {
				contains = true;
			}
		}
		assertTrue(fixture3 + " should contain " + subExpression, contains);
	}

	/**
	 * Fixture for example 4.
	 */
	private final IExpression fixture4 = Parser.fromString("(x|(z x))");

	/**
	 * Example 4 for substitution.
	 */
	@Test
	public final void example4() {
		assertSubstitutes("(x|(y x))", fixture4, "z", "y");
	}

	/**
	 * Fixture for example 5.
	 */
	private final IExpression fixture5 = Parser.fromString("(y|(z y))");

	/**
	 * Example 5 for substitution.
	 */
	@Test
	public final void example5() {
		assertSubstitutes("(v0|(y v0))", fixture5, "z", "y");
	}

	/**
	 * First fixture for exercise 1.
	 */
	private final IExpression fixtureE1A = Parser
			.fromString("((x|(x y)) (y|y))");

	/**
	 * First test for exercise 1.
	 */
	@Test
	public final void exercise1A() {
		assertNotFree("x", fixtureE1A);
		assertFree("y", fixtureE1A);
		assertNotFree("z", fixtureE1A);
	}

	/**
	 * Second fixture for exercise 1.
	 */
	private final IExpression fixtureE1B = Parser
			.fromString("(x|(y|(z ((z|z) (x|y)))))");

	/**
	 * Second test for exercise 1.
	 */
	@Test
	public final void exercise1B() {
		assertNotFree("x", fixtureE1B);
		assertNotFree("y", fixtureE1B);
		assertFree("z", fixtureE1B);
		assertNotFree("w", fixtureE1B);
	}

	/**
	 * Third fixture for exercise 1.
	 */
	private final IExpression fixtureE1C = Parser
			.fromString("((x|(y|((x z) (y z)))) (x|(y (y|y))))");

	/**
	 * Third test for exercise 1.
	 */
	@Test
	public final void exercise1C() {
		assertNotFree("x", fixtureE1C);
		assertFree("y", fixtureE1C);
		assertFree("z", fixtureE1C);
		assertNotFree("w", fixtureE1C);
	}

	/**
	 * First fixture for exercise 2.
	 */
	private final IExpression fixtureE2A = Parser
			.fromString("((x|(y|(x ((z|y) z)))) (((x|(v|v)) 8) (x|((w|w) x))))");

	/**
	 * Exercise 2 contains test for beta reduction.
	 */
	@Test
	public final void exercise2A() {
		assertNormalizes("(y|y)", fixtureE2A);
	}

	/**
	 * Second fixture for exercise 2.
	 */
	private final IExpression fixtureE2B = Parser
			.fromString("((h|((x|(h (x x))) (x|(h (x x))))) ((a|(b|a)) ((plus 1) 5)))");

	/**
	 * Second test for second exercise.
	 */
	@Test
	public final void exercise2B() {
		assertNormalizes("((plus 1) 5)", fixtureE2B);
	}

	/**
	 * Fixture for exercise 3.
	 */
	private final IExpression fixtureE3 = Parser
			.fromString("((((t|(t t)) (f|(x|(f (f x))))) (x|((plus x) 1))) 0)");

	/**
	 * Test for third exercise.
	 */
	@Test
	public final void exercise3() {
		assertNormalizes("((plus ((plus ((plus ((plus 0) 1)) 1)) 1)) 1)",
				fixtureE3);
	}
}
