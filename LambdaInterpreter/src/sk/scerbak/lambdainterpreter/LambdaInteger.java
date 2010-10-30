package sk.scerbak.lambdainterpreter;

/**
 * @author The0retico Lambda expression containing integer constant
 */
class LambdaInteger extends AbstractLambdaExpression {

	/**
	 * May be invalid, checked during evaluate method call.
	 */
	private final int value;

	/**
	 * @param value
	 *            any Integer
	 */
	public LambdaInteger(final Integer number) {
		this.value = number;
	}

	@Override
	public int evaluate() {
		if (value < 0) {
			throw new IllegalArgumentException();
		}
		return value;
	}

}
