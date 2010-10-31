package sk.scerbak.lambdainterpreter;

import java.util.List;

/**
 * Lambda abstraction interpreter class.
 * 
 * @author The0retico
 */
class LambdaAbstraction implements ILambdaExpression {

	/**
	 * Body of this lambda abstraction, which is any lambda expression.
	 */
	private final ILambdaExpression body;
	/**
	 * Label for variable of this lambda abstraction.
	 */
	private final String variable;

	/**
	 * @param variableLabel
	 *            for this abstraction
	 * @param expression
	 *            abstraction body
	 */
	public LambdaAbstraction(final String variableLabel,
			final ILambdaExpression expression) {
		this.variable = variableLabel;
		this.body = expression;
	}

	@Override
	public boolean free(final String variableLabel) {
		return !bounds(variableLabel) && body.free(variableLabel);
	}

	/**
	 * @param variableLabel
	 *            string containing a variable name
	 * @return true if the variableLabel represents variable of this
	 */
	private boolean bounds(final String variableLabel) {
		return this.variable.equals(variableLabel);
	}

	@Override
	public List<ILambdaExpression> subterm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILambdaExpression substitute(final String variableLabel,
			final ILambdaExpression expression) {
		// TODO Auto-generated method stub
		return null;
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

}
