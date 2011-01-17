package grammar.ebnf;


public class Comment implements Factor {
	private final String value;

	public Comment(final String comment) {
		value = comment;
	}
}