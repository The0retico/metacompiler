package metacompiler;

import static org.junit.Assert.assertTrue;

import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;

import org.junit.BeforeClass;
import org.junit.Test;

public class GrammarTest {

	@BeforeClass
	public static final void config() {
		Grammar.LOGGER.setLevel(Level.OFF);
	}

	@Test
	public final void followStartSymbol() throws IOException, ParserException {
		final Grammar grammar = BNFParser.analyze(new FileReader(
				"./grammars/Expressions.bnf"));
		assertTrue(grammar.follow("expr").contains(')'));
	}

	@Test
	public final void isTerminal() {
		assertTrue(Grammar.IS_TERMINAL.apply("\'ab\'"));
	}
}
