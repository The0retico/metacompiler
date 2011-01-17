package ebnf.parser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Iterator;

import org.junit.Test;

import ebnf.grammar.Grammar;
import ebnf.lexer.IToken;
import ebnf.lexer.Identifier;
import ebnf.lexer.Keyword;
import ebnf.lexer.Keyword.Type;
import ebnf.lexer.LexerMock;
import ebnf.lexer.Terminal;
import ebnf.parser.Parser;

public class ParserTest {

	@Test
	public void empty() {
		final Iterator<IToken> lexer = new LexerMock(new IToken[] {});
		final Grammar grammar = new Parser(lexer).analyze();
		assertNull(grammar);
	}

	@Test
	public void simpleRule() {
		final Iterator<IToken> lexer = new LexerMock(new IToken[] {
				new Identifier("Character", 1, 0),
				new Keyword(Type.DEFINITION, 1, 9), new Terminal("a", 1, 11),
				new Keyword(Type.TERMINATION, 1, 12) });
		final Grammar grammar = new Parser(lexer).analyze();
		assertNotNull(
				"AST of a grammar with at least one rule shouldn't be empty",
				grammar);

	}
}
