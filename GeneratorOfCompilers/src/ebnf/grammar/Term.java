package ebnf.grammar;

import java.util.List;

public class Term {
	private final Factor factor;
	private final List<Factor> factors;

	public Term(final Factor head, final List<Factor> tail) {
		factor = head;
		factors = tail;
	}
}