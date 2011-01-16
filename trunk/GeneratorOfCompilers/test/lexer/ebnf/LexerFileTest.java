package lexer.ebnf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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
public class LexerFileTest {

	/**
	 * @return pairs of path and arrays of respective tokens as parameters for
	 *         this test
	 */
	@Parameters
	public static final Collection<Object[]> symbols() {
		final Object[][] parameters = new Object[][] {
				{ "grammars/EBNF.ebnf", new IToken[] {} },
				{ "grammars/LambdaCalculus.ebnf", new IToken[] {} } };
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
	 *            text parameter containing path to the grammar for this test
	 * @param tokenTypes
	 *            for the symbols in the input
	 * @throws FileNotFoundException
	 *             if path doesn't point to a grammar file
	 */
	public LexerFileTest(final String path, final IToken[] tokenTypes)
			throws FileNotFoundException {
		lexer = new Lexer(new File(path));
		tokens = new LinkedList<IToken>(Arrays.asList(tokenTypes));
	}

	/**
	 * Test symbols in the input being scanned properly.
	 * 
	 * @throws Exception
	 *             if symbol could not be recognized
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
