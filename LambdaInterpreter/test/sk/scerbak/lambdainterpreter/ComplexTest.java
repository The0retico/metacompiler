package sk.scerbak.lambdainterpreter;

import static sk.scerbak.lambdainterpreter.Assertions.assertNormalizes;

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
				Parser.fromString("(((m|(n|(f|(x|((m f) ((n f) x)))))) (f|(x|(f x)))) (f|(x|(f (f x)))))"));
	}

	/**
	 * Zero and successor.
	 */
	@Test
	public final void successorForChurchNumbers() {
		final IExpression zero = Parser.fromString("(f|(x|x))");
		final IExpression succ = Parser.fromString("(n|(f|(x|(f ((n f) x)))))");

		final IExpression one = new Application(succ, zero);
		assertNormalizes("(f|(x|(f x)))", one);
		final IExpression two = new Application(succ, one);
		assertNormalizes("(f|(x|(f (f x))))", two);
		final IExpression three = new Application(succ, two);
		assertNormalizes("(f|(x|(f (f (f x)))))", three);

	}

	/**
	 * Multiplication of church numbers.
	 */
	@Test
	public final void multiplicationOfChurchNumbers() {
		final IExpression two = Parser.fromString("(f|(x|(f (f x))))");
		final IExpression three = Parser.fromString("(f|(x|(f (f (f x)))))");
		final IExpression mult = Parser.fromString("(m|(n|(f|(n (m f)))))");
		final IExpression six = new Application(new Application(mult, two),
				three);
		assertNormalizes("(f|(x|(f (f (f (f (f (f x))))))))", six);
	}

	/**
	 * Exponential function on church numbers.
	 */
	@Test
	public final void exponentialOnChurchNumbers() {
		final IExpression two = Parser.fromString("(f|(x|(f (f x))))");
		final IExpression exp = Parser.fromString("(m|(n|(f|((n m) f))))");
		final IExpression four = new Application(new Application(exp, two), two);
		assertNormalizes("(f|(x|(f (f (f (f x))))))", four);
	}

}
