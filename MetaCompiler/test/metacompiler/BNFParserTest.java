package metacompiler;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map.Entry;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class BNFParserTest {

	private static final String NON_TERMINAL = "singleword";;

	@Test
	public final void oneRule() throws IOException, ParserException {
		final Reader grammarText = new StringReader(NON_TERMINAL + " = 'aaa';");
		final Grammar grammar = BNFParser.analyze(grammarText);
		assertEquals(NON_TERMINAL, grammar.getStartSymbol());
	}

	@Test
	public final void oneRuleWithAlternative() throws IOException,
			ParserException {
		final Reader grammarText = new StringReader(NON_TERMINAL
				+ " = 'aaa' | 'b' 'c';");
		final Grammar grammar = BNFParser.analyze(grammarText);
		assertEquals(NON_TERMINAL, grammar.getStartSymbol());
		int rulesCount = 0;
		for (final Entry<String, ImmutableList<String>> rule : grammar) {
			rulesCount++;
			assertEquals(NON_TERMINAL, rule.getKey());
		}
		assertEquals(2, rulesCount);
	}

	@Test
	public final void oneRuleWithMultipleAlternative() throws IOException,
			ParserException {
		final Reader grammarText = new StringReader(NON_TERMINAL
				+ " = 'aaa' | 'b' | 'cc' ;\n");
		final Grammar grammar = BNFParser.analyze(grammarText);
		assertEquals(NON_TERMINAL, grammar.getStartSymbol());
	}
}
