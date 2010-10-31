package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

/**
 * Unit tests for lambda application.
 * 
 * @author The0retico
 * 
 */
public class LambdaApplicationTest {

	/**
	 * Lambda variable X, used in other constants.
	 */
	private final LambdaVariable variableX = new LambdaVariable("x");

	/**
	 * Lambda abstraction for identity (aka I).
	 */
	private final LambdaAbstraction identity = new LambdaAbstraction("x",
			variableX);

	/**
	 * Lambda abstraction constantly returning variable X.
	 */
	private final LambdaAbstraction constantX = new LambdaAbstraction("y",
			variableX);

	/**
	 * ConstantX written as application of itself to identity.
	 */
	private final LambdaApplication appliedXToI = new LambdaApplication(
			identity, constantX);

	/**
	 * If variable is not bound in function nor argument, it is free.
	 */
	@Test
	public final void variableNotBoundInApplicationIsFree() {
		assertTrue("Variable should not be bound", appliedXToI.free("z"));
	}

	/**
	 * Lambda applications itself and subterms of function and argument are
	 * their subterms.
	 */
	@Test
	public final void itselfAndFunctionSubtermsAndArgumentSubterms() {
		List<ILambdaExpression> subterms = appliedXToI.subterm();
		assertNotNull(subterms);
		final int numberOfAppliedXToISubterms = 5;
		assertEquals(numberOfAppliedXToISubterms, subterms.size());
		assertEquals(appliedXToI, subterms.get(0));
		assertEquals(identity, subterms.get(1));
		assertEquals(variableX, subterms.get(2));
		assertEquals(constantX, subterms.get(3));
		assertEquals(variableX, subterms.get(4));
	}
}
