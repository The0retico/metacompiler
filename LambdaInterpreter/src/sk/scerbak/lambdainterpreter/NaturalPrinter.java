package sk.scerbak.lambdainterpreter;

public class NaturalPrinter implements IVisitor {

	private int successorLevel;
	private String firstBoundVariable;
	private String secondBoundVariable;
	private boolean isNatural;

	public final int getValue() {
		return successorLevel;
	}

	/**
	 * @param abstraction
	 *            which might represent a church natural
	 * @return true if the abstraction represents a church natural
	 */
	public final boolean isNatural(final Abstraction abstraction) {
		successorLevel = 0;
		firstBoundVariable = "";
		secondBoundVariable = "";
		isNatural = false;
		abstraction.accept(this);
		return isNatural;
	}

	@Override
	public final void visit(final Abstraction abstraction) {
		if (secondBoundVariable.isEmpty()) {
			if (firstBoundVariable.isEmpty()) {
				firstBoundVariable = abstraction.getVariable();
			} else {
				secondBoundVariable = abstraction.getVariable();
			}
			abstraction.getBody().accept(this);
		}
	}

	@Override
	public final void visit(final Application application) {
		if (!secondBoundVariable.isEmpty() && !firstBoundVariable.isEmpty()) {
			final int before = successorLevel;
			application.getFunction().accept(this);
			if (successorLevel - before == 1) {
				application.getArgument().accept(this);
			}
		}
	}

	@Override
	public void visit(final IExpression expression) {
		// TODO Auto-generated method stub

	}

	@Override
	public final void visit(final Variable variable) {
		final String variableName = variable.getLabel();
		if (firstBoundVariable.equals(variableName)) {
			successorLevel++;
		}
		isNatural = secondBoundVariable.equals(variableName);
	}

}
