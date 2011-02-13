package metacompiler;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.Reader;
import java.util.Deque;
import java.util.Map.Entry;
import java.util.logging.Logger;

import util.LineColumnNumberReader;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

class Parser {

	public static final Logger LOGGER = Logger
			.getLogger(Parser.class.getName());

	public static Table<Character, String, Production> createTable(
			final Grammar grammar) {
		checkNotNull(grammar);
		LOGGER.info("start");
		final Table<Character, String, Production> result = HashBasedTable
				.create();
		for (final Entry<String, Production> rule : grammar) {
			LOGGER.info(rule.toString());
			final String nonTerminal = rule.getKey();
			final Production production = rule.getValue();
			for (final Character character : grammar.first(production
					.getSymbols())) {
				result.put(character, nonTerminal, production);
			}
			if (grammar.first(production.getSymbols())
					.contains(Grammar.EPSILON)) {
				for (final Character character : grammar.follow(nonTerminal)) {
					result.put(character, nonTerminal, production);
				}
			}
		}
		LOGGER.info("end");
		return result;
	}

	private final Lexer lexer;
	private final Table<Character, String, Production> parsingTable;
	private final Deque<String> stack;

	Parser(final Grammar grammar) {
		checkNotNull(grammar);
		checkNotNull(grammar.getStartSymbol());
		lexer = new Lexer();
		parsingTable = createTable(grammar);
		stack = Lists.newLinkedList();
		stack.addFirst("\'" + Grammar.EOF + "\'");
		stack.addFirst(grammar.getStartSymbol());
	}

	public ImmutableList<Entry<String, Production>> analyze(final Reader reader)
			throws IOException, LexerException, ParserException {
		checkNotNull(reader);
		final ImmutableList.Builder<Entry<String, Production>> parseTree = ImmutableList
				.builder();
		final LineColumnNumberReader input = new LineColumnNumberReader(reader);
		while (!stack.isEmpty()) {
			LOGGER.warning(stack.toString());
			final String symbol = stack.removeFirst();
			if (Grammar.IS_TERMINAL.apply(symbol)) {
				if (lexer.match(symbol, input)) {
					LOGGER.warning("matched:" + symbol);
				}
			} else {
				parseTree.add(reduce(symbol, input));
			}
		}
		return parseTree.build();
	}

	private Entry<String, Production> reduce(final String nonTerminal,
			final LineColumnNumberReader input) throws IOException,
			ParserException {
		checkNotNull(nonTerminal);
		checkNotNull(input);
		final char lookahead = lexer.lookAhead(input);
		final Production production = parsingTable.get(lookahead, nonTerminal);
		if (production == null) {
			throwParsingException(nonTerminal, input, lookahead);
		}
		for (final String symbol : production.getSymbols().reverse()) {
			stack.addFirst(symbol);
		}
		LOGGER.warning("reduced:" + nonTerminal + "->" + production);
		return Maps.immutableEntry(nonTerminal, production);
	}

	private void throwParsingException(final String nonTerminal,
			final LineColumnNumberReader input, final char lookahead)
			throws ParserException {
		final String inputSateMesage = "Input - line:" + input.getLineNumber()
				+ ", column:" + input.getColumnNumber() + "\n";
		final String parserStateMessage = "Parser - lookahead:\'" + lookahead
				+ "\', symbol:[" + nonTerminal + "]\n";
		final String tableStateMessage = "Table:\n\t\'" + lookahead
				+ "\' lookahead entries:\n\t\t" + parsingTable.row(lookahead)
				+ "\n\t[" + nonTerminal + "] symbol entries:\n\t\t"
				+ parsingTable.column(nonTerminal);
		final String stateMesage = inputSateMesage + parserStateMessage
				+ tableStateMessage;
		throw new ParserException("Unexpected input!\n" + stateMesage);
	}
}
