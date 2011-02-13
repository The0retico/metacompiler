package sk.scerbak.lambdainterpreter;

public class Interpreter {

	public static final String execute(final String code) {
		final IExpression expression = Parser.fromString(code);
		return Printer.toString(expression.normalForm());
	}
}
