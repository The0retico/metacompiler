package sk.scerbak.lambdainterpreter;

import java.util.List;

/**
 * @author The0retico Predefined constant or internal function.
 */
class LambdaConstant implements ILambdaExpression {

	/**
	 * Name of this constant.
	 */
	private final String constant;

	/**
	 * @param constantLabel
	 *            uppercase string name of this lambda constant
	 */
	public LambdaConstant(final String constantLabel) {
		this.constant = constantLabel;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILambdaExpression normalForm() {
		// TODO Auto-generated method stub
		return null;
	}

}
