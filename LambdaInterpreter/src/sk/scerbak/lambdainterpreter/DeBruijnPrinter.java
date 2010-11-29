package sk.scerbak.lambdainterpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * Printer for lambda expressions which uses de Bruijn indexes instead of
 * variable names to avoid substitution problems.
 * 
 * @author The0retico
 * 
 */
public class DeBruijnPrinter implements IVisitor {

	/**
	 * @param expression
	 *            to be printed
	 * @return string representation of the expression
	 */
	public static String toString(final IExpression expression) {
		final DeBruijnPrinter printer = new DeBruijnPrinter();
		expression.accept(printer);
		return printer.toString();
	}

	/**
	 * Map to keep track of levels at which variables are bound in abstractions.
	 */
	private final Map<String, Integer> variableLevels;

	/**
	 * Current abstraction level while traversing the expression.
	 */
	private int abstractionLevel;

	/**
	 * String output builder for an expression.
	 */
	private final StringBuilder output;

	/**
	 * Used for counting nested applications to allow associativity of
	 * applications.
	 */
	private int applicationLevel;

	private int numberOfFreeVariables;

	private DeBruijnPrinter() {
		output = new StringBuilder();
		variableLevels = new HashMap<String, Integer>();
		abstractionLevel = 0;
		applicationLevel = 0;
		numberOfFreeVariables = 0;
	}

	@Override
	public final String toString() {
		return output.toString();
	}

	@Override
	public void visit(final Abstraction abstraction) {
		final int currentAbstractionLevel = abstractionLevel;
		final String variableName = abstraction.getVariable();
		int originalLevel = -1;
		if (variableLevels.containsKey(variableName)) {
			originalLevel = variableLevels.get(variableName);
		}
		variableLevels.put(variableName, currentAbstractionLevel);
		abstractionLevel++;
		output.append("(|");
		abstraction.getBody().accept(this);
		abstractionLevel = currentAbstractionLevel;
		if (originalLevel != -1) {
			variableLevels.remove(variableName);
			variableLevels.put(variableName, originalLevel);
		}
		output.append(")");
	}

	@Override
	public void visit(final Application application) {
		final int currentAbstractionLevel = abstractionLevel;
		if (applicationLevel == 0) {
			output.append('(');
		}
		applicationLevel++;
		application.getFunction().accept(this);
		abstractionLevel = currentAbstractionLevel;
		applicationLevel--;
		output.append(' ');
		application.getArgument().accept(this);
		abstractionLevel = currentAbstractionLevel;
		if (applicationLevel == 0) {
			output.append(')');
		}
	}

	@Override
	public void visit(final IExpression expression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(final Variable variable) {
		final String variableName = variable.getLabel();
		if (variableLevels.containsKey(variableName)) {
			final int deBruijnIndex = abstractionLevel
					- variableLevels.get(variableName) - 1;
			output.append(deBruijnIndex);
		} else {
			numberOfFreeVariables++;
			output.append(abstractionLevel + numberOfFreeVariables);
		}
	}

}
