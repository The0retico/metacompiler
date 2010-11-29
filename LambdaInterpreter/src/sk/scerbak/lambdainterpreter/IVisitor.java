package sk.scerbak.lambdainterpreter;

/**
 * Visitor pattern interface for lambda expressions to implement pattern
 * matching. Every visit method can be seen as case expression in a particular
 * pattern matching function, which is a class implementing this interface.
 * 
 * @author The0retico
 * 
 */
public interface IVisitor {
	/**
	 * @param abstraction
	 *            to be visited
	 */
	public void visit(Abstraction abstraction);

	/**
	 * @param application
	 *            to be visited
	 */
	public void visit(Application application);

	/**
	 * @param expression
	 *            to be visited
	 */
	public void visit(IExpression expression);

	/**
	 * @param variable
	 *            to be visited
	 */
	public void visit(Variable variable);
}
