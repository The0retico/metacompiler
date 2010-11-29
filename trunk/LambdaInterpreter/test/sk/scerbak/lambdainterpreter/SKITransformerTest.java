package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static sk.scerbak.lambdainterpreter.Calculus.I;
import static sk.scerbak.lambdainterpreter.Calculus.K;
import static sk.scerbak.lambdainterpreter.Calculus.S;
import static sk.scerbak.lambdainterpreter.Calculus.apply;
import static sk.scerbak.lambdainterpreter.Calculus.def;
import static sk.scerbak.lambdainterpreter.Calculus.var;
import static sk.scerbak.lambdainterpreter.SKITransformer.transform;

import org.junit.Test;

/**
 * Unit tests for the SKI transformator.
 * 
 * @author The0retico
 * 
 */
public class SKITransformerTest {

	@Test
	public final void combinatorI() {
		assertEquals(Printer.toString(I),
				Printer.toString(SKITransformer.transform(def("x").var("x"))));
	}

	@Test
	public final void combinatorK() {
		assertEquals(Printer.toString(apply(K, I)),
				Printer.toString(transform(def("y", "x").var("x"))));
	}

	@Test
	public final void combinatorS() {
		assertEquals(
				Printer.toString(apply(S, I, I)),
				Printer.toString(SKITransformer.transform(def("z").apply(
						var("z"), var("z")))));
	}

	/**
	 * Simple test.
	 */
	@Test
	public final void simple() {
		assertEquals(
				"((S (K (S I))) ((S (K K)) (I))",
				Printer.toString(SKITransformer.transform(def("x", "y").apply(
						var("y"), var("x")))));
	}
}
