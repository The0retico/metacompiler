package sk.scerbak.lambdainterpreter;

import java.util.LinkedList;
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
		final List<ILambdaExpression> result = new LinkedList<ILambdaExpression>();
		result.add(this);
		return result;
	}

	@Override
	public ILambdaExpression substitute(final String variable,
			final ILambdaExpression expression) {
		return this;
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

	@Override
	public boolean equals(final Object obj) {
		boolean result = false;
		if (obj != null && obj instanceof LambdaConstant) {
			final LambdaConstant other = (LambdaConstant) obj;
			result = this.constant.equals(other.constant);
		}
		return result;
	}

	@Override
	public String toString() {
		return this.constant;
	}
}
