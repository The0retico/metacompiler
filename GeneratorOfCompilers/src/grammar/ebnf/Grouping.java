package grammar.ebnf;

public class Grouping implements Factor {
	private final Expression expression;

	public Grouping(final Expression group) {
		expression = group;
	}
}