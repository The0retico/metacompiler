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

	private static final IExpression sum = def("sum", "n").apply(IF,
			apply(ISZERO, var("n")), nat(0),
			apply(PLUS, var("n"), apply(var("sum"), apply(PRED, var("n")))));;

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
		assertNormalizes(FALSE, apply(AND, TRUE, FALSE));
	}

	/**
	 * Exponential function on church numbers.
	 */
	@Test
	public final void exponentialOnChurchNumbers() {
		final IExpression exp = def("m", "n", "f").apply("n", "m", "f");
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
				apply("m", "f"));
		assertNormalizes(nat(6), apply(mult, nat(2), nat(3)));
	}

	@Test
	public final void nepar() {
		final IExpression nepar = def("nepar", "n").apply(
				IF,
				apply(ISZERO, apply(PRED, var("n"))),
				nat(1),
				apply(PLUS, apply(PRED, apply(MULT, var("n"), nat(2))),
						apply(var("nepar"), apply(PRED, var("n")))));
		final int neparFour = 16;
		assertNormalizes(nat(neparFour), apply(Y, nepar, nat(4)));
	}

	@Test
	public final void par() {
		final IExpression par = def("par", "n").apply(
				IF,
				apply(ISZERO, var("n")),
				nat(0),
				apply(PLUS, apply(MULT, var("n"), nat(2)),
						apply(var("par"), apply(PRED, var("n")))));
		final int parThree = 12;
		assertNormalizes(nat(parThree), apply(Y, par, nat(3)));
	}

	/**
	 * Predecessor on church numbers.
	 */
	@Test
	public final void predecessorOnChurchNubmers() {
		assertNormalizes(nat(2), apply(PRED, nat(3)));
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
		def("m", "n", "f", "x").apply(var("m"), var("f"), apply("n", "f", "x"));
		final int sumSix = 21;
		assertNormalizes(nat(sumSix), apply(Y, sum, nat(6)));

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
