package sk.scerbak.lambdainterpreter;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
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
		for (IExpression expression : subterms) {
			if (identity.equals(expression)) {
				count++;
			}
		}
		assertEquals(
				def("x").apply(def("y").var("y"),
						apply(var("x"), def("y").var("y")))
						+ " should occure twice in " + identity, 2, count);
	}

	/**
	 * Example 3 of free and subterm.
	 */
	@Test
	public final void example3() {
		final List<IExpression> subterms = apply(var("w"),
				apply(var("x"), apply(var("y"), var("z")))).subterm();
		assertThat(subterms,
				hasItem(apply(var("x"), apply(var("y"), var("z")))));
	}

	/**
	 * Example 4 for substitution.
	 */
	@Test
	public final void example4() {
		assertSubstitutes("(x|(y x))", def("x").apply(var("z"), var("x")), "z",
				"y");
	}

	/**
	 * Example 5 for substitution.
	 */
	@Test
	public final void example5() {
		assertSubstitutes("(v0|(y v0))", def("y").apply(var("z"), var("y")),
				"z", "y");
	}

	/**
	 * First fixture for exercise 1.
	 */
	private final IExpression fixtureE1A = apply(
			def("x").apply(var("x"), var("y")), def("y").var("y"));

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
	private final IExpression fixtureE1B = def("x", "y").apply(var("z"),
			apply(def("z").var("z"), def("x").var("y")));

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
	private final IExpression fixtureE1C = apply(
			def("x", "y").apply(var("x"), var("z"), apply(var("y"), var("z"))),
			def("x").apply(var("y"), def("y").var("y")));

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
		assertNormalizes(def("y").var("y"), fixtureE2A);
	}

	/**
	 * Second fixture for exercise 2.
	 */
	private final IExpression fixtureE2B = Parser
			.fromString("((h|((x|(h (x x))) (x|(h (x x))))) ((a|(b|a)) (PLUS 1 5)))");

	/**
	 * Second test for second exercise.
	 */
	@Test
	public final void exercise2B() {
		assertNormalizes(nat(6), fixtureE2B);
	}

	/**
	 * Fixture for exercise 3.
	 */
	private final IExpression fixtureE3 = Parser
			.fromString("((((t|(t t)) (f|(x|(f (f x))))) (x|((PLUS x) 1))) 0)");
	private final IExpression identity = def("y").var("y");

	/**
	 * Test for third exercise.
	 */
	@Test
	public final void exercise3() {
		assertNormalizes(nat(4), fixtureE3);
	}
}
