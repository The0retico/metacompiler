package ebnf.grammar;

public class NonTerminal implements Factor {
	private final String string;

	public NonTerminal(final String name) {
		string = name;
	}
}