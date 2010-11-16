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
	 * @return the body
	 */
	public IExpression getBody() {
		return body;
	}

	/**
	 * @return the variable
	 */
	public String getVariable() {
		return variable;
	}

	/**
	 * Label for variable of this lambda abstraction.
	 */
	private final String variable;

	/**
	 * @param label
	 *            for this abstraction
	 * @param expression
	 *            abstraction body
	 */
	public Abstraction(final String label, final IExpression expression) {
		this.variable = label;
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
			final String newVariableLabel = freshVariableFor(expression);
			result = new Abstraction(newVariableLabel, this.body.substitute(
					this.variable, new Variable(newVariableLabel)).substitute(
					variableLabel, expression));
		} else {
			result = new Abstraction(this.variable, this.body.substitute(
					variableLabel, expression));
		}
		return result;
	}

	/**
	 * @param expression
	 *            where variable should be free
	 * @return new variable name, which is free in this abstractions body and in
	 *         expression
	 */
	private String freshVariableFor(final IExpression expression) {
		String newVariableLabel = "z";
		while (!isFreshVariableFor(newVariableLabel, expression)) {
			newVariableLabel = nextVariableName();
		}
		return newVariableLabel;
	}

	/**
	 * Counter for unique IDs for variable substitution in this expression.
	 */
	private int lastVariableId = -1;

	/**
	 * @return unique name for a new variable
	 */
	private String nextVariableName() {
		lastVariableId++;
		return "v" + lastVariableId;
	}

	/**
	 * @param newVariableLabel
	 *            which will be tested if free in expression
	 * @param expression
	 *            where newVariableLabel will be tested if free
	 * @return true if newVariableName is free in this body and in expression
	 */
	private boolean isFreshVariableFor(final String newVariableLabel,
			final IExpression expression) {
		final boolean freeInBody = this.body.free(newVariableLabel);
		final boolean freeInExpression = expression.free(newVariableLabel);
		return !freeInBody && !freeInExpression;
	}

	@Override
	public IExpression oneStepBetaReduce() {
		return new Abstraction(this.variable, this.body.oneStepBetaReduce());
	}

	@Override
	public IExpression normalForm() {
		return new Abstraction(this.variable, this.body.normalForm());
	}

	@Override
	public String toString() {
		return "(" + this.variable + "|" + this.body.toString() + ")";
	}

	@Override
	public boolean isReducible() {
		return this.body.isReducible();
	}

	@Override
	public boolean equals(final Object other) {
		boolean result = false;
		if (other instanceof Abstraction) {
			Abstraction otherAbstraction = (Abstraction) other;
			final String otherVariable = otherAbstraction.variable;
			final IExpression otherBody;
			if (this.variable.equals(otherVariable)) {
				otherBody = otherAbstraction.body;
			} else {
				otherBody = otherAbstraction.body.substitute(otherVariable,
						new Variable(this.variable));
			}
			result = this.body.equals(otherBody);
		}
		return result;
	}
}
