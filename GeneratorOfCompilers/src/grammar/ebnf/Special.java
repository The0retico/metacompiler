package grammar.ebnf;

public class Special implements Factor {
	private final String specialSequence;

	public Special(final String special) {
		specialSequence = special;
	}
}