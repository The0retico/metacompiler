package sk.scerbak.lambdainterpreter;

/**
 * Printer visitor which after visiting a lambda expression will return through
 * toString methods string representation of that expression.
 * 
 * @author The0retico
 * 
 */
public class Printer implements IVisitor {

	/**
	 * Convenience method for printing lambda expressions.
	 * 
	 * @param expression
	 *            to be printed
	 * @return string representation of expression
	 */
	public static String toString(final IExpression expression) {
		final Printer printer = new Printer();
		expression.accept(printer);
		return printer.toString();
	}

	/**
	 * Output message to be printed after traversing a lambda expression
	 */
	private final StringBuilder output;
	/**
	 * Count abstraction levels.
	 */
	private int abstractionLevel;

	/**
	 * Determines if we are in a lambda expression which represents a natural.
	 */
	private final boolean inNatural;

	public Printer() {
		output = new StringBuilder();
		abstractionLevel = 0;
		inNatural = false;
	}

	/**
	 * 
	 */
	private void closeParenthesis() {
		while (abstractionLevel > 0) {
			output.append(')');
			abstractionLevel--;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return output.toString();
	}

	@Override
	public void visit(final Abstraction abstraction) {
		output.append("(" + abstraction.getVariable() + "|");
		abstractionLevel++;
	}

	@Override
	public void visit(final Application application) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(final Constant constant) {
		output.append(constant.getLabel());
		closeParenthesis();
	}

	@Override
	public void visit(final IExpression expression) {
		output.append(expression.toString());
		closeParenthesis();
	}

	@Override
	public void visit(final Natural number) {
		output.append(number.getValue());
		closeParenthesis();
	}

	@Override
	public void visit(final Variable variable) {
		output.append(variable.getLabel());
		closeParenthesis();
	}

}
