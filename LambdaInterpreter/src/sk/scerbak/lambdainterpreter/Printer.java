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
	private final NaturalPrinter naturalReductor;

	public Printer() {
		output = new StringBuilder();
		applicationLevel = 0;
		naturalReductor = new NaturalPrinter();
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
		if (naturalReductor.isNatural(abstraction)) {
			output.append(naturalReductor.getValue());
		} else if (Calculus.PRED.equals(abstraction)) {
			output.append("PRED");
		} else if (Calculus.SUCC.equals(abstraction)) {
			output.append("SUCC");
		} else if (Calculus.PLUS.equals(abstraction)) {
			output.append("PLUS");
		} else if (Calculus.MULT.equals(abstraction)) {
			output.append("MULT");
		} else if (Calculus.ISZERO.equals(abstraction)) {
			output.append("ISZERO");
		} else if (Calculus.IF.equals(abstraction)) {
			output.append("IF");
		} else if (Calculus.TRUE.equals(abstraction)) {
			output.append("TRUE");
		} else if (Calculus.FALSE.equals(abstraction)) {
			output.append("FALSE");
		} else if (Calculus.NOT.equals(abstraction)) {
			output.append("NOT");
		} else if (Calculus.AND.equals(abstraction)) {
			output.append("AND");
		} else if (Calculus.OR.equals(abstraction)) {
			output.append("OR");
		} else if (Calculus.PAIR.equals(abstraction)) {
			output.append("PAIR");
		} else if (Calculus.LEFT.equals(abstraction)) {
			output.append("LEFT");
		} else if (Calculus.RIGHT.equals(abstraction)) {
			output.append("RIGHT");
		} else if (Calculus.Y.equals(abstraction)) {
			output.append("Y");
		} else {
			output.append("(" + abstraction.getVariable() + "|");
			abstraction.getBody().accept(this);
			output.append(")");
		}
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
	public final void visit(final IExpression expression) {
		output.append(expression.toString());
	}

	@Override
	public final void visit(final Variable variable) {
		output.append(variable.getLabel());
	}
}
