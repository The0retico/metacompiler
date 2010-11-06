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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IExpression normalForm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return this.label;
	}
}
