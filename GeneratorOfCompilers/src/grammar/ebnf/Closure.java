package grammar.ebnf;

public class Closure implements Factor {
	private final Expression expression;

	public Closure(final Expression repeated) {
		expression = repeated;
	}
}