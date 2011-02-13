package metacompiler;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;

class GrammarParser {

	private static final char SEPARATOR_SYMBOL = ';';
	private static final char DEFINITION_SYMBOL = '=';
	private static final char SEMANTIC_SYMBOL = '?';

	public static Grammar analyze(final Reader input) throws IOException,
			ParserException {
		checkNotNull(input);
		final StreamTokenizer lexer = createBNFLexer(input);
		final GrammarParser parser = new GrammarParser(lexer);
		return parser.generate();
	}

	private static StreamTokenizer createBNFLexer(final Reader input) {
		checkNotNull(input);
		final StreamTokenizer lexer = new StreamTokenizer(input);
		lexer.eolIsSignificant(false);
		lexer.lowerCaseMode(false);
		lexer.slashSlashComments(false);
		lexer.slashStarComments(false);
		lexer.whitespaceChars(0, 'A' - 1);
		lexer.whitespaceChars('Z' + 1, 'a' - 1);
		lexer.whitespaceChars('z' + 1, 0xFF);
		lexer.wordChars('a', 'z');
		lexer.wordChars('A', 'Z');
		lexer.ordinaryChar(DEFINITION_SYMBOL);
		lexer.ordinaryChar(ALTERNATIVE_SYMBOL);
		lexer.ordinaryChar(SEPARATOR_SYMBOL);
		lexer.quoteChar('\'');
		lexer.quoteChar('\"');
		lexer.quoteChar(SEMANTIC_SYMBOL);
		return lexer;
	}

	private final StreamTokenizer lexer;
	private static final char ALTERNATIVE_SYMBOL = '|';

	GrammarParser(final StreamTokenizer metaGrammarLexer) {
		checkNotNull(metaGrammarLexer);
		lexer = metaGrammarLexer;
	}

	private Grammar generate() throws IOException, ParserException {
		final ImmutableListMultimap.Builder<String, Production> result = ImmutableListMultimap
				.builder();
		while (lexer.nextToken() != StreamTokenizer.TT_EOF) {
			result.putAll(parseRule());
		}
		return new Grammar(result.build());
	}

	private ImmutableList<Production> parseAlternatives() throws IOException {
		final ImmutableList.Builder<Production> result = ImmutableList
				.builder();
		ImmutableList<String> sentenceForm;
		String semantic = "";
		do {
			sentenceForm = parseProduction();
			if (lexer.nextToken() == SEMANTIC_SYMBOL) {
				semantic = lexer.sval;
			} else {
				lexer.pushBack();
			}
			result.add(new Production(sentenceForm, semantic));
		} while (lexer.nextToken() == ALTERNATIVE_SYMBOL);
		lexer.pushBack();
		return result.build();
	}

	private ImmutableList<String> parseProduction() throws IOException {
		checkState(lexer.ttype != StreamTokenizer.TT_EOF);
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

	private ImmutableListMultimap<String, Production> parseRule()
			throws ParserException, IOException {
		checkState(lexer.ttype != StreamTokenizer.TT_EOF);
		final ImmutableListMultimap.Builder<String, Production> alternatives = ImmutableListMultimap
				.builder();
		if (lexer.ttype == StreamTokenizer.TT_WORD) {
			final String nonTerminal = lexer.sval;
			if (lexer.nextToken() != DEFINITION_SYMBOL) {
				throw new ParserException("Missing \'" + DEFINITION_SYMBOL
						+ "\' in " + lexer.toString());
			}
			alternatives.putAll(nonTerminal, parseAlternatives());
			if (lexer.nextToken() != SEPARATOR_SYMBOL) {
				throw new ParserException("Missing \'" + SEPARATOR_SYMBOL
						+ "\' in " + lexer.toString());
			}
		} else {
			throw new ParserException("Missing non-terminal in "
					+ lexer.toString());
		}
		return alternatives.build();
	}
}
