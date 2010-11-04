package sk.scerbak.lambdainterpreter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author The0retico Lambda expression containing integer constant
 */
class LambdaInteger implements ILambdaExpression {

	/**
	 * May be invalid, checked during evaluate method call.
	 */
	private final int value;

	/**
	 * @param number
	 *            any Integer
	 */
	public LambdaInteger(final Integer number) {
		this.value = number;
		if (value < 0) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public boolean free(final String variable) {
		return true;
	}

	@Override
	public List<ILambdaExpression> subterm() {
		final List<ILambdaExpression> result = new LinkedList<ILambdaExpression>();
		result.add(this);
		return result;
	}

	@Override
	public ILambdaExpression substitute(final String variable,
			final ILambdaExpression expression) {
		return this;
	}

	@Override
	public ILambdaExpression oneStepBetaReduce() {
		return this;
	}

	@Override
	public ILambdaExpression normalForm() {
		return this;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public boolean equals(final Object obj) {
		boolean result = false;
		if (obj != null && obj instanceof LambdaInteger) {
			final LambdaInteger other = (LambdaInteger) obj;
			result = this.value == other.value;
		}
		return result;
	}
}
