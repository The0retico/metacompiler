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
	 * Output message to be printed after traversing a lambda expression.
	 */
	private final StringBuilder output;
	private int applicationLevel;

	public Printer() {
		output = new StringBuilder();
		applicationLevel = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return output.toString();
	}

	@Override
	public final void visit(final Abstraction abstraction) {
		output.append("(" + abstraction.getVariable() + "|");
		abstraction.getBody().accept(this);
		output.append(")");
	}

	@Override
	public void visit(final Application application) {
		if (applicationLevel == 0) {
			output.append("(");
		}
		applicationLevel++;
		application.getFunction().accept(this);
		applicationLevel--;
		output.append(" ");
		application.getArgument().accept(this);
		if (applicationLevel == 0) {
			output.append(")");
		}
	}

	@Override
	public final void visit(final Constant constant) {
		output.append(constant.name());
	}

	@Override
	public final void visit(final IExpression expression) {
		output.append(expression.toString());
	}

	@Override
	public final void visit(final Natural number) {
		output.append(number.getValue());
	}

	@Override
	public final void visit(final Variable variable) {
		output.append(variable.getLabel());
	}

}
