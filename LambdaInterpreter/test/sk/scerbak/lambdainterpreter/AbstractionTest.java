package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for LambdaAbstraction class.
 * 
 * @author The0retico
 * 
 */
public class AbstractionTest {

	/**
	 * Fixture for this test.
	 */

	private IExpression fixture;
	/**
	 * 
	 */
	private final String abstraction = "(x|B)";

	/**
	 * Set up method for fixture.
	 */
	@Before
	public final void setUp() {
		fixture = new Abstraction("x", new Mock("B", "y"));
	}

	/**
	 * 
	 */
	@Test
	public final void toStringIsItsVariableAndBody() {
		assertEquals(abstraction, fixture.toString());
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
		final List<IExpression> subterms = fixture.subterm();
		assertNotNull("The expression itself is always its subterm", subterms);
		assertEquals("[(x|B.subterm), B.subterm]", subterms.toString());
	}

	/**
	 * Substitution for variable bound by lambda abstraction has no effect.
	 */
	@Test
	public final void substituteAbstractionVariable() {
		final IExpression substituted = fixture.substitute("x", new Mock("M"));
		assertNotNull("Abstraction substitution should be successful",
				substituted);
		assertEquals("(x|B)", substituted.toString());
	}

	/**
	 * Substition of a free variable in body of this lambda abstraction.
	 */
	@Test
	public final void substituteFreeVariable() {
		final IExpression substituted = fixture.substitute("y", new Mock("M",
				"x"));
		assertNotNull("Abstraction substitution should be successful",
				substituted);
		assertEquals("(z|B.free.free[x:z][y:M.free.free])",
				substituted.toString());
	}

	/**
	 * Substition of a variable bound in body of this lambda abstraction should
	 * substitute the body.
	 */
	@Test
	public final void substituteBoundVariable() {
		final IExpression substituted = fixture.substitute("w", new Mock("M"));
		assertNotNull("Abstraction substitution should be successful",
				substituted);
		assertEquals("(x|B.free[w:M])", substituted.toString());
	}

	/**
	 * Normal form of an abstraction should have its body in normal form.
	 */
	@Test
	public final void normalFormHasBodyInNormalForm() {
		final IExpression normalForm = fixture.normalForm();
		assertEquals("(x|B.normal)", normalForm.toString());
	}
}
