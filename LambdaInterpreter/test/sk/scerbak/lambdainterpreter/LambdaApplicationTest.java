package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for lambda application.
 * 
 * @author The0retico
 * 
 */
public class LambdaApplicationTest {

	/**
	 * Test fixture.
	 */
	private LambdaApplication fixture;

	/**
	 * Set up a test fixture for every test.
	 */
	@Before
	public final void setUp() {
		fixture = new LambdaApplication(FakeExpression.create("A"),
				FakeExpression.create("B"));
	}

	/**
	 * If variable is not bound in function nor argument, it is free.
	 */
	@Test
	public final void variableNotBoundInApplicationIsFree() {
		assertTrue("Variable should be free", fixture.free("z"));
		assertEquals("(A.free B.free)", fixture.toString());
	}

	/**
	 * Lambda applications itself and subterms of function and argument are
	 * their subterms.
	 */
	@Test
	public final void itselfAndFunctionSubtermsAndArgumentSubterms() {
		final List<ILambdaExpression> subterms = fixture.subterm();
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
		final ILambdaExpression substituted = fixture.substitute(
				"x", FakeExpression.create("M"));
		assertNotNull("Every application substitution should be sucessful",
				substituted);
		assertTrue("Application should be substituted for application",
				substituted instanceof LambdaApplication);
		assertEquals("(A[x:M] B[x:M])", substituted.toString());
	}
}
