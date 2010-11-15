package sk.scerbak.lambdainterpreter;

import static sk.scerbak.lambdainterpreter.Assertions.assertNormalizes;
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
public class ComplexTest {

	/**
	 * Addition of Church numbers.
	 */
	@Test
	public final void additionOnChurchNumbers() {
		assertNormalizes(
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
				apply(mult, nat(2), nat(3)));
	}

	/**
	 * Exponential function on church numbers.
	 */
	@Test
	public final void exponentialOnChurchNumbers() {
		final IExpression exp = def("m", "n", "f").apply(var("n"), var("m"),
				var("f"));
		assertNormalizes("(f|(x|(f (f (f (f x))))))",
				apply(exp, nat(2), nat(2)));
	}

	/**
	 * Predecessor on church numbers.
	 */
	@Test
	public final void predecessorOnChurchNubmers() {
		final IExpression pred = Parser
				.fromString("(n|(f|(x|((n (g|(h|(h (g f))))) (u|x) (u|u)))))");
		final IExpression two = apply(pred, nat(3));
		assertNormalizes("(f|(x|(f (f x))))", two);
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
}
