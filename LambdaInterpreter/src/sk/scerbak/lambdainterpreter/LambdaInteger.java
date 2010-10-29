package sk.scerbak.lambdainterpreter;

public class LambdaInteger extends LambdaExpression {

	private final int value;

	public LambdaInteger(Integer value) {
		this.value = value;
	}

	@Override
	public int evaluate() {
		if (value < 0) {
			throw new IllegalArgumentException();
		}
		return value;
	}

}
