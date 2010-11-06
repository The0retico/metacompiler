package sk.scerbak.lambdainterpreter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author The0retico Lambda application interpreter class.
 */
class Application implements IExpression {

	/**
	 * Lambda expression representing function of this application.
	 */
	private final IExpression function;
	/**
	 * Lambda expression representing argument to this application.
	 */
	private final IExpression argument;

	/**
	 * @param functionLambda
	 *            expression
	 * @param argumentLambda
	 *            expression
	 */
	public Application(final IExpression functionLambda,
			final IExpression argumentLambda) {
		this.function = functionLambda;
		this.argument = argumentLambda;
	}

	@Override
	public boolean free(final String variable) {
		return function.free(variable) && argument.free(variable);
	}

	@Override
	public List<IExpression> subterm() {
		final List<IExpression> result = new LinkedList<IExpression>();
		result.add(this);
		result.addAll(function.subterm());
		result.addAll(argument.subterm());
		return result;
	}

	@Override
	public IExpression substitute(final String variable,
			final IExpression expression) {
		return new Application(function.substitute(variable, expression),
				argument.substitute(variable, expression));
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
		return "(" + this.function + " " + this.argument + ")";
	}
}
