package sk.scerbak.lambdainterpreter;

import java.util.LinkedList;
import java.util.List;

abstract class LambdaSymbol implements ILambdaExpression {

	@Override
	public boolean free(final String variable) {
		return true;
	}

	@Override
	public List<ILambdaExpression> subterm() {
		final List<ILambdaExpression> result = new LinkedList<ILambdaExpression>();
		result.add(this);
		return result;
	}

}
