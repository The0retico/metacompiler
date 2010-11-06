package sk.scerbak.lambdainterpreter;

/**
 * @author The0retico Lambda expression containing integer constant
 */
class Natural extends Symbol implements IExpression {

	/**
	 * May be invalid, checked during evaluate method call.
	 */
	private final int value;

	/**
	 * @param integer
	 *            any Integer
	 */
	public Natural(final Integer integer) {
		super();
		this.value = integer;
		if (value < 0) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public IExpression substitute(final String variable,
			final IExpression expression) {
		return this;
	}

	@Override
	public IExpression oneStepBetaReduce() {
		return this;
	}

	@Override
	public IExpression normalForm() {
		return this;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

}
