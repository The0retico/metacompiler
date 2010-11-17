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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Application)) {
			return false;
		}
		Application other = (Application) obj;
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
		} else if (this.function instanceof Constant) {
			final Constant constant = (Constant) this.function;
			if (constant.getExpression() instanceof Abstraction) {
				Abstraction abstraction = (Abstraction) constant
						.getExpression();
				result = abstraction.getBody().substitute(
						abstraction.getVariable(), this.argument);
			} else {
				throw new IllegalArgumentException();
			}
		} else if (this.function instanceof Natural) {
			final Natural naturalNumber = (Natural) this.function;
			if (naturalNumber.getExpression() instanceof Abstraction) {
				Abstraction churchNumber = (Abstraction) naturalNumber
						.getExpression();
				result = churchNumber.getBody().substitute(
						churchNumber.getVariable(), this.argument);
			} else {
				throw new IllegalArgumentException();
			}
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
				|| this.function instanceof Natural
				|| this.function.isReducible() || this.argument.isReducible();
	}
}
