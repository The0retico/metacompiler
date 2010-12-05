package lexer.ebnf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import lexer.ebnf.Keyword.UndefinedSymbolException;

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
				{ "}", Keyword.RIGHT_REPETITION } };
		return Arrays.asList(parameters);
	}

	/**
	 * Lexer with parameterized input.
	 */
	private final Lexer lexer;

	/**
	 * Type of the token for the symbol in the input of lexer.
	 */
	private final Keyword token;

	/**
	 * @param input
	 *            text parameter containing single symbol for this test
	 * @param tokenType
	 *            for the symbol in the input
	 */
	public LexerTest(final String input, final Keyword tokenType) {
		lexer = new Lexer(input);
		token = tokenType;
	}

	/**
	 * Test one symbol in the input being scanned properly.
	 * 
	 * @throws UndefinedSymbolException
	 *             if symbol could not be recognized
	 */
	@Test
	public final void symbolToToken() throws UndefinedSymbolException {
		assertTrue(token.name() + " symbol should be recognized as a token",
				lexer.hasNextToken());
		assertEquals(token, lexer.getNextToken());
		assertFalse("Lexer should not offer any more tokens",
				lexer.hasNextToken());
	}

}
