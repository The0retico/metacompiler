package sk.scerbak.lambdainterpreter;

import static sk.scerbak.lambdainterpreter.Calculus.apply;
import static sk.scerbak.lambdainterpreter.Calculus.def;
import static sk.scerbak.lambdainterpreter.Calculus.var;
import static sk.scerbak.lambdainterpreter.Calculus.vars;

/**
 * Lambda expression containing integer constant.
 * 
 * @author The0retico
 */
class Natural extends Symbol implements IExpression {

	/**
	 * May be invalid, checked during evaluate method call.
	 */
	private final int value;

	/**
	 * Equivalent church numbering expression to the value.
	 */
	private final IExpression expression;

	/**
	 * @return the expression
	 */
	public IExpression getExpression() {
		return expression;
	}

	/**
	 * @param integer
	 *            any Integer
	 */
	public Natural(final Integer integer) {
		super();
		this.value = integer;
		if (value < 0) {
			throw new IllegalArgumentException();
		}
		IExpression result = var("x");
		for (int index = 0; index < value; index++) {
			result = apply(var("f"), result);
		}
		this.expression = def(vars("f", "x"), result);
	}

	@Override
	public IExpression substitute(final String variable,
			final IExpression expression) {
		return this;
	}

	@Override
	public IExpression oneStepBetaReduce() {
		return this;
	}

	@Override
	public IExpression normalForm() {
		return this;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public boolean free(final String variable) {
		return false;
	}

	@Override
	public boolean isReducible() {
		return false;
	}

}
