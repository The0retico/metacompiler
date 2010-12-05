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
	 * @return pairs of symbol and respective token as parameters for this test
	 */
	@Parameters
	public static final Collection<Object[]> symbols() {
		final Object[][] parameters = new Object[][] {
				{ "|", Keyword.ALTERNATION }, { ",", Keyword.CONCATENATION },
				{ "=", Keyword.DEFINITION }, { "-", Keyword.EXCEPTION },
				{ ";", Keyword.TERMINATION }, { "(", Keyword.LEFT_GROUPING },
				{ "[", Keyword.LEFT_OPTION }, { "{", Keyword.LEFT_REPETITION },
				{ "*", Keyword.REPETITION }, { ")", Keyword.RIGHT_GROUPING },
				{ "]", Keyword.RIGHT_OPTION },
				{ "}", Keyword.RIGHT_REPETITION },
				{ "x", new Identifier("x") }, { "abc", new Identifier("abc") },
				{ "1", new Number(1) }, { "123", new Number(123) },
				{ " = ", Keyword.DEFINITION }, { "\t=\t", Keyword.DEFINITION },
				{ "\n=\n", Keyword.DEFINITION },
				{ "\r=\r", Keyword.DEFINITION },
				{ "\t123  ", new Number(123) },
				{ "\rabc\n", new Identifier("abc") }, 
				{"a4", new Identifier("a4")},
				{"\"abc\"", new Terminal("abc")},{"'3a'", new Terminal("3a")},
				{"'a34b!@#$%^&*()_+'", new Terminal("a34b!@#$%^&*()_+")},	
				{"'\"'", new Terminal("\"")},{"\"'\"", new Terminal("'")},
				{"'[]{}();=,|-'", new Terminal("[]{}();=,|-")},{"\"[]{}();=,|-\"", new Terminal("[]{}();=,|-")},
				{"(*ahoj*)", new Comment("ahoj")},{"(*)*)", new Comment(")")},
				{"?d<=4;?", new Special("d<=4;")}
				};
		return Arrays.asList(parameters);
	}

	/**
	 * Lexer with parameterized input.
	 */
	private final Lexer lexer;

	/**
	 * Type of the token for the symbol in the input of lexer.
	 */
	private final IToken token;

	/**
	 * @param input
	 *            text parameter containing single symbol for this test
	 * @param tokenType
	 *            for the symbol in the input
	 */
	public LexerTest(final String input, final IToken tokenType) {
		lexer = new Lexer(input);
		token = tokenType;
	}

	/**
	 * Test one symbol in the input being scanned properly.
	 * 
	 * @throws Exception
	 *             if symbol could not be recognized
	 */
	@Test
	public final void symbolToToken() throws Exception {
		assertTrue(
				token.getValue() + " symbol should be recognized as a token",
				lexer.hasNextToken());
		assertEquals(token, lexer.getNextToken());
		assertFalse("Lexer should not offer any more tokens",
				lexer.hasNextToken());
	}

}
