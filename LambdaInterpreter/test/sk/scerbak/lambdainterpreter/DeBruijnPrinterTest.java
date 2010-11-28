package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static sk.scerbak.lambdainterpreter.Calculus.apply;
import static sk.scerbak.lambdainterpreter.Calculus.def;
import static sk.scerbak.lambdainterpreter.Calculus.var;

import org.junit.Test;

/**
 * Unit tests for the printer which uses de Bruijn indexes instead of variable
 * names and therefore can omit variable names in the lambda abstractions.
 * 
 * @author The0retico
 * 
 */
public class DeBruijnPrinterTest {

	public static IExpression fixture;

	@Test
	public final void freeVariables() {
		fixture = apply(
				def("x", "y").apply(var("z"), var("x"),
						def("u").apply(var("u"), var("x"))),
				def("x").apply(var("w"), var("x")));
		assertEquals("((|(|3 1 (|0 2))) (|4 0))",
				DeBruijnPrinter.toString(fixture));
	}

	@Test
	public final void normal() {
		fixture = def("x", "y", "f").apply(var("f"), def("x").var("x"),
				apply(var("x"), var("y")));
		assertEquals("(|(|(|(0 (|0) (2 1)))))",
				DeBruijnPrinter.toString(fixture));
	}

	@Test
	public final void simple() {
		fixture = def("x", "y").apply(var("y"), var("x"));
		assertEquals("(|(|(0 1)))", DeBruijnPrinter.toString(fixture));
	}
}
