package metacompiler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class EvaluateExpressions {

	@BeforeClass
	public static final void config() {
		Grammar.LOGGER.setLevel(Level.OFF);
		Parser.LOGGER.setLevel(Level.OFF);
		MetaCompiler.LOGGER.setLevel(Level.INFO);
	}

	private Reader grammar;

	@Test
	public final void everything() throws IOException, ParserException,
			LexerException {
		final Reader program = new StringReader("100/(2+3)*10-99");
		final ImmutableList<Entry<String, Production>> result = MetaCompiler
				.compile(grammar, program);
		assertNotNull(result);
		assertEquals(52, result.size());
	}

	@Test
	public final void nonzero() throws IOException, ParserException,
			LexerException {
		final Reader program = new StringReader("2");
		final ImmutableList<Entry<String, Production>> result = MetaCompiler
				.compile(grammar, program);
		assertNotNull(result);
		assertEquals(9, result.size());
		assertEquals("2", MetaCompiler.execute(result));
	}

	@Test
	public final void parentheses() throws IOException, ParserException,
			LexerException {
		final Reader program = new StringReader("(2*1)");
		final ImmutableList<Entry<String, Production>> result = MetaCompiler
				.compile(grammar, program);
		assertNotNull(result);
		assertEquals(21, result.size());
		assertEquals("2", MetaCompiler.execute(result));
	}

	@Before
	public final void setUp() throws FileNotFoundException {
		grammar = new FileReader("./grammars/Expressions.bnf");
	}

	@Test
	public final void sum() throws IOException, ParserException, LexerException {
		final Reader program = new StringReader("2+3");
		final ImmutableList<Entry<String, Production>> result = MetaCompiler
				.compile(grammar, program);
		assertNotNull(result);
		assertEquals(17, result.size());
		assertEquals("5", MetaCompiler.execute(result));
	}
}
