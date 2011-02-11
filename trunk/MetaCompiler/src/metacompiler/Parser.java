package metacompiler;

import java.io.IOException;
import java.io.Reader;
import java.util.Deque;
import java.util.Map.Entry;
import java.util.logging.Logger;

import util.LineColumnNumberReader;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

class Parser {

	public static final Logger LOGGER = Logger
			.getLogger(Parser.class.getName());

	public static Table<Character, String, Entry<ImmutableList<String>, String>> createTable(
			final Grammar grammar) {
		Preconditions.checkNotNull(grammar);
		LOGGER.info("start");
		final Table<Character, String, Entry<ImmutableList<String>, String>> result = HashBasedTable
				.create();
		for (final Entry<String, Entry<ImmutableList<String>, String>> rule : grammar) {
			LOGGER.info(rule.toString());
			for (final Character terminal : grammar.first(rule.getValue()
					.getKey())) {
				result.put(terminal, rule.getKey(), rule.getValue());
			}
			if (grammar.first(rule.getValue().getKey()).contains(
					Grammar.EPSILON)) {
				for (final Character terminal : grammar.follow(rule.getKey())) {
					result.put(terminal, rule.getKey(), rule.getValue());
				}
			}
		}
		LOGGER.info("end");
		return result;
	}

	private final Lexer lexer;
	private final Table<Character, String, Entry<ImmutableList<String>, String>> parsingTable;
	private final Deque<String> stack;

	Parser(final Grammar grammar) {
		Preconditions.checkNotNull(grammar);
		Preconditions.checkNotNull(grammar.getStartSymbol());
		lexer = new Lexer();
		parsingTable = createTable(grammar);
		stack = Lists.newLinkedList();
		stack.addFirst("\'" + Grammar.EOF + "\'");
		stack.addFirst(grammar.getStartSymbol());
	}

	public ImmutableList<Entry<String, Entry<ImmutableList<String>, String>>> analyze(
			final Reader reader) throws IOException, LexerException,
			ParserException {
		Preconditions.checkNotNull(reader);
		final ImmutableList.Builder<Entry<String, Entry<ImmutableList<String>, String>>> parseTree = ImmutableList
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

	private Entry<String, Entry<ImmutableList<String>, String>> reduce(
			final String nonTerminal, final LineColumnNumberReader input)
			throws IOException, ParserException {
		Preconditions.checkNotNull(nonTerminal);
		Preconditions.checkNotNull(input);
		final char lookahead = lexer.lookAhead(input);
		final Entry<ImmutableList<String>, String> production = parsingTable
				.get(lookahead, nonTerminal);
		if (production == null) {
			throwParsingException(nonTerminal, input, lookahead);
		}
		for (int index = production.getKey().size() - 1; index >= 0; index--) {
			final String symbol = production.getKey().get(index);
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
