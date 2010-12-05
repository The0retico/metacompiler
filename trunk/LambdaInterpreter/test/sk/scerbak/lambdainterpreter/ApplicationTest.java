package sk.scerbak.lambdainterpreter;

import static sk.scerbak.lambdainterpreter.Assertions.assertCalled;
import static sk.scerbak.lambdainterpreter.Assertions.assertFree;
import static sk.scerbak.lambdainterpreter.Assertions.assertSubstitutes;

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
	 * Mock object for the function lambda expression in an application.
	 */
	private Mock mockFunction;

	/**
	 * Mock object for the argument lambda expression in an application.
	 */
	private Mock mockArgument;

	/**
	 * Lambda applications itself and subterms of function and argument are
	 * their subterms.
	 */
	// TODO @Test
	// public final void itselfAndFunctionSubtermsAndArgumentSubterms() {
	// final List<IExpression> subterms = fixture.subterm();
	// assertNotNull("Application should be its own subterm", subterms);
	// assertThat(subterms, hasItems(apply(mockFunction, mockArgument),
	// mockFunction, mockArgument));
	// assertCalled(mockFunction, "subterm");
	// assertCalled(mockArgument, "subterm");
	// }

	/**
	 * Set up a test fixture for every test.
	 */
	@Before
	public final void setUp() {
		mockFunction = new Mock("A", "x");
		mockArgument = new Mock("B", "y");
		fixture = new Application(mockFunction, mockArgument);
	}

	/**
	 * Substitution to lambda applications should substitute to function and
	 * argument.
	 */
	@Test
	public final void substituteToFunctionAndArgument() {
		assertSubstitutes(fixture, fixture, "x", new Mock("M"));
		assertCalled(mockFunction, "[x:M]");
		assertCalled(mockArgument, "[x:M]");
	}

	/**
	 * If variable is not bound in function nor argument, it is free.
	 */
	@Test
	public final void variableNotBoundInApplicationIsFree() {
		assertFree("x", fixture);
		assertCalled(mockFunction, "free");
		assertFree("y", fixture);
		assertCalled(mockArgument, "free");
	}

}
