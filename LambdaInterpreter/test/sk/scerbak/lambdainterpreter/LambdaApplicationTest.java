package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertTrue;

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
}
