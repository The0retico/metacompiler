package ebnf.lexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ebnf.lexer.Keyword.Type;

/**
 * Unit tests for the lexer of the metagrammar langauge (EBNF).
 * 
 * @author sarvasmartin
 * 
 */
@RunWith(Parameterized.class)
public class LexerTest {

	/**
	 * @return pairs of symbol and arrays of respective tokens as parameters for
	 *         this test
	 */
	@Parameters
	public static final Collection<Object[]> symbols() {
		final Object[][] parameters = new Object[][] {
				{ "-", new IToken[] { new Keyword(Type.EXCEPTION, 1, 0) } },
				{ "(", new IToken[] { new Keyword(Type.LEFT_GROUPING, 1, 0) } },
				{ "[", new IToken[] { new Keyword(Type.LEFT_OPTION, 1, 0) } },
				{ "{", new IToken[] { new Keyword(Type.LEFT_REPETITION, 1, 0) } },
				{ "*", new IToken[] { new Keyword(Type.REPETITION, 1, 0) } },
				{ ")", new IToken[] { new Keyword(Type.RIGHT_GROUPING, 1, 0) } },
				{ "]", new IToken[] { new Keyword(Type.RIGHT_OPTION, 1, 0) } },
				{
						"}",
						new IToken[] { new Keyword(Type.RIGHT_REPETITION, 1, 0) } },
				{ "=", new IToken[] { new Keyword(Type.DEFINITION, 1, 0) } },
				{ "= ", new IToken[] { new Keyword(Type.DEFINITION, 1, 0) } },
				{ " = ", new IToken[] { new Keyword(Type.DEFINITION, 1, 1) } },
				{ "\t,\t",
						new IToken[] { new Keyword(Type.CONCATENATION, 1, 1) } },
				{ "\n|\n", new IToken[] { new Keyword(Type.ALTERNATION, 2, 0) } },
				{ "\r;\r", new IToken[] { new Keyword(Type.TERMINATION, 2, 0) } },
				{ "\t123  ", new IToken[] { new Number(123, 1, 1) } },
				{ "\rabc4\n", new IToken[] { new Identifier("abc4", 2, 0) } },
				{ "\"abc\"", new IToken[] { new Terminal("abc", 1, 0) } },
				{ "'3a \n '", new IToken[] { new Terminal("3a \n ", 1, 0) } },
				{ "(*ahoj*)a", new IToken[] { new Identifier("a", 1, 8) } },
				{ "?d<=4; \n ?",
						new IToken[] { new Special("d<=4; \n ", 1, 0) } },
				{ "(*a \n s*)1", new IToken[] { new Number(1, 2, 4) } },
				{
						"list = 'head', list | 'tail';",
						new IToken[] { new Identifier("list", 1, 0),
								new Keyword(Type.DEFINITION, 1, 5),
								new Terminal("head", 1, 7),
								new Keyword(Type.CONCATENATION, 1, 13),
								new Identifier("list", 1, 15),
								new Keyword(Type.ALTERNATION, 1, 20),
								new Terminal("tail", 1, 22),
								new Keyword(Type.TERMINATION, 1, 28) } },
				{
						"assignment = identifier , ':=' ,(*g*sd'g*) ( number | identifier | string ) ;",
						new IToken[] { new Identifier("assignment", 1, 0),
								new Keyword(Type.DEFINITION, 1, 10),
								new Identifier("identifier", 1, 13),
								new Keyword(Type.CONCATENATION, 1, 24),
								new Terminal(":=", 1, 26),
								new Keyword(Type.CONCATENATION, 1, 31),
								new Keyword(Type.LEFT_GROUPING, 1, 43),
								new Identifier("number", 1, 45),
								new Keyword(Type.ALTERNATION, 1, 52),
								new Identifier("identifier", 1, 54),
								new Keyword(Type.ALTERNATION, 1, 65),
								new Identifier("string", 1, 67),
								new Keyword(Type.RIGHT_GROUPING, 1, 74),
								new Keyword(Type.TERMINATION, 1, 76) } } };
		return Arrays.asList(parameters);
	}

	/**
	 * Lexer with parameterized input.
	 */
	private final Lexer lexer;

	/**
	 * Type of the token for the symbol in the input of lexer.
	 */
	private final List<IToken> tokens;

	/**
	 * @param input
	 *            text parameter containing single symbol for this test
	 * @param tokenTypes
	 *            for the symbols in the input
	 */
	public LexerTest(final String input, final IToken[] tokenTypes) {
		lexer = new Lexer(input);
		tokens = new LinkedList<IToken>(Arrays.asList(tokenTypes));
	}

	/**
	 * Test symbols in the input being scanned properly.
	 */
	@Test
	public final void symbolToToken() {
		for (final IToken expectedToken : tokens) {
			assertTrue(expectedToken.getValue()
					+ " symbol should be recognized as a token",
					lexer.hasNext());
			final IToken actualToken = lexer.next();
			assertNotNull(actualToken);
			assertEquals(expectedToken, actualToken);
		}
		assertFalse("Lexer should not offer any more tokens", lexer.hasNext());
	}
}
