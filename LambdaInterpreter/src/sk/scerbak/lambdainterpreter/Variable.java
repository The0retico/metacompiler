package sk.scerbak.lambdainterpreter;

import java.util.LinkedList;
import java.util.List;

/**
 * Variable in the lambda expressions.
 * 
 * @author The0retico
 * 
 */
class Variable implements IExpression {

	/**
	 * String label for this variable.
	 */
	private final String label;

	/**
	 * @param variableLabel
	 *            lowercase string name for this variable
	 */
	public Variable(final String variableLabel) {
		super();
		label = variableLabel;
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
		if (!(obj instanceof Variable)) {
			return false;
		}
		final Variable other = (Variable) obj;
		if (label == null) {
			if (other.label != null) {
				return false;
			}
		} else if (!label.equals(other.label)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean free(final String variable) {
		return label.equals(variable);
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
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
		return label.equals(variable) ? expression : this;
	}

	@Override
	public List<IExpression> subterm() {
		final List<IExpression> result = new LinkedList<IExpression>();
		result.add(this);
		return result;
	}

}
