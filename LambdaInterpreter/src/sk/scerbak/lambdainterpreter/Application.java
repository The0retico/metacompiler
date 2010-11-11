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
		return function.free(variable) || argument.free(variable);
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
		IExpression result;
		if (this.function instanceof Abstraction) {
			final Abstraction abstraction = (Abstraction) this.function;
			result = abstraction.getBody().substitute(
					abstraction.getVariable(), this.argument);
		} else if (this.function.isReducible()) {
			result = new Application(this.function.oneStepBetaReduce(),
					this.argument);
		} else {
			result = new Application(this.function,
					this.argument.oneStepBetaReduce());
		}
		return result;
	}

	@Override
	public IExpression normalForm() {
		IExpression result = this;
		while (result.isReducible()) {
			result = result.oneStepBetaReduce();
		}
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder arguments = new StringBuilder();
		IExpression left = this.function;
		while (left instanceof Application) {
			final Application leftApplication = (Application) left;
			arguments.insert(0, " " + leftApplication.argument);
			left = leftApplication.function;
		}
		arguments.insert(0, left);
		return "(" + arguments + " " + this.argument + ")";
	}

	@Override
	public boolean isReducible() {
		return this.function instanceof Abstraction
				|| this.function.isReducible() || this.argument.isReducible();
	}
}