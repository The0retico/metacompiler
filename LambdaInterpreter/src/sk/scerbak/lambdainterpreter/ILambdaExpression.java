package sk.scerbak.lambdainterpreter;

import java.util.List;

/**
 * @author The0retico Every type of lambda expression should implement this
 *         interface.
 */
public interface ILambdaExpression {

	/**
	 * @param variable
	 *            string label
	 * @return true if variable is a name of free variable in this lambda
	 *         expression
	 */
	boolean free(String variable);

	/**
	 * @return all subterm lambda expressions (including this one)
	 */
	List<ILambdaExpression> subterm();

	/**
	 * @param variable
	 *            name which should be substituted
	 * @param expression
	 *            which will substitute variable
	 * @return TODO
	 */
	ILambdaExpression substitute(String variable, ILambdaExpression expression);

	/**
	 * @return reduced lambda expression by one step or the same expression,
	 *         when it cannot be reduced further
	 */
	ILambdaExpression oneStepBetaReduce();

	/**
	 * @return equivalent lambda expression in normal form
	 */
	ILambdaExpression normalForm();
}
