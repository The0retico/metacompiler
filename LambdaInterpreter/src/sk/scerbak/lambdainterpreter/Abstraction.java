package sk.scerbak.lambdainterpreter;

import java.util.LinkedList;
import java.util.List;

/**
 * Lambda abstraction interpreter class.
 * 
 * @author The0retico
 */
class Abstraction implements IExpression {

	/**
	 * Body of this lambda abstraction, which is any lambda expression.
	 */
	private final IExpression body;
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
	public Abstraction(final String variableLabel,
			final IExpression expression) {
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
	public List<IExpression> subterm() {
		final List<IExpression> result = new LinkedList<IExpression>();
		result.add(this);
		result.addAll(body.subterm());
		return result;
	}

	@Override
	public IExpression substitute(final String variableLabel,
			final IExpression expression) {
		Abstraction result = null;
		if (this.variable.equals(variableLabel)) {
			result = this;
		} else if (this.body.free(variableLabel) && expression.free(variable)) {
			result = new Abstraction("z", this.body.substitute(
					this.variable, new Variable("z")).substitute(
					variableLabel, expression));
		} else {
			result = new Abstraction(this.variable, this.body.substitute(
					variableLabel, expression));
		}
		return result;
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
		return "(" + this.variable + "|" + this.body.toString() + ")";
	}
}
