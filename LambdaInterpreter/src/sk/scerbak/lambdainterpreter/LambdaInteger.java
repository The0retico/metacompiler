package sk.scerbak.lambdainterpreter;

import java.util.List;

/**
 * @author The0retico Lambda expression containing integer constant
 */
class LambdaInteger implements ILambdaExpression {

	/**
	 * May be invalid, checked during evaluate method call.
	 */
	private final int value;

	/**
	 * @param number
	 *            any Integer
	 */
	public LambdaInteger(final Integer number) {
		this.value = number;
	}

	@Override
	public boolean free(final String variable) {
		return true;
	}

	@Override
	public List<ILambdaExpression> subterm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILambdaExpression substitute(final String variable,
			final ILambdaExpression expression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILambdaExpression oneStepBetaReduce() {
		if (value < 0) {
			throw new IllegalArgumentException();
		}
		return this;
	}

	@Override
	public ILambdaExpression normalForm() {
		// TODO Auto-generated method stub
		return null;
	}

}
