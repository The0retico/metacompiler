package sk.scerbak.lambdainterpreter;

/**
 * @author The0retico Predefined constant or internal function.
 */
class Constant extends Symbol implements IExpression {

	/**
	 * Name of this constant.
	 */
	private final String label;

	/**
	 * @param constantLabel
	 *            uppercase string name of this lambda constant
	 */
	public Constant(final String constantLabel) {
		super();
		this.label = constantLabel;
	}

	@Override
	public IExpression substitute(final String variable,
			final IExpression expression) {
		return this;
	}

	@Override
	public IExpression oneStepBetaReduce() {
		return this;
	}

	@Override
	public IExpression normalForm() {
		return this;
	}

	@Override
	public String toString() {
		return this.label;
	}

	@Override
	public boolean free(final String variable) {
		return false;
	}

	@Override
	public boolean isReducible() {
		return false;
	}
}
