package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for LambdaAbstraction class.
 * 
 * @author The0retico
 * 
 */
public class LambdaAbstractionTest extends LambdaFixtureTest {

	/**
	 * Set up method for fixture.
	 */
	@Before
	public final void setUp() {
		fixture = new LambdaAbstraction("x", new LambdaMock("B"));
	}

	/**
	 * 
	 */
	@Test
	public final void toStringIsItsVariableAndBody() {
		assertEquals("(x|B)", fixture.toString());
	}

	/**
	 * Variable bound in this abstraction is not free.
	 */
	@Test
	public final void boundVariableIsNotFree() {
		assertFalse("Variable should be bound", fixture.free("x"));
		assertEquals("(x|B)", fixture.toString());
	}

	/**
	 * Variable which is not bound in this abstraction is free.
	 */
	@Test
	public final void notBoundVariableIsFree() {
		assertTrue("Variable should not be bound", fixture.free("y"));
		assertEquals("(x|B.free)", fixture.toString());
	}

	/**
	 * Variable in compound abstractions can be bound in any of them.
	 */
	@Test
	public final void boundVariableElsewhereIsNotFree() {
		assertFalse("Variable should be bound also by subterm",
				fixture.free("x"));
		assertEquals("(x|B)", fixture.toString());
	}

	/**
	 * Abstractions subterms are the abstraction itself and all subterms of its
	 * body.
	 */
	@Test
	public final void itselfAndBodySubterms() {
		final List<ILambdaExpression> subterms = fixture.subterm();
		assertNotNull("The expression itself is always its subterm", subterms);
		assertEquals("[(x|B.subterm), B.subterm]", subterms.toString());
	}

	/**
	 * Substitution for variable bound by lambda abstraction has no effect.
	 */
	@Test
	public final void boundVariablesCannotBeSubstituted() {
		final ILambdaExpression substituted = fixture.substitute("x",
				new LambdaMock("M"));
		assertNotNull("Abstraction substitution should be successful",
				substituted);
		assertTrue("Abstraction should be substituted for abstraction",
				substituted instanceof LambdaAbstraction);
		assertEquals("(x|B)", substituted.toString());
	}

	/**
	 * Substition of a variable bound in body of this lambda abstraction should
	 * substitute the body.
	 */
	// @Test
	// public final void variableBoundInBodySubstitution() {
	// final ILambdaExpression substituted = fixtureAbstraction.substitute(
	// "y", FakeExpression.create("M"));
	// assertNotNull(substituted);
	// assertTrue(substituted instanceof LambdaAbstraction);
	// assertEquals("(x|B[y:M])", substituted.toString());
	// }

	/**
	 * Two lambda abstractions are structuraly equivalent, if their varialbes
	 * and bodies are equivalent.
	 */
	@Test
	public final void abstractionsEqualIfTheirVariableAndBodyDo() {
		assertEquals(fixture, fixture);
		assertFalse(fixture.equals(new LambdaMock("M")));
		// assertFalse(identity.equals(zero));
		// assertEquals(identity, new LambdaAbstraction("x", variableX));
	}
}
