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
		if (!(obj instanceof Natural)) {
			return false;
		}
		Natural other = (Natural) obj;
		if (value != other.value) {
			return false;
		}
		return true;
	}

	/**
	 * Equivalent church numbering expression to the value.
	 */
	private final IExpression churchNatural;

	/**
	 * @return the expression
	 */
	public IExpression getExpression() {
		return churchNatural;
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
		this.churchNatural = def(vars("f", "x"), result);
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
