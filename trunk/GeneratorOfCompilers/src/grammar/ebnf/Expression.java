package grammar.ebnf;

import java.util.List;

public class Expression {
	private final Term term;
	private final List<Term> terms;

	public Expression(final Term head, final List<Term> tail) {
		term = head;
		terms = tail;
	}
}