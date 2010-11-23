package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.hasItems;
import static sk.scerbak.lambdainterpreter.Assertions.assertCalled;
import static sk.scerbak.lambdainterpreter.Assertions.assertNormalizes;
import static sk.scerbak.lambdainterpreter.Assertions.assertNotCalled;
import static sk.scerbak.lambdainterpreter.Assertions.assertNotFree;
import static sk.scerbak.lambdainterpreter.Assertions.assertSubstitutes;
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
	 * Mock object for lambda expression which serves as a body of fixture.
	 */
	private Mock mock;

	/**
	 * Variable in compound abstractions can be bound in any of them.
	 */
	@Test
	public final void boundVariableElsewhereIsNotFree() {
		assertNotFree("x", fixture);
		assertNotCalled(mock, "free");
	}

	/**
	 * Variable bound in this abstraction is not free.
	 */
	@Test
	public final void boundVariableIsNotFree() {
		assertNotFree("x", fixture);
		assertNotCalled(mock, "free");
	}

	/**
	 * Two lambda abstractions are equal if one is alpha convertible to another,
	 * which means that they are exact the same w.r.t. variable name
	 * substitution.
	 */
	@Test
	public final void equalsIfAlphaConvertible() {
		assertTrue(def("x").var("x").equals(def("x").var("x")));
		assertTrue(def("x").var("x").equals(def("y").var("y")));
		assertFalse(def("x").var("x").equals(def("x").var("y")));
	}

	/**
	 * Abstractions subterms are the abstraction itself and all subterms of its
	 * body.
	 */
	@Test
	public final void itselfAndBodySubterms() {
		final List<IExpression> subterms = fixture.subterm();
		assertNotNull("The expression itself is always its subterm", subterms);
		assertThat(subterms, hasItems(fixture, mock));
		assertCalled(mock, "subterm");
	}

	/**
	 * Normal form of an abstraction should have its body in normal form.
	 */
	@Test
	public final void normalFormHasBodyInNormalForm() {
		assertNormalizes(def("x", mock), fixture);
		assertCalled(mock, "normalForm");
	}

	/**
	 * Set up method for fixture.
	 */
	@Before
	public final void setUp() {
		mock = new Mock("B", "y");
		fixture = def("x", mock);
	}

	/**
	 * Substitution for variable bound by lambda abstraction has no effect.
	 */
	@Test
	public final void substituteAbstractionVariable() {
		assertSubstitutes(fixture, fixture, "x", new Mock("M"));
	}

	/**
	 * Substition of a variable bound in body of this lambda abstraction should
	 * substitute the body.
	 */
	@Test
	public final void substituteBoundVariable() {
		assertSubstitutes(fixture, fixture, "w", new Mock("M"));
		assertCalled(mock, "free", "[w:M]");
	}

	/**
	 * Substition of a free variable in body of this lambda abstraction.
	 */
	@Test
	public final void substituteFreeVariable() {
		final Mock substituentMock = new Mock("M", "x");
		assertSubstitutes(fixture, fixture, "y", substituentMock);
		assertCalled(mock, "free", "[x:z]", "[y:M]");
		assertCalled(substituentMock, "free");
	}

	/**
	 * Testing printing.
	 */
	@Test
	public final void toStringIsItsVariableAndBody() {
		assertEquals("(x|B)", Printer.toString(fixture));
	}
}
