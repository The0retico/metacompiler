package sk.scerbak.lambdainterpreter;

import static sk.scerbak.lambdainterpreter.Assertions.assertNormalizes;
import static sk.scerbak.lambdainterpreter.Assertions.assertNormalizesString;
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
public class ComplexTests {

	private final IExpression ltrue = def("x", "y").var("x");
	private final IExpression lfalse = def("x", "y").var("y");
	private final IExpression and = def("x", "y").apply(var("x"), var("y"),
			lfalse);
	private final IExpression or = def("x", "y").apply(var("x"), ltrue,
			var("y"));
	private final IExpression not = def("x").apply(var("x"), lfalse, ltrue);
	private final IExpression pair = def("x", "y", "c").apply(var("c"),
			var("x"), var("y"));
	private final IExpression left = def("x").apply(var("x"), ltrue);
	private final IExpression right = def("x").apply(var("x"), lfalse);
	private final IExpression pred = Parser
			.fromString("(n|(f|(x|((n (g|(h|(h (g f))))) (u|x) (u|u)))))");
	private final IExpression lif = def("c", "x", "y").apply(var("c"),
			var("x"), var("y"));
	private final IExpression iszero = def("n").apply(var("n"),
			def("x", lfalse), ltrue);
	private final IExpression combinatorY = def("f").apply(
			def("x").apply(var("f"), apply(var("x"), var("x"))),
			def("x").apply(var("f"), apply(var("x"), var("x"))));

	/**
	 * Addition of Church numbers.
	 */
	@Test
	public final void additionOnChurchNumbers() {
		assertNormalizesString(
				"(f|(x|(f (f (f x)))))",
				apply(def("m", "n", "f", "x").apply(var("m"), var("f"),
						apply(var("n"), var("f"), var("x"))), nat(1), nat(2)));
	}

	/**
	 * Zero and successor.
	 */
	@Test
	public final void successorForChurchNumbers() {
		final IExpression succ = def("n", "f", "x").apply(var("f"),
				apply(var("n"), var("f"), var("x")));
		final IExpression one = apply(succ, nat(0));
		assertNormalizesString("(f|(x|(f x)))", one);
		final IExpression two = apply(succ, one);
		assertNormalizesString("(f|(x|(f (f x))))", two);
		final IExpression three = apply(succ, two);
		assertNormalizesString("(f|(x|(f (f (f x)))))", three);
	}

	/**
	 * Multiplication of church numbers.
	 */
	@Test
	public final void multiplicationOfChurchNumbers() {
		final IExpression mult = def("m", "n", "f").apply(var("n"),
				apply(var("m"), var("f")));
		final IExpression two = nat(2);
		final IExpression three = nat(3);
		assertNormalizesString("(f|(x|(f (f (f (f (f (f x))))))))",
				apply(mult, two, three));
	}

	/**
	 * Exponential function on church numbers.
	 */
	@Test
	public final void exponentialOnChurchNumbers() {
		final IExpression exp = def("m", "n", "f").apply(var("n"), var("m"),
				var("f"));
		assertNormalizesString("(f|(x|(f (f (f (f x))))))",
				apply(exp, Calculus.nat(2), Calculus.nat(2)));
	}

	/**
	 * Predecessor on church numbers.
	 */
	@Test
	public final void predecessorOnChurchNubmers() {
		final IExpression three = nat(3);
		assertNormalizesString("(f|(x|(f (f x))))", apply(pred, three));
	}

	/**
	 * And function on booleans.
	 */
	@Test
	public final void andOnBooleans() {
		assertNormalizes(def("x", "y").var("y"), apply(and, ltrue, lfalse));
	}

	/**
	 * Xor function on booleans.
	 */
	@Test
	public final void xorOnBooleans() {
		final IExpression xor = def("x", "y").apply(and,
				apply(or, var("x"), var("y")),
				apply(not, apply(and, var("x"), var("y"))));
		assertNormalizes(lfalse, apply(xor, ltrue, ltrue));
		assertNormalizes(ltrue, apply(xor, ltrue, lfalse));
		assertNormalizes(ltrue, apply(xor, lfalse, ltrue));
		assertNormalizes(lfalse, apply(xor, lfalse, lfalse));
	}

	/**
	 * Left function on pairs in lambda expressions.
	 */
	@Test
	public final void leftOnPairs() {
		final IExpression pairAB = apply(pair, con("A"), con("B"));
		assertNormalizes(con("A"), apply(left, pairAB));
	}

	/**
	 * Test type union data constructor and destructor in lambda calculus.
	 */
	@Test
	public final void union() {
		final IExpression destroyA = def("z").apply(right, var("z"));
		final IExpression constructA = def("x").apply(pair, ltrue, var("x"));
		assertNormalizes(con("A"), apply(destroyA, apply(constructA, con("A"))));
	}

	/**
	 * Test recursion through Y combinator and eta reduced factorial.
	 */
	@Test
	public final void factorial() {
		final IExpression mult = def("m", "n", "f").apply(var("n"),
				apply(var("m"), var("f")));
		final IExpression fac = def("fac", "n")
				.apply(lif,
						apply(iszero, var("n")),
						Calculus.nat(1),
						apply(mult, var("n"),
								apply(var("fac"), apply(pred, var("n")))));
		final StringBuilder factorial6Result = new StringBuilder("x");
		final int factorialSix = 720;
		for (int index = 0; index < factorialSix; index++) {
			factorial6Result.insert(0, "(f ");
			factorial6Result.append(')');
		}
		factorial6Result.insert(0, "(f|(x|");
		factorial6Result.append("))");
		final IExpression six = nat(6);
		assertNormalizesString(factorial6Result.toString(),
				apply(combinatorY, fac, six));
	}

	/**
	 * Test recursion using Y combinator on eta reduced sum.
	 */
	@Test
	public final void sum() {
		final IExpression plus = def("m", "n", "f", "x").apply(var("m"),
				var("f"), apply(var("n"), var("f"), var("x")));
		final IExpression sum = def("sum", "n")
				.apply(lif,
						apply(iszero, var("n")),
						nat(0),
						apply(plus, var("n"),
								apply(var("sum"), apply(pred, var("n")))));
		final StringBuilder sumSixExpression = new StringBuilder("x");
		final int sumSix = 21;
		for (int index = 0; index < sumSix; index++) {
			sumSixExpression.insert(0, "(f ");
			sumSixExpression.append(')');
		}
		sumSixExpression.insert(0, "(f|(x|");
		sumSixExpression.append("))");
		final IExpression six = nat(6);
		assertNormalizesString(sumSixExpression.toString(),
				apply(combinatorY, sum, six));

	}
}
