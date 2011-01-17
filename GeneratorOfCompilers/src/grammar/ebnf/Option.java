package grammar.ebnf;

public class Option implements Factor {
	private final Expression expression;

	public Option(final Expression optional) {
		expression = optional;
	}
}