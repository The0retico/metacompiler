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
final class Mock implements IExpression {

	/**
	 * @param label
	 *            name for this expression to be printed
	 * @param variables
	 *            which will be reported as free in this mock for lambda
	 *            expressions
	 */
	Mock(final String label, final String... variables) {
		this.message = new StringBuilder(label);
		this.freeVariables.addAll(Arrays.asList(variables));
	}

	/**
	 * Message where information about methods called on this mock objects will
	 * be stored.
	 */
	private final StringBuilder message;

	/**
	 * List of free variables.
	 */
	private final List<String> freeVariables = new LinkedList<String>();

	@Override
	public String toString() {
		return this.message.toString();
	}

	@Override
	public boolean free(final String variable) {
		this.message.append(".free");
		return this.freeVariables.contains(variable);
	}

	@Override
	public List<IExpression> subterm() {
		this.message.append(".subterm");
		final List<IExpression> result = new LinkedList<IExpression>();
		result.add(this);
		return result;
	}

	@Override
	public IExpression substitute(final String variable,
			final IExpression expression) {
		this.message.append("[" + variable + ":" + expression + "]");
		return this;
	}

	@Override
	public IExpression oneStepBetaReduce() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IExpression normalForm() {
		this.message.append(".normal");
		return this;
	}

	@Override
	public boolean isReducible() {
		return false;
	}

}
