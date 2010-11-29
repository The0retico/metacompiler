package sk.scerbak.lambdainterpreter;

import static sk.scerbak.lambdainterpreter.Calculus.I;
import static sk.scerbak.lambdainterpreter.Calculus.K;
import static sk.scerbak.lambdainterpreter.Calculus.S;
import static sk.scerbak.lambdainterpreter.Calculus.apply;
import static sk.scerbak.lambdainterpreter.Calculus.def;

/**
 * Visitor for transforming lambda expressions into SKI combinator logic.
 * 
 * @author The0retico
 * 
 */
public class SKITransformer implements IVisitor {

	private class SKIValidator implements IVisitor {

		private boolean isInSKI;

		SKIValidator() {
			isInSKI = true;
		}

		public boolean notInSKI() {
			return !isInSKI;
		}

		@Override
		public void visit(final Abstraction abstraction) {
			if (!(S.equals(abstraction) || K.equals(abstraction) || I
					.equals(abstraction))) {
				isInSKI = false;
			}
		}

		@Override
		public void visit(final Application application) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(final IExpression expression) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visit(final Variable variable) {
			isInSKI = false;
		}

	}

	/**
	 * @param expression
	 *            to be transformed
	 * @return expression transformed into SKI combinator logic
	 */
	public static IExpression transform(final IExpression expression) {
		final SKITransformer transformer = new SKITransformer();
		expression.accept(transformer);
		return transformer.getResult();
	}

	/**
	 * Resulting expression in SKI combinator logic.
	 */
	private IExpression result;
	private String boundVariable;

	private SKITransformer() {

	}

	private IExpression getResult() {
		return result;
	}

	@Override
	public void visit(final Abstraction abstraction) {
		boundVariable = abstraction.getVariable();
		abstraction.getBody().accept(this);
		final SKIValidator validator = new SKIValidator();
		result.accept(validator);
		while (validator.notInSKI()) {
			def(boundVariable, result).accept(this);
		}
	}

	@Override
	public void visit(final Application application) {
		def(boundVariable, application.getFunction()).accept(this);
		final IExpression transformedFunction = result;
		def(boundVariable, application.getArgument()).accept(this);
		final IExpression transformedArgument = result;
		result = apply(S, transformedFunction, transformedArgument);
	}

	@Override
	public void visit(final IExpression expression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(final Variable variable) {
		if (boundVariable.equals(variable.getLabel())) {
			result = I;
		} else {
			result = apply(K, variable);
		}
	}

}
