package lexer.ebnf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

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
				{ "-", new IToken[] { Keyword.EXCEPTION } },
				{ "(", new IToken[] { Keyword.LEFT_GROUPING } },
				{ "[", new IToken[] { Keyword.LEFT_OPTION } },
				{ "{", new IToken[] { Keyword.LEFT_REPETITION } },
				{ "*", new IToken[] { Keyword.REPETITION } },
				{ ")", new IToken[] { Keyword.RIGHT_GROUPING } },
				{ "]", new IToken[] { Keyword.RIGHT_OPTION } },
				{ "}", new IToken[] { Keyword.RIGHT_REPETITION } },
				{ " = ", new IToken[] { Keyword.DEFINITION } },
				{ "\t,\t", new IToken[] { Keyword.CONCATENATION } },
				{ "\n|\n", new IToken[] { Keyword.ALTERNATION } },
				{ "\r;\r", new IToken[] { Keyword.TERMINATION } },
				{ "\t123  ", new IToken[] { new Number(123) } },
				{ "\rabc4\n", new IToken[] { new Identifier("abc4") } },
				{ "\"abc\"", new IToken[] { new Terminal("abc") } },
				{ "'3a \n '", new IToken[] { new Terminal("3a \n ") } },
				{ "(*ahoj*)a", new IToken[] { new Identifier("a") } },
				{ "?d<=4; \n ?", new IToken[] { new Special("d<=4; \n ") } },
				{ "(*a \n s*)1", new IToken[] { new Number(1) } },
				{
						"list = 'head', list | 'tail';",
						new IToken[] { new Identifier("list"),
								Keyword.DEFINITION, new Terminal("head"),
								Keyword.CONCATENATION, new Identifier("list"),
								Keyword.ALTERNATION, new Terminal("tail"),
								Keyword.TERMINATION } },
				{
						"assignment = identifier , ':=' ,(*g*sd'g*) ( number | identifier | string ) ;",
						new IToken[] { new Identifier("assignment"),
								Keyword.DEFINITION,
								new Identifier("identifier"),
								Keyword.CONCATENATION, new Terminal(":="),
								Keyword.CONCATENATION, Keyword.LEFT_GROUPING,
								new Identifier("number"), Keyword.ALTERNATION,
								new Identifier("identifier"),
								Keyword.ALTERNATION, new Identifier("string"),
								Keyword.RIGHT_GROUPING, Keyword.TERMINATION } } };
		return Arrays.asList(parameters);
	}

	/**
	 * Lexer with parameterized input.
	 */
	private final Lexer lexer;

	/**
	 * Type of the token for the symbol in the input of lexer.
	 */
	private final IToken[] tokens;

	/**
	 * @param input
	 *            text parameter containing single symbol for this test
	 * @param tokenTypes
	 *            for the symbols in the input
	 */
	public LexerTest(final String input, final IToken[] tokenTypes) {
		lexer = new Lexer(input);
		tokens = tokenTypes;
	}

	/**
	 * Test symbols in the input being scanned properly.
	 * 
	 * @throws Exception
	 *             if symbol could not be recognized
	 */
	@Test
	public final void symbolToToken() throws Exception {

		for (final IToken token : tokens) {
			assertTrue(token.getValue()
					+ " symbol should be recognized as a token",
					lexer.hasNextToken());
			assertEquals(token, lexer.getNextToken());
		}
		assertFalse("Lexer should not offer any more tokens",
				lexer.hasNextToken());
	}

}
