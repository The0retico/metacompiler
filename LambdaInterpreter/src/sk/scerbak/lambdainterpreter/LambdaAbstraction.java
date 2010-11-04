package sk.scerbak.lambdainterpreter;

import java.util.LinkedList;
import java.util.List;

/**
 * Lambda abstraction interpreter class.
 * 
 * @author The0retico
 */
class LambdaAbstraction implements ILambdaExpression {

	/**
	 * Body of this lambda abstraction, which is any lambda expression.
	 */
	private final ILambdaExpression body;
	/**
	 * Label for variable of this lambda abstraction.
	 */
	private final String variable;

	/**
	 * @param variableLabel
	 *            for this abstraction
	 * @param expression
	 *            abstraction body
	 */
	public LambdaAbstraction(final String variableLabel,
			final ILambdaExpression expression) {
		this.variable = variableLabel;
		this.body = expression;
	}

	@Override
	public boolean free(final String variableLabel) {
		return !bounds(variableLabel) && body.free(variableLabel);
	}

	/**
	 * @param variableLabel
	 *            string containing a variable name
	 * @return true if the variableLabel represents variable of this
	 */
	private boolean bounds(final String variableLabel) {
		return this.variable.equals(variableLabel);
	}

	@Override
	public List<ILambdaExpression> subterm() {
		final List<ILambdaExpression> result = new LinkedList<ILambdaExpression>();
		result.add(this);
		result.addAll(body.subterm());
		return result;
	}

	@Override
	public ILambdaExpression substitute(final String variableLabel,
			final ILambdaExpression expression) {
		LambdaAbstraction result = null;
		if (this.variable.equals(variableLabel)) {
			result = this;
		} else if (this.body.free(variableLabel) && expression.free(variable)) {
			result = new LambdaAbstraction("z", this.body.substitute(
					this.variable, new LambdaVariable("z")).substitute(
					variableLabel, expression));
		} else {
			result = new LambdaAbstraction(this.variable, this.body.substitute(
					variableLabel, expression));
		}
		return result;
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
		if (obj instanceof LambdaAbstraction) {
			final LambdaAbstraction other = (LambdaAbstraction) obj;
			final boolean areVariablesEqual = this.variable
					.equals(other.variable);
			boolean areBodiesEqual = this.body.equals(other.body);
			if (!areVariablesEqual) {
				areBodiesEqual = this.body.equals(other.body.substitute(
						other.variable, new LambdaVariable(this.variable)));
			}
			result = areBodiesEqual;
		}
		return result;
	}

	@Override
	public String toString() {
		return "(" + this.variable + "|" + this.body.toString() + ")";
	}
}
