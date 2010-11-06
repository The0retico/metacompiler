package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

/**
 * Proposed tests for functionality.
 * 
 * @author The0retico
 * 
 */
public class AcceptanceTests {

	/**
	 * Concrete syntax to ease writing of tests.
	 */
	private final ILambdaSyntax syntax = new LambdaParser();
	/**
	 * Fixture for example1.
	 */
	private final ILambdaExpression fixture1 = syntax
			.fromString("(x|(y|(x z)))");

	/**
	 * Simple example.
	 */
	@Test
	public final void example1() {
		assertFalse("x should be bound in " + fixture1, fixture1.free("x"));
		assertTrue("z should be free in " + fixture1, fixture1.free("z"));
		final ILambdaExpression variableY = syntax.fromString("y");
		final List<ILambdaExpression> subterms = fixture1.subterm();
		assertFalse(subterms + " should not contain y",
				subterms.contains(variableY));
	}

}
