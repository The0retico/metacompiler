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
	 * Fixture for example1.
	 */
	private final IExpression fixture1 = Parser
			.fromString("(x|(y|(x z)))");

	/**
	 * Simple example.
	 */
	@Test
	public final void example1() {
		assertFalse("x should be bound in " + fixture1, fixture1.free("x"));
		assertTrue("z should be free in " + fixture1, fixture1.free("z"));
		final IExpression variableY = Parser.fromString("y");
		final List<IExpression> subterms = fixture1.subterm();
		assertFalse(subterms + " should not contain y",
				subterms.contains(variableY));
	}

}
