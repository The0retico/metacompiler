package sk.scerbak.lambdainterpreter;

import java.util.LinkedList;
import java.util.List;

/**
 * Variable in the lambda expressions.
 * 
 * @author The0retico
 * 
 */
class LambdaVariable implements ILambdaExpression {

	/**
	 * String label for this variable.
	 */
	private final String label;

	/**
	 * @param variableLabel
	 *            lowercase string name for this variable
	 */
	public LambdaVariable(final String variableLabel) {
		this.label = variableLabel;
	}

	@Override
	public boolean free(final String variable) {
		return true;
	}

	@Override
	public List<ILambdaExpression> subterm() {
		List<ILambdaExpression> result = new LinkedList<ILambdaExpression>();
		result.add(this);
		return result;
	}

	@Override
	public ILambdaExpression substitute(final String variable,
			final ILambdaExpression expression) {
		return label.equals(variable) ? expression : this;
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
		if (obj != null && obj instanceof LambdaVariable) {
			final LambdaVariable other = (LambdaVariable) obj;
			result = this.label.equals(other.label);
		}
		return result;
	}

	@Override
	public String toString() {
		return this.label;
	}
}
