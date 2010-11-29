package sk.scerbak.lambdainterpreter;

import static sk.scerbak.lambdainterpreter.Assertions.assertNormalizes;
import static sk.scerbak.lambdainterpreter.Calculus.AND;
import static sk.scerbak.lambdainterpreter.Calculus.FALSE;
import static sk.scerbak.lambdainterpreter.Calculus.IF;
import static sk.scerbak.lambdainterpreter.Calculus.ISZERO;
import static sk.scerbak.lambdainterpreter.Calculus.LEFT;
import static sk.scerbak.lambdainterpreter.Calculus.MULT;
import static sk.scerbak.lambdainterpreter.Calculus.NOT;
import static sk.scerbak.lambdainterpreter.Calculus.OR;
import static sk.scerbak.lambdainterpreter.Calculus.PAIR;
import static sk.scerbak.lambdainterpreter.Calculus.PLUS;
import static sk.scerbak.lambdainterpreter.Calculus.PRED;
import static sk.scerbak.lambdainterpreter.Calculus.RIGHT;
import static sk.scerbak.lambdainterpreter.Calculus.SUCC;
import static sk.scerbak.lambdainterpreter.Calculus.TRUE;
import static sk.scerbak.lambdainterpreter.Calculus.Y;
import static sk.scerbak.lambdainterpreter.Calculus.apply;
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

	/**
	 * Addition of Church numbers.
	 */
	@Test
	public final void additionOnChurchNumbers() {
		assertNormalizes(nat(3), apply(PLUS, nat(1), nat(2)));
	}

	/**
	 * And function on booleans.
	 */
	@Test
	public final void andOnBooleans() {
		assertNormalizes(FALSE, // def("x", "y").var("y"),
				apply(AND, TRUE, FALSE));
	}

	/**
	 * Exponential function on church numbers.
	 */
	@Test
	public final void exponentialOnChurchNumbers() {
		final IExpression exp = def("m", "n", "f").apply(var("n"), var("m"),
				var("f"));
		assertNormalizes(nat(4), apply(exp, nat(2), nat(2)));
	}

	/**
	 * Test recursion through Y combinator and eta reduced factorial.
	 */
	@Test
	public final void factorial() {
		final IExpression fac = def("fac", "n")
				.apply(IF,
						apply(ISZERO, var("n")),
						nat(1),
						apply(MULT, var("n"),
								apply(var("fac"), apply(PRED, var("n")))));
		final int factorialSix = 720;
		final IExpression six = nat(6);
		assertNormalizes(nat(factorialSix), apply(Y, fac, six));
	}

	/**
	 * Left function on pairs in lambda expressions.
	 */
	@Test
	public final void leftOnPairs() {
		final IExpression pairAB = apply(PAIR, var("a"), var("b"));
		assertNormalizes(var("a"), apply(LEFT, pairAB));
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
		assertNormalizes(nat(6), apply(mult, two, three));
	}

	/**
	 * Predecessor on church numbers.
	 */
	@Test
	public final void predecessorOnChurchNubmers() {
		final IExpression three = nat(3);
		assertNormalizes(nat(2), apply(PRED, three));
	}

	/**
	 * Zero and successor.
	 */
	@Test
	public final void successorForChurchNumbers() {
		final IExpression one = apply(SUCC, nat(0));
		assertNormalizes(nat(1), one);
		final IExpression two = apply(SUCC, one);
		assertNormalizes(nat(2), two);
		final IExpression three = apply(SUCC, two);
		assertNormalizes(nat(3), three);
	}

	/**
	 * Test recursion using Y combinator on eta reduced sum.
	 */
	@Test
	public final void sum() {
		final IExpression plus = def("m", "n", "f", "x").apply(var("m"),
				var("f"), apply(var("n"), var("f"), var("x")));
		final IExpression sum = def("sum", "n")
				.apply(IF,
						apply(ISZERO, var("n")),
						nat(0),
						apply(plus, var("n"),
								apply(var("sum"), apply(PRED, var("n")))));
		final int sumSix = 21;
		final IExpression six = nat(6);
		assertNormalizes(nat(sumSix), apply(Y, sum, six));

	}

	/**
	 * Test type union data constructor and destructor in lambda calculus.
	 */
	@Test
	public final void union() {
		final IExpression destroyA = def("z").apply(RIGHT, var("z"));
		final IExpression constructA = def("x").apply(PAIR, TRUE, var("x"));
		assertNormalizes(var("a"), apply(destroyA, apply(constructA, var("a"))));
	}

	/**
	 * Xor function on booleans.
	 */
	@Test
	public final void xorOnBooleans() {
		final IExpression xor = def("x", "y").apply(AND,
				apply(OR, var("x"), var("y")),
				apply(NOT, apply(AND, var("x"), var("y"))));
		assertNormalizes(FALSE, apply(xor, TRUE, TRUE));
		assertNormalizes(TRUE, apply(xor, TRUE, FALSE));
		assertNormalizes(TRUE, apply(xor, FALSE, TRUE));
		assertNormalizes(FALSE, apply(xor, FALSE, FALSE));
	}
}
