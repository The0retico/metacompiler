package sk.scerbak.lambdainterpreter;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.hasItem;
import static sk.scerbak.lambdainterpreter.Assertions.assertFree;
import static sk.scerbak.lambdainterpreter.Assertions.assertNormalizes;
import static sk.scerbak.lambdainterpreter.Assertions.assertNotFree;
import static sk.scerbak.lambdainterpreter.Assertions.assertSubstitutes;
import static sk.scerbak.lambdainterpreter.Calculus.apply;
import static sk.scerbak.lambdainterpreter.Calculus.def;
import static sk.scerbak.lambdainterpreter.Calculus.nat;
import static sk.scerbak.lambdainterpreter.Calculus.var;

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
	 * First fixture for exercise 1.
	 */
	private final IExpression fixtureE1A = apply(
			def("x").apply(var("x"), var("y")), def("y").var("y"));

	/**
	 * Second fixture for exercise 1.
	 */
	private final IExpression fixtureE1B = def("x", "y").apply(var("z"),
			apply(def("z").var("z"), def("x").var("y")));

	/**
	 * Third fixture for exercise 1.
	 */
	private final IExpression fixtureE1C = apply(
			def("x", "y").apply(var("x"), var("z"), apply(var("y"), var("z"))),
			def("x").apply(var("y"), def("y").var("y")));

	/**
	 * First fixture for exercise 2.
	 */
	private final IExpression fixtureE2A = Parser
			.fromString("((x|(y|(x ((z|y) z)))) (((x|(v|v)) 8) (x|((w|w) x))))");

	/**
	 * Second fixture for exercise 2.
	 */
	private final IExpression fixtureE2B = Parser
			.fromString("((h|((x|(h (x x))) (x|(h (x x))))) ((a|(b|a)) (PLUS 1 5)))");

	/**
	 * Fixture for exercise 3.
	 */
	private final IExpression fixtureE3 = Parser
			.fromString("((((t|(t t)) (f|(x|(f (f x))))) (x|((PLUS x) 1))) 0)");

	/**
	 * Example 1 for free and subterm.
	 */
	@Test
	public final void example1() {
		assertNotFree("x", def("x", "y").apply(var("x"), var("z")));
		assertFree("z", def("x", "y").apply(var("x"), var("z")));
		assertThat(def("x", "y").apply(var("x"), var("z")).subterm(),
				not(hasItem(var("y"))));
	}

	/**
	 * Example 2 for free and subterm.
	 */
	@Test
	public final void example2() {
		final List<IExpression> subterms = def("x").apply(def("y").var("y"),
				apply(var("x"), def("y").var("y"))).subterm();
		int count = 0;
		for (final IExpression expression : subterms) {
			if (def("y").var("y").equals(expression)) {
				count++;
			}
		}
		assertEquals(
				def("x").apply(def("y").var("y"),
						apply(var("x"), def("y").var("y")))
						+ " should occure twice in " + def("y").var("y"), 2,
				count);
	}

	/**
	 * Example 3 of free and subterm.
	 */
	@Test
	public final void example3() {
		final List<IExpression> subterms = apply(var("w"),
				apply(var("x"), apply(var("y"), var("z")))).subterm();
		final IExpression expected = apply(var("x"), apply(var("y"), var("z")));
		boolean result = false;
		for (final IExpression expression : subterms) {
			if (expression.equals(expected)) {
				result = true;
			}
		}
		assertTrue(result);
	}

	/**
	 * Example 4 for substitution.
	 */
	@Test
	public final void example4() {
		assertSubstitutes(def("x").apply(var("y"), var("x")),
				def("x").apply(var("z"), var("x")), "z", var("y"));
	}

	/**
	 * Example 5 for substitution.
	 */
	@Test
	public final void example5() {
		assertSubstitutes(def("v0").apply(var("y"), var("v0")),
				def("y").apply(var("z"), var("y")), "z", var("y"));
	}

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
	 * Exercise 2 contains test for beta reduction.
	 */
	@Test
	public final void exercise2A() {
		assertNormalizes(def("y").var("y"), fixtureE2A);
	}

	/**
	 * Second test for second exercise.
	 */
	@Test
	public final void exercise2B() {
		assertNormalizes(nat(6), fixtureE2B);
	}

	/**
	 * Test for third exercise.
	 */
	@Test
	public final void exercise3() {
		assertNormalizes(nat(4), fixtureE3);
	}
}
