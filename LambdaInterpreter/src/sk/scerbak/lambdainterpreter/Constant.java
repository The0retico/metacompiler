package sk.scerbak.lambdainterpreter;

/**
 * @author The0retico Predefined constant or internal function.
 */
class Constant extends Symbol implements IExpression {

	/**
	 * Name of this constant.
	 */
	private final String label;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Constant)) {
			return false;
		}
		Constant other = (Constant) obj;
		if (label == null) {
			if (other.label != null) {
				return false;
			}
		} else if (!label.equals(other.label)) {
			return false;
		}
		return true;
	}

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
