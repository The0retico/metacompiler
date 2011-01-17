package grammar.ebnf;

public class Repetition implements Factor {
	private final int number;
	private final Expression expression;

	public Repetition(final Expression repeated, final int times) {
		number = times;
		expression = repeated;
	}
}