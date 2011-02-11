package metacompiler;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.Map.Entry;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Maps;

class BNFParser {

	public static Grammar analyze(final Reader grammarText) throws IOException,
			ParserException {
		Preconditions.checkNotNull(grammarText);
		final StreamTokenizer lexer = createBNFLexer(grammarText);
		final BNFParser parser = new BNFParser(lexer);
		return parser.generate();
	}

	/**
	 * @param grammarText
	 * @return
	 */
	private static StreamTokenizer createBNFLexer(final Reader grammarText) {
		Preconditions.checkNotNull(grammarText);
		final StreamTokenizer lexer = new StreamTokenizer(grammarText);
		lexer.eolIsSignificant(false);
		lexer.lowerCaseMode(false);
		lexer.slashSlashComments(false);
		lexer.slashStarComments(false);
		lexer.whitespaceChars(0, 'A' - 1);
		lexer.whitespaceChars('Z' + 1, 'a' - 1);
		lexer.whitespaceChars('z' + 1, 0xFF);
		lexer.wordChars('a', 'z');
		lexer.wordChars('A', 'Z');
		lexer.ordinaryChar('=');
		lexer.ordinaryChar('|');
		lexer.ordinaryChar(';');
		lexer.quoteChar('\'');
		lexer.quoteChar('\"');
		return lexer;
	}

	private final StreamTokenizer lexer;

	BNFParser(final StreamTokenizer bnfLexer) {
		Preconditions.checkNotNull(bnfLexer);
		lexer = bnfLexer;
	}

	private Grammar generate() throws IOException, ParserException {
		final ImmutableListMultimap.Builder<String, Entry<ImmutableList<String>, String>> result = ImmutableListMultimap
				.builder();
		while (lexer.nextToken() != StreamTokenizer.TT_EOF) {
			result.putAll(parseRule());
		}
		return new Grammar(result.build());
	}

	/**
	 * @return
	 * @throws IOException
	 */
	private Iterable<Entry<ImmutableList<String>, String>> parseAlternatives()
			throws IOException {
		final ImmutableList.Builder<Entry<ImmutableList<String>, String>> result = ImmutableList
				.builder();
		result.add(Maps.immutableEntry(parseProduction(), ""));
		while (lexer.nextToken() == '|') {
			result.add(Maps.immutableEntry(parseProduction(), ""));
		}
		lexer.pushBack();
		return result.build();
	}

	private ImmutableList<String> parseProduction() throws IOException {
		Preconditions.checkState(lexer.ttype != StreamTokenizer.TT_EOF);
		final ImmutableList.Builder<String> productions = ImmutableList
				.builder();
		while (lexer.nextToken() == StreamTokenizer.TT_WORD
				|| Grammar.isQuote((char) lexer.ttype)) {
			if (Grammar.isQuote((char) lexer.ttype)) {
				if (lexer.sval.isEmpty()) {
					productions.add("\'" + Grammar.EPSILON + '\'');
				} else {
					productions.add('\'' + lexer.sval + '\'');
				}
			} else {
				productions.add(lexer.sval);
			}
		}
		lexer.pushBack();
		return productions.build();
	}

	private ImmutableListMultimap<String, Entry<ImmutableList<String>, String>> parseRule()
			throws ParserException, IOException {
		Preconditions.checkState(lexer.ttype != StreamTokenizer.TT_EOF);
		final ImmutableListMultimap.Builder<String, Entry<ImmutableList<String>, String>> alternatives = ImmutableListMultimap
				.builder();
		if (lexer.ttype == StreamTokenizer.TT_WORD) {
			final String nonTerminal = lexer.sval;
			if (lexer.nextToken() != '=') {
				throw new ParserException("Missing '=' in " + lexer.toString());
			}
			alternatives.putAll(nonTerminal, parseAlternatives());
			if (lexer.nextToken() != ';') {
				throw new ParserException("Missing ';' in " + lexer.toString());
			}
		} else {
			throw new ParserException("Missing non-terminal in "
					+ lexer.toString());
		}
		return alternatives.build();
	}
}
