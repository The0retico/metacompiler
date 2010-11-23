package sk.scerbak.lambdainterpreter;

public class NaturalReductor implements IVisitor {

	private int abstractionLevel;
	private int applicationLevel;
	private String firstBoundVariable;
	private String secondBoundVariable;
	private boolean isNatural;
	private int succLevel;

	public int getValue() {
		if (applicationLevel == succLevel) {
			return succLevel;
		} else {
			return -1;
		}
	}

	public boolean isNatural(final Abstraction abstraction) {
		abstractionLevel = 0;
		applicationLevel = 0;
		succLevel = 0;
		firstBoundVariable = "";
		secondBoundVariable = "";
		isNatural = false;
		abstraction.accept(this);
		return isNatural;
	}

	@Override
	public void visit(final Abstraction abstraction) {
		if ("".equals(secondBoundVariable)) {
			if ("".equals(firstBoundVariable)) {
				firstBoundVariable = abstraction.getVariable();
			} else {
				firstBoundVariable = new String(secondBoundVariable);
				secondBoundVariable = abstraction.getVariable();
			}
			abstraction.getBody().accept(this);
		}
	}

	@Override
	public void visit(final Application application) {
		if (!firstBoundVariable.isEmpty() && !secondBoundVariable.isEmpty()
				&& !firstBoundVariable.equals(secondBoundVariable)) {
			applicationLevel++;
			application.getFunction().accept(this);
			application.getArgument().accept(this);
		}
	}

	@Override
	public void visit(final IExpression expression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(final Variable variable) {
		if (variable.getLabel().equals(firstBoundVariable)) {
			succLevel++;
		} else {
			isNatural = secondBoundVariable.equals(variable.getLabel());
		}
	}

}
