package metacompiler;

import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class BNFGrammars {
	@BeforeClass
	public static final void config() {
		Grammar.LOGGER.setLevel(Level.WARNING);
		Parser.LOGGER.setLevel(Level.INFO);
	}

	private FileReader grammar;

	private FileReader program;

	@Test
	public final void bnfGrammars() throws IOException, ParserException,
			LexerException {
		final ImmutableList<Entry<String, ImmutableList<String>>> result = MetaCompiler
				.compile(grammar, program);
		assertNotNull(result);
	}

	@Before
	public final void setUp() throws FileNotFoundException {
		grammar = new FileReader("./grammars/BNF.bnf");
		program = new FileReader("./grammars/Expressions.bnf");
	}
}
