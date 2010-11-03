package sk.scerbak.lambdainterpreter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author The0retico Lambda application interpreter class.
 */
class LambdaApplication implements ILambdaExpression {

	/**
	 * Lambda expression representing function of this application.
	 */
	private final ILambdaExpression function;
	/**
	 * Lambda expression representing argument to this application.
	 */
	private final ILambdaExpression argument;

	/**
	 * @param functionLambda
	 *            expression
	 * @param argumentLambda
	 *            expression
	 */
	public LambdaApplication(final ILambdaExpression functionLambda,
			final ILambdaExpression argumentLambda) {
		this.function = functionLambda;
		this.argument = argumentLambda;
	}

	@Override
	public boolean free(final String variable) {
		return function.free(variable) && argument.free(variable);
	}

	@Override
	public List<ILambdaExpression> subterm() {
		List<ILambdaExpression> result = new LinkedList<ILambdaExpression>();
		result.add(this);
		result.addAll(function.subterm());
		result.addAll(argument.subterm());
		return result;
	}

	@Override
	public ILambdaExpression substitute(final String variable,
			final ILambdaExpression expression) {
		return new LambdaApplication(function.substitute(variable, expression),
				argument.substitute(variable, expression));
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
		if (obj instanceof LambdaApplication) {
			final LambdaApplication other = (LambdaApplication) obj;
			if (function.equals(other.function)
					&& argument.equals(other.argument)) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public String toString() {
		return "(" + this.function + " " + this.argument + ")";
	}
}
