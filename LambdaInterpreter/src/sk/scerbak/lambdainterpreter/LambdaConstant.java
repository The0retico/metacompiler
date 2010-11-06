package sk.scerbak.lambdainterpreter;


/**
 * @author The0retico Predefined constant or internal function.
 */
class LambdaConstant extends LambdaSymbol implements ILambdaExpression {

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
