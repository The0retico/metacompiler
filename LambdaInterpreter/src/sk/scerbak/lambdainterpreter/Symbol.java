package sk.scerbak.lambdainterpreter;

import java.util.LinkedList;
import java.util.List;

/**
 * Symbol in a lambda expression, in this implementation is just an
 * implementation of common behaviour for specialized types of lambda symbols.
 * 
 * @author The0retico
 * 
 */
abstract class Symbol implements IExpression {

	@Override
	public boolean free(final String variable) {
		return true;
	}

	@Override
	public List<IExpression> subterm() {
		final List<IExpression> result = new LinkedList<IExpression>();
		result.add(this);
		return result;
	}

}
