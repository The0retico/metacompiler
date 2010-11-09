package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static sk.scerbak.lambdainterpreter.LambdaAssert.assertFree;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for lambda application.
 * 
 * @author The0retico
 * 
 */
public class ApplicationTest {

	/**
	 * Fixture for this test.
	 */
	private IExpression fixture;

	/**
	 * Set up a test fixture for every test.
	 */
	@Before
	public final void setUp() {
		fixture = new Application(new Mock("A", "x"), new Mock("B", "y"));
	}

	/**
	 * 
	 */
	@Test
	public final void toStringIsFunctionAndArgument() {
		assertEquals("(A B)", fixture.toString());
	}

	/**
	 * If variable is not bound in function nor argument, it is free.
	 */
	@Test
	public final void variableNotBoundInApplicationIsFree() {
		assertFree("x", fixture);
		assertEquals("(A.free B)", fixture.toString());
		assertFree("y", fixture);
		assertEquals("(A.free.free B.free)", fixture.toString());
	}

	/**
	 * Lambda applications itself and subterms of function and argument are
	 * their subterms.
	 */
	@Test
	public final void itselfAndFunctionSubtermsAndArgumentSubterms() {
		final List<IExpression> subterms = fixture.subterm();
		assertNotNull("Application should be its own subterm", subterms);
		assertEquals("[(A.subterm B.subterm), A.subterm, B.subterm]",
				subterms.toString());
	}

	/**
	 * Substitution to lambda applications should substitute to function and
	 * argument.
	 */
	@Test
	public final void substituteToFunctionAndArgument() {
		final IExpression substituted = fixture.substitute("x", new Mock("M"));
		assertNotNull("Every application substitution should be sucessful",
				substituted);
		assertEquals("(A[x:M] B[x:M])", substituted.toString());
	}

}
