package sk.scerbak.lambdainterpreter;

import static sk.scerbak.lambdainterpreter.Assertions.assertNormalizes;
import static sk.scerbak.lambdainterpreter.Calcuclus.apply;
import static sk.scerbak.lambdainterpreter.Calcuclus.def;
import static sk.scerbak.lambdainterpreter.Calcuclus.var;
import static sk.scerbak.lambdainterpreter.Calcuclus.vars;

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
				Parser.fromString("(((m|(n|(f|(x|(m f (n f x)))))) (f|(x|(f x)))) (f|(x|(f (f x)))))"));
	}

	/**
	 * Zero and successor.
	 */
	@Test
	public final void successorForChurchNumbers() {
		final IExpression zero = Parser.fromString("(f|(x|x))");
		final IExpression succ = Parser.fromString("(n|(f|(x|(f ((n f) x)))))");

		final IExpression one = apply(succ, zero);
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
		final IExpression two = Parser.fromString("(f|(x|(f (f x))))");
		final IExpression three = Parser.fromString("(f|(x|(f (f (f x)))))");
		final IExpression mult = def(vars("m", "n", "f"),
				apply(var("n"), apply(var("m"), var("f"))));
		final IExpression six = apply(mult, two, three);
		assertNormalizes("(f|(x|(f (f (f (f (f (f x))))))))", six);
	}

	/**
	 * Exponential function on church numbers.
	 */
	@Test
	public final void exponentialOnChurchNumbers() {
		final IExpression two = Parser.fromString("(f|(x|(f (f x))))");
		final IExpression exp = Parser.fromString("(m|(n|(f|((n m) f))))");
		final IExpression four = apply(exp, two, two);
		assertNormalizes("(f|(x|(f (f (f (f x))))))", four);
	}

	/**
	 * Predecessor on church numbers.
	 */
	@Test
	public final void predecessorOnChurchNubmers() {
		final IExpression pred = Parser
				.fromString("(n|(f|(x|((n (g|(h|(h (g f))))) (u|x) (u|u)))))");
		final IExpression three = Parser.fromString("(f|(x|(f (f (f x)))))");
		final IExpression two = apply(pred, three);
		assertNormalizes("(f|(x|(f (f x))))", two);
	}

	/**
	 * And function on booleans.
	 */
	@Test
	public final void andOnBooleans() {
		final IExpression ltrue = Parser.fromString("(x|(y|x))");
		final IExpression lfalse = Parser.fromString("(x|(y|y))");
		final IExpression and = Parser.fromString("(x|(y|((x y) (x|(y|y)))))");
		final IExpression andTrueFalse = apply(and, ltrue, lfalse);
		assertNormalizes("(x|(y|y))", andTrueFalse);
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
		final String and = "(x|(y|((x y) " + sfalse + ")))";
		final String or = "(x|(y|((x " + strue + ") y)))";
		final String not = "(x|((x " + sfalse + ") " + strue + "))";
		final IExpression xor = Parser.fromString("(x|(y|((" + and + " ((" + or
				+ " x) y)) (" + not + " ((" + and + " x) y)))))");
		assertNormalizes(sfalse, apply(xor, ltrue, ltrue));
		assertNormalizes(strue, apply(xor, ltrue, lfalse));
		assertNormalizes(strue, apply(xor, lfalse, ltrue));
		assertNormalizes(sfalse, apply(xor, lfalse, lfalse));
	}
}
