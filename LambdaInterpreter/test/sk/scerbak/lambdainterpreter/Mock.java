package sk.scerbak.lambdainterpreter;

import java.util.LinkedList;
import java.util.List;

/**
 * Library class with helper methods for tests.
 * 
 * @author The0retico
 * 
 */
final class Mock implements IExpression {

	/**
	 * @param label
	 *            name for this expression to be printed.
	 */
	Mock(final String label) {
		this.message = new StringBuilder(label);
	}

	/**
	 * Message where information about methods called on this mock objects will
	 * be stored.
	 */
	private final StringBuilder message;

	@Override
	public String toString() {
		return this.message.toString();
	}

	@Override
	public boolean free(final String variable) {
		this.message.append(".free");
		return true;
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
		// TODO Auto-generated method stub
		return null;
	}

}
