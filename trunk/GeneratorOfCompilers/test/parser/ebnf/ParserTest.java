package parser.ebnf;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import grammar.ebnf.Grammar;

import java.util.Iterator;

import lexer.ebnf.IToken;
import lexer.ebnf.Identifier;
import lexer.ebnf.Keyword;
import lexer.ebnf.Keyword.Type;
import lexer.ebnf.LexerMock;
import lexer.ebnf.Terminal;

import org.junit.Test;

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
