package metacompiler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

public class ParserTest {

	@BeforeClass
	public static final void config() {
		Grammar.LOGGER.setLevel(Level.OFF);
		Parser.LOGGER.setLevel(Level.OFF);
	}

	private Grammar grammar;

	@Test
	public final void analyze() throws IOException, LexerException,
			ParserException {
		final Parser parser = new Parser(grammar);
		final ImmutableList<Entry<String, Entry<ImmutableList<String>, String>>> derivations = parser
				.analyze(new StringReader("ab"));
		assertNotNull(derivations);
		assertEquals(2, derivations.size());
		final Entry<String, Entry<ImmutableList<String>, String>> derivation = derivations
				.get(0);
		assertEquals("A", derivation.getKey());
		assertEquals(2, derivation.getValue().getKey().size());
		assertEquals("\'ab\'", derivation.getValue().getKey().get(0));
	}

	@Test(expected = ParserException.class)
	public final void badInput() throws IOException, LexerException,
			ParserException {
		new Parser(grammar).analyze(new StringReader("abababef"));
	}

	@Test
	public final void createTable() {
		final Table<Character, String, Entry<ImmutableList<String>, String>> table = Parser
				.createTable(grammar);
		assertNotNull(table);
		assertEquals(4, table.rowKeySet().size());
		assertEquals(1, table.columnKeySet().size());
		assertEquals("\'ab\'", table.get('a', "A").getKey().get(0));
		assertEquals("\'cd\'", table.get('c', "A").getKey().get(0));
	}

	@Before
	public final void setUp() {
		final ImmutableListMultimap.Builder<String, Entry<ImmutableList<String>, String>> result = ImmutableListMultimap
				.builder();
		result.put("A",
				Maps.immutableEntry(ImmutableList.of("\'ab\'", "A"), ""));
		result.put("A", Maps.immutableEntry(ImmutableList.of("\'\'"), ""));
		result.put("A", Maps.immutableEntry(ImmutableList.of("\'cd\'"), ""));
		grammar = new Grammar(result.build());
	}
}
