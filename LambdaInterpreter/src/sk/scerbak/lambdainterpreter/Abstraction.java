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
	 * Counter for unique IDs for variable substitution in this expression.
	 */
	private int lastVariableId = -1;

	/**
	 * @param label
	 *            for this abstraction
	 * @param expression
	 *            abstraction body
	 */
	public Abstraction(final String label, final IExpression expression) {
		variable = label;
		body = expression;
	}

	@Override
	public void accept(final IVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * @param variableLabel
	 *            string containing a variable name
	 * @return true if the variableLabel represents variable of this
	 */
	private boolean bounds(final String variableLabel) {
		return variable.equals(variableLabel);
	}

	@Override
	public boolean equals(final IExpression other) {
		boolean result = false;
		if (other instanceof Abstraction) {
			final Abstraction otherAbstraction = (Abstraction) other;
			final String otherVariable = otherAbstraction.variable;
			final IExpression otherBody;
			if (variable.equals(otherVariable)) {
				otherBody = otherAbstraction.body;
			} else {
				otherBody = otherAbstraction.body.substitute(otherVariable,
						new Variable(variable));
			}
			result = body.equals(otherBody);
		}
		return result;
	}

	@Override
	public boolean free(final String variableLabel) {
		return !bounds(variableLabel) && body.free(variableLabel);
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
	 * @param newVariableLabel
	 *            which will be tested if free in expression
	 * @param expression
	 *            where newVariableLabel will be tested if free
	 * @return true if newVariableName is free in this body and in expression
	 */
	private boolean isFreshVariableFor(final String newVariableLabel,
			final IExpression expression) {
		final boolean freeInBody = body.free(newVariableLabel);
		final boolean freeInExpression = expression.free(newVariableLabel);
		return !freeInBody && !freeInExpression;
	}

	@Override
	public boolean isReducible() {
		return body.isReducible();
	}

	/**
	 * @return unique name for a new variable
	 */
	private String nextVariableName() {
		lastVariableId++;
		return "v" + lastVariableId;
	}

	@Override
	public IExpression normalForm() {
		return new Abstraction(variable, body.normalForm());
	}

	@Override
	public IExpression oneStepBetaReduce() {
		return new Abstraction(variable, body.oneStepBetaReduce());
	}

	@Override
	public IExpression substitute(final String variableLabel,
			final IExpression expression) {
		Abstraction result = null;
		if (variable.equals(variableLabel)) {
			result = this;
		} else if (body.free(variableLabel) && expression.free(variable)) {
			final String newVariableLabel = freshVariableFor(expression);
			result = new Abstraction(newVariableLabel, body.substitute(
					variable, new Variable(newVariableLabel)).substitute(
					variableLabel, expression));
		} else {
			result = new Abstraction(variable, body.substitute(variableLabel,
					expression));
		}
		return result;
	}

	@Override
	public List<IExpression> subterm() {
		final List<IExpression> result = new LinkedList<IExpression>();
		result.add(this);
		result.addAll(body.subterm());
		return result;
	}

}
