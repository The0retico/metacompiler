package ebnf.grammar;

public class Terminal implements Factor {
	private final String string;

	public Terminal(final String name) {
		string = name;
	}
}