package sk.scerbak.lambdainterpreter;

import java.util.List;

/**
 * @author The0retico Every type of lambda expression should implement this
 *         interface.
 */
interface ILambdaExpression {

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
	 * Equivalence by alpha conversion. Alpha conversion is process of
	 * substituting variable for other variable, which is bound in current
	 * lambda expression.
	 * 
	 * @param other
	 *            object to which this should be equal
	 * @return true if other is of same type of lambda expression as this and
	 *         other can be alpha converted to this
	 */
	@Override
	boolean equals(Object other);

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
