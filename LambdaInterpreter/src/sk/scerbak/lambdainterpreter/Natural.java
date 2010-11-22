package sk.scerbak.lambdainterpreter;

import static sk.scerbak.lambdainterpreter.Calculus.apply;
import static sk.scerbak.lambdainterpreter.Calculus.def;
import static sk.scerbak.lambdainterpreter.Calculus.var;
import static sk.scerbak.lambdainterpreter.Calculus.vars;

import java.util.LinkedList;
import java.util.List;

/**
 * Lambda expression containing integer constant.
 * 
 * @author The0retico
 */
class Natural implements IExpression {

	/**
	 * May be invalid, checked during evaluate method call.
	 */
	private final int value;

	/**
	 * Equivalent church numbering expression to the value.
	 */
	private final IExpression churchNatural;

	/**
	 * @param integer
	 *            any Integer
	 */
	public Natural(final Integer integer) {
		super();
		value = integer;
		if (value < 0) {
			throw new IllegalArgumentException();
		}
		IExpression result = var("x");
		for (int index = 0; index < value; index++) {
			result = apply(var("f"), result);
		}
		churchNatural = def(vars("f", "x"), result);
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
	public boolean alphaEquals(final IExpression obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Natural)) {
			if (!(obj instanceof Abstraction)) {
				return false;
			}
			final Abstraction other = (Abstraction) obj;
			return churchNatural.alphaEquals(other);
		}
		final Natural other = (Natural) obj;
		if (value != other.value) {
			return false;
		}
		return true;
	}

	@Override
	public boolean free(final String variable) {
		return false;
	}

	/**
	 * @return the expression
	 */
	public IExpression getExpression() {
		return churchNatural;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	@Override
	public boolean isReducible() {
		return false;
	}

	@Override
	public IExpression normalForm() {
		return this;
	}

	@Override
	public IExpression oneStepBetaReduce() {
		return this;
	}

	@Override
	public IExpression substitute(final String variable,
			final IExpression expression) {
		return this;
	}

	@Override
	public List<IExpression> subterm() {
		final List<IExpression> result = new LinkedList<IExpression>();
		result.add(this);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.valueOf(value);
	}

}
