package sk.scerbak.lambdainterpreter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Mock object class for lambda expressions.
 * 
 * @author The0retico
 * 
 */
public final class Mock implements IExpression {

	/**
	 * List of free variables.
	 */
	private final List<String> freeVariables = new LinkedList<String>();

	/**
	 * List of methods which have been called on this mock object.
	 */
	private final List<String> methodCalls;

	/**
	 * Label for this mock lambda expression.
	 */
	private final String label;

	/**
	 * @param expressionLabel
	 *            name for this expression to be printed
	 * @param variables
	 *            which will be reported as free in this mock for lambda
	 *            expressions
	 */
	Mock(final String expressionLabel, final String... variables) {
		label = expressionLabel;
		freeVariables.addAll(Arrays.asList(variables));
		methodCalls = new LinkedList<String>();
	}

	@Override
	public void accept(final IVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public boolean equals(final IExpression other) {
		return this == other;
	}

	@Override
	public boolean free(final String variable) {
		methodCalls.add("free");
		return freeVariables.contains(variable);
	}

	/**
	 * @return list of method calls on this mock object
	 */
	public List<String> getMethodCalls() {
		return methodCalls;
	}

	@Override
	public boolean isReducible() {
		return false;
	}

	@Override
	public IExpression normalForm() {
		methodCalls.add("normalForm");
		return this;
	}

	@Override
	public IExpression oneStepBetaReduce() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IExpression substitute(final String variable,
			final IExpression expression) {
		methodCalls.add("[" + variable + ":" + Printer.toString(expression)
				+ "]");
		return this;
	}

	@Override
	public List<IExpression> subterm() {
		methodCalls.add("subterm");
		final List<IExpression> result = new LinkedList<IExpression>();
		result.add(this);
		return result;
	}

	@Override
	public String toString() {
		return label;
	}

}
