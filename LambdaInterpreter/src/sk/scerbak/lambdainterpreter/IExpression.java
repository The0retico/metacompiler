package sk.scerbak.lambdainterpreter;

import java.util.List;

/**
 * @author The0retico Every type of lambda expression should implement this
 *         interface.
 */
interface IExpression {

	/**
	 * This method should traverse expression in leftmost-outermost first order.
	 * 
	 * @param visitor
	 *            should implement visit(IExpression) according to Visitor
	 *            pattern
	 */
	public void accept(IVisitor visitor);

	/**
	 * @param other
	 *            expression to be compared with this
	 * @return true if both expressions are equal according to alpha equivalence
	 */
	boolean equals(IExpression other);

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
	 * @return true if this expression can be reduced using beta reduction
	 */
	boolean isReducible();

	/**
	 * @return equivalent lambda expression in normal form
	 */
	IExpression normalForm();

	/**
	 * @return reduced lambda expression by one step or the same expression,
	 *         when it cannot be reduced further
	 */
	IExpression oneStepBetaReduce();

	/**
	 * @param variable
	 *            name which should be substituted
	 * @param expression
	 *            which will substitute variable
	 * @return TODO
	 */
	IExpression substitute(String variable, IExpression expression);

	/**
	 * @return all subterm lambda expressions (including this one)
	 */
	List<IExpression> subterm();
}
