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
		function = functionLambda;
		argument = argumentLambda;
	}

	@Override
	public void accept(final IVisitor visitor) {
		visitor.visit(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final IExpression obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Application)) {
			return false;
		}
		final Application other = (Application) obj;
		if (argument == null) {
			if (other.argument != null) {
				return false;
			}
		} else if (!argument.equals(other.argument)) {
			return false;
		}
		if (function == null) {
			if (other.function != null) {
				return false;
			}
		} else if (!function.equals(other.function)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean free(final String variable) {
		return function.free(variable) || argument.free(variable);
	}

	/**
	 * @return the argument
	 */
	public IExpression getArgument() {
		return argument;
	}

	/**
	 * @return the function
	 */
	public IExpression getFunction() {
		return function;
	}

	@Override
	public boolean isReducible() {
		return function instanceof Abstraction || function.isReducible()
				|| argument.isReducible();
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
	public IExpression oneStepBetaReduce() {
		IExpression result;
		if (function instanceof Abstraction) {
			final Abstraction abstraction = (Abstraction) function;
			result = abstraction.getBody().substitute(
					abstraction.getVariable(), argument);
		} else if (function.isReducible()) {
			result = new Application(function.oneStepBetaReduce(), argument);
		} else {
			result = new Application(function, argument.oneStepBetaReduce());
		}
		return result;
	}

	@Override
	public IExpression substitute(final String variable,
			final IExpression expression) {
		return new Application(function.substitute(variable, expression),
				argument.substitute(variable, expression));
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
	public String toString() {
		final StringBuilder arguments = new StringBuilder();
		IExpression left = function;
		while (left instanceof Application) {
			final Application leftApplication = (Application) left;
			arguments.insert(0, " " + leftApplication.argument);
			left = leftApplication.function;
		}
		arguments.insert(0, left);
		return "(" + arguments + " " + argument + ")";
	}
}
