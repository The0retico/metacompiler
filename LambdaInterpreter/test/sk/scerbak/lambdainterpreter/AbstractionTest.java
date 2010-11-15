package sk.scerbak.lambdainterpreter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static sk.scerbak.lambdainterpreter.Assertions.assertNormalizes;
import static sk.scerbak.lambdainterpreter.Assertions.assertNotFree;
import static sk.scerbak.lambdainterpreter.Calculus.def;

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
		fixture = def("x", new Mock("B", "y"));
	}

	/**
	 * Testing printing.
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
		assertNotFree("x", fixture);
		assertEquals("(x|B)", fixture.toString());
	}

	/**
	 * Variable in compound abstractions can be bound in any of them.
	 */
	@Test
	public final void boundVariableElsewhereIsNotFree() {
		assertNotFree("x", fixture);
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
		assertNormalizes("(x|B.normal)", fixture);
	}

	/**
	 * Two lambda abstractions are equal if one is alpha convertible to another,
	 * which means that they are exact the same w.r.t. variable name
	 * substitution.
	 */
	@Test
	public final void equalsIfAlphaConvertible() {
		assertThat(def("x").var("x"), is(def("x").var("x")));
		assertThat(def("x").var("x"), is(def("y").var("y")));
		assertThat(def("x").var("x"), is(not(def("x").var("y"))));
	}
}
