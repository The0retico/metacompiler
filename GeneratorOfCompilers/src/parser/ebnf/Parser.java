package parser.ebnf;

import grammar.ebnf.Grammar;

import java.util.Iterator;

import lexer.ebnf.IToken;

public class Parser {

	private final Iterator<IToken> lexer;

	public Parser(final Iterator<IToken> tokenStream) {
		lexer = tokenStream;
	}

	public Grammar analyze() {
		final Grammar result;
		if (!lexer.hasNext()) {
			result = null;
		} else {
			result = new Grammar();
		}
		return result;
	}

	public AbstractSyntaxTree analyze(final Grammar grammar) {
		return null;
	}
}
