package sk.scerbak.lambdainterpreter;

import java.util.LinkedList;
import java.util.List;

/**
 * Library class with helper methods for tests.
 * 
 * @author The0retico
 * 
 */
final class LambdaMock implements ILambdaExpression {

	LambdaMock(final String label) {
		this.message = new StringBuilder(label);
	}

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
	public List<ILambdaExpression> subterm() {
		this.message.append(".subterm");
		final List<ILambdaExpression> result = new LinkedList<ILambdaExpression>();
		result.add(this);
		return result;
	}

	@Override
	public ILambdaExpression substitute(final String variable,
			final ILambdaExpression expression) {
		this.message.append("[" + variable + ":" + expression + "]");
		return this;
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

}
