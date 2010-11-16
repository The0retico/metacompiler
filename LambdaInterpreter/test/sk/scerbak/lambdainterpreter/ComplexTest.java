package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static sk.scerbak.lambdainterpreter.Assertions.assertNormalizes;
import static sk.scerbak.lambdainterpreter.Calculus.apply;
import static sk.scerbak.lambdainterpreter.Calculus.con;
import static sk.scerbak.lambdainterpreter.Calculus.def;
import static sk.scerbak.lambdainterpreter.Calculus.nat;
import static sk.scerbak.lambdainterpreter.Calculus.var;

import org.junit.Test;

/**
 * Tests presenting interpreter functionality on more complex lambda
 * expressions.
 * 
 * @author The0retico
 * 
 */
public class ComplexTest {

	/**
	 * Addition of Church numbers.
	 */
	@Test
	public final void additionOnChurchNumbers() {
		assertNormalizes(
				"(f|(x|(f (f (f x)))))",
				apply(def("m", "n", "f", "x").apply(var("m"), var("f"),
						apply(var("n"), var("f"), var("x"))), Calculus.nat(1),
						Calculus.nat(2)));
	}

	/**
	 * Zero and successor.
	 */
	@Test
	public final void successorForChurchNumbers() {
		final IExpression succ = def("n", "f", "x").apply(var("f"),
				apply(var("n"), var("f"), var("x")));
		final IExpression one = apply(succ, Calculus.nat(0));
		assertNormalizes("(f|(x|(f x)))", one);
		final IExpression two = apply(succ, one);
		assertNormalizes("(f|(x|(f (f x))))", two);
		final IExpression three = apply(succ, two);
		assertNormalizes("(f|(x|(f (f (f x)))))", three);
	}

	/**
	 * Multiplication of church numbers.
	 */
	@Test
	public final void multiplicationOfChurchNumbers() {
		final IExpression mult = def("m", "n", "f").apply(var("n"),
				apply(var("m"), var("f")));
		assertNormalizes("(f|(x|(f (f (f (f (f (f x))))))))",
				apply(mult, Calculus.nat(2), Calculus.nat(3)));
	}

	/**
	 * Exponential function on church numbers.
	 */
	@Test
	public final void exponentialOnChurchNumbers() {
		final IExpression exp = def("m", "n", "f").apply(var("n"), var("m"),
				var("f"));
		assertNormalizes("(f|(x|(f (f (f (f x))))))",
				apply(exp, Calculus.nat(2), Calculus.nat(2)));
	}

	/**
	 * Predecessor on church numbers.
	 */
	@Test
	public final void predecessorOnChurchNubmers() {
		final IExpression pred = Parser
				.fromString("(n|(f|(x|((n (g|(h|(h (g f))))) (u|x) (u|u)))))");
		assertNormalizes("(f|(x|(f (f x))))", apply(pred, Calculus.nat(3)));
	}

	/**
	 * And function on booleans.
	 */
	@Test
	public final void andOnBooleans() {
		final IExpression ltrue = def("x", "y").var("x");
		final IExpression lfalse = def("x", "y").var("y");
		final IExpression and = def("x", "y").apply(var("x"), var("y"),
				def("x", "y").var("y"));
		assertNormalizes("(x|(y|y))", apply(and, ltrue, lfalse));
	}

	/**
	 * Xor function on booleans.
	 */
	@Test
	public final void xorOnBooleans() {
		final String strue = "(x|(y|x))";
		final String sfalse = "(x|(y|y))";
		final IExpression ltrue = Parser.fromString(strue);
		final IExpression lfalse = Parser.fromString(sfalse);
		final IExpression and = def("x", "y").apply(var("x"), var("y"), lfalse);
		final IExpression or = def("x", "y").apply(var("x"), ltrue, var("y"));
		final IExpression not = def("x").apply(var("x"), lfalse, ltrue);
		final IExpression xor = def("x", "y").apply(and,
				apply(or, var("x"), var("y")),
				apply(not, apply(and, var("x"), var("y"))));
		assertNormalizes(sfalse, apply(xor, ltrue, ltrue));
		assertNormalizes(strue, apply(xor, ltrue, lfalse));
		assertNormalizes(strue, apply(xor, lfalse, ltrue));
		assertNormalizes(sfalse, apply(xor, lfalse, lfalse));
	}

	/**
	 * Left function on pairs in lambda expressions.
	 */
	@Test
	public final void leftOnPairs() {
		final IExpression pair = def("x", "y", "c").apply(var("c"), var("x"),
				var("y"));
		final IExpression ltrue = def("x", "y").var("x");
		final IExpression left = def("x").apply(var("x"), ltrue);
		final IExpression pairAB = apply(pair, con("A"), con("B"));
		final IExpression normalForm = apply(left, pairAB).normalForm();
		assertEquals(con("A"), normalForm);
	}

	/**
	 * Test type union data constructor and destructor in lambda calculus.
	 */
	@Test
	public final void union() {
		final IExpression ltrue = def("x", "y").var("x");
		final IExpression lfalse = def("x", "y").var("y");
		final IExpression pair = def("x", "y", "c").apply(var("c"), var("x"),
				var("y"));
		final IExpression right = def("x").apply(var("x"), lfalse);
		final IExpression destroyA = def("z").apply(right, var("z"));
		final IExpression constructA = def("x").apply(pair, ltrue, var("x"));
		assertEquals(con("A"), apply(destroyA, apply(constructA, con("A")))
				.normalForm());
	}

	/**
	 * Test recursion through Y combinator and eta reduced factorial.
	 */
	@Test
	public final void factorial() {
		final IExpression ltrue = def("x", "y").var("x");
		final IExpression lfalse = def("x", "y").var("y");
		final IExpression pred = Parser
				.fromString("(n|(f|(x|((n (g|(h|(h (g f))))) (u|x) (u|u)))))");
		final IExpression lif = def("c", "x", "y").apply(var("c"), var("x"),
				var("y"));
		final IExpression constantFalse = def("x", lfalse);
		final IExpression iszero = def("n").apply(var("n"), constantFalse,
				ltrue);
		final IExpression mult = def("m", "n", "f").apply(var("n"),
				apply(var("m"), var("f")));
		final IExpression fac = def("fac", "n")
				.apply(lif,
						apply(iszero, var("n")),
						Calculus.nat(1),
						apply(mult, var("n"),
								apply(var("fac"), apply(pred, var("n")))));
		final IExpression combinatorY = def("f").apply(
				def("x").apply(var("f"), apply(var("x"), var("x"))),
				def("x").apply(var("f"), apply(var("x"), var("x"))));
		final StringBuilder factorialSix = new StringBuilder("x");
		for (int index = 0; index < 720; index++) {
			factorialSix.insert(0, "(f ");
			factorialSix.append(')');
		}
		factorialSix.insert(0, "(f|(x|");
		factorialSix.append("))");
		assertNormalizes(factorialSix.toString(),
				apply(combinatorY, fac, Calculus.nat(6)));
	}

	/**
	 * Test recursion using Y combinator on eta reduced sum.
	 */
	@Test
	public final void sum() {
		final IExpression ltrue = def("x", "y").var("x");
		final IExpression lfalse = def("x", "y").var("y");
		final IExpression pred = Parser
				.fromString("(n|(f|(x|((n (g|(h|(h (g f))))) (u|x) (u|u)))))");
		final IExpression lif = def("c", "x", "y").apply(var("c"), var("x"),
				var("y"));
		final IExpression constantFalse = def("x", lfalse);
		final IExpression iszero = def("n").apply(var("n"), constantFalse,
				ltrue);
		final IExpression plus = def("m", "n", "f", "x").apply(var("m"),
				var("f"), apply(var("n"), var("f"), var("x")));
		final IExpression sum = def("sum", "n")
				.apply(lif,
						apply(iszero, var("n")),
						nat(0),
						apply(plus, var("n"),
								apply(var("sum"), apply(pred, var("n")))));
		final IExpression combinatorY = def("f").apply(
				def("x").apply(var("f"), apply(var("x"), var("x"))),
				def("x").apply(var("f"), apply(var("x"), var("x"))));
		final StringBuilder sumSix = new StringBuilder("x");
		for (int index = 0; index < 21; index++) {
			sumSix.insert(0, "(f ");
			sumSix.append(')');
		}
		sumSix.insert(0, "(f|(x|");
		sumSix.append("))");
		assertNormalizes(sumSix.toString(), apply(combinatorY, sum, nat(6)));

	}
}
