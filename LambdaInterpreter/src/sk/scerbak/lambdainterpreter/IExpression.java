package sk.scerbak.lambdainterpreter;

import java.util.List;

/**
 * @author The0retico Every type of lambda expression should implement this
 *         interface.
 */
interface IExpression {

	/**
	 * Determines if variable is free in this lambda expression.
	 * 
	 * @param variable
	 *            lower-case string label
	 * @return true if variable is a label of a free variable in this lambda
	 *         expression, false otherwise
	 */
	boolean free(String variable);

	/**
	 * @return all subterm lambda expressions (including this one)
	 */
	List<IExpression> subterm();

	/**
	 * @param variable
	 *            name which should be substituted
	 * @param expression
	 *            which will substitute variable
	 * @return TODO
	 */
	IExpression substitute(String variable, IExpression expression);

	/**
	 * @return reduced lambda expression by one step or the same expression,
	 *         when it cannot be reduced further
	 */
	IExpression oneStepBetaReduce();

	/**
	 * @return equivalent lambda expression in normal form
	 */
	IExpression normalForm();
}
