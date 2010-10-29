package sk.scerbak.lambdainterpreter;

public class LambdaAbstraction extends LambdaExpression {
	private final String variable;
	private final LambdaExpression expression;

	public LambdaAbstraction(String variable, LambdaExpression expression) {
		this.variable = variable;
		this.expression = expression;
	}

	@Override
	public int evaluate() {
		return -1;
	}
}
