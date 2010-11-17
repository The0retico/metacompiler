package sk.scerbak.lambdainterpreter;

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
public class ComplexTests {

	/**
	 * Addition of Church numbers.
	 */
	@Test
	public final void additionOnChurchNumbers() {
		assertNormalizes(nat(3), apply(con("PLUS"), nat(1), nat(2)));
	}

	/**
	 * Zero and successor.
	 */
	@Test
	public final void successorForChurchNumbers() {
		final IExpression one = apply(con("SUCC"), nat(0));
		assertNormalizes(nat(1), one);
		final IExpression two = apply(con("SUCC"), one);
		assertNormalizes(nat(2), two);
		final IExpression three = apply(con("SUCC"), two);
		assertNormalizes(nat(3), three);
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
	 * Exponential function on church numbers.
	 */
	@Test
	public final void exponentialOnChurchNumbers() {
		final IExpression exp = def("m", "n", "f").apply(var("n"), var("m"),
				var("f"));
		assertNormalizes(nat(4), apply(exp, nat(2), nat(2)));
	}

	/**
	 * Predecessor on church numbers.
	 */
	@Test
	public final void predecessorOnChurchNubmers() {
		final IExpression three = nat(3);
		assertNormalizes(nat(2), apply(con("PRED"), three));
	}

	/**
	 * And function on booleans.
	 */
	@Test
	public final void andOnBooleans() {
		assertNormalizes(con("FALSE"), // def("x", "y").var("y"),
				apply(con("AND"), con("TRUE"), con("FALSE")));
	}

	/**
	 * Xor function on booleans.
	 */
	@Test
	public final void xorOnBooleans() {
		final IExpression xor = def("x", "y").apply(con("AND"),
				apply(con("OR"), var("x"), var("y")),
				apply(con("NOT"), apply(con("AND"), var("x"), var("y"))));
		assertNormalizes(con("FALSE"), apply(xor, con("TRUE"), con("TRUE")));
		assertNormalizes(con("TRUE"), apply(xor, con("TRUE"), con("FALSE")));
		assertNormalizes(con("TRUE"), apply(xor, con("FALSE"), con("TRUE")));
		assertNormalizes(con("FALSE"), apply(xor, con("FALSE"), con("FALSE")));
	}

	/**
	 * Left function on pairs in lambda expressions.
	 */
	@Test
	public final void leftOnPairs() {
		final IExpression pairAB = apply(con("PAIR"), con("A"), con("B"));
		assertNormalizes(con("A"), apply(con("LEFT"), pairAB));
	}

	/**
	 * Test type union data constructor and destructor in lambda calculus.
	 */
	@Test
	public final void union() {
		final IExpression destroyA = def("z").apply(con("RIGHT"), var("z"));
		final IExpression constructA = def("x").apply(con("PAIR"), con("TRUE"),
				var("x"));
		assertNormalizes(con("A"), apply(destroyA, apply(constructA, con("A"))));
	}

	/**
	 * Test recursion through Y combinator and eta reduced factorial.
	 */
	@Test
	public final void factorial() {
		final IExpression fac = def("fac", "n").apply(
				con("IF"),
				apply(con("ZERO?"), var("n")),
				Calculus.nat(1),
				apply(con("MULT"), var("n"),
						apply(var("fac"), apply(con("PRED"), var("n")))));
		final int factorialSix = 720;
		final IExpression six = nat(6);
		assertNormalizes(nat(factorialSix), apply(con("Y"), fac, six));
	}

	/**
	 * Test recursion using Y combinator on eta reduced sum.
	 */
	@Test
	public final void sum() {
		final IExpression plus = def("m", "n", "f", "x").apply(var("m"),
				var("f"), apply(var("n"), var("f"), var("x")));
		final IExpression sum = def("sum", "n").apply(
				con("IF"),
				apply(con("ZERO?"), var("n")),
				nat(0),
				apply(plus, var("n"),
						apply(var("sum"), apply(con("PRED"), var("n")))));
		final int sumSix = 21;
		final IExpression six = nat(6);
		assertNormalizes(nat(sumSix), apply(con("Y"), sum, six));

	}
}
