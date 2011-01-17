package lexer.ebnf;

import java.util.Arrays;
import java.util.Iterator;

public class LexerMock implements Iterator<IToken> {

	private final Iterator<IToken> iterator;

	public LexerMock(final IToken[] tokens) {
		iterator = Arrays.asList(tokens).iterator();
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public IToken next() {
		return iterator.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
