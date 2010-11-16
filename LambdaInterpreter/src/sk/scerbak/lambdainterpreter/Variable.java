package sk.scerbak.lambdainterpreter;

/**
 * Variable in the lambda expressions.
 * 
 * @author The0retico
 * 
 */
class Variable extends Symbol implements IExpression {

	/**
	 * String label for this variable.
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
		if (!(obj instanceof Variable)) {
			return false;
		}
		Variable other = (Variable) obj;
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
	 * @param variableLabel
	 *            lowercase string name for this variable
	 */
	public Variable(final String variableLabel) {
		super();
		this.label = variableLabel;
	}

	@Override
	public IExpression substitute(final String variable,
			final IExpression expression) {
		return label.equals(variable) ? expression : this;
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
		return this.label.equals(variable);
	}

	@Override
	public boolean isReducible() {
		return false;
	}

}
