package ebnf.parser;

import ebnf.grammar.Grammar;
import ebnf.lexer.IToken;

import java.util.Iterator;


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
