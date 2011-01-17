package ebnf.grammar;

public class Rule {
	private final NonTerminal nonTerminal;
	private final Expression expression;

	public Rule(final NonTerminal name, final Expression rule) {
		nonTerminal = name;
		expression = rule;
	}
}