package metacompiler;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.getFirst;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.base.CharMatcher;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;

class Grammar implements Iterable<Entry<String, Production>> {

	public static final Logger LOGGER = Logger.getLogger(Grammar.class
			.getName());

	private static final Set<Character> quotes = ImmutableSet.of('\'', '\"');

	public static final Character EOF = '$';

	public static final Character EPSILON = '\0';

	public static final Predicate<String> IS_TERMINAL = new Predicate<String>() {

		@Override
		public boolean apply(final String symbol) {
			checkNotNull(Strings.emptyToNull(symbol));
			final char first = symbol.charAt(0);
			final char last = symbol.charAt(symbol.length() - 1);
			return first == last && isQuote(first);

		}
	};

	public static final boolean isQuote(final char character) {
		return quotes.contains(character);
	}

	private final ImmutableListMultimap<String, Production> rules;

	public Grammar(final ImmutableListMultimap<String, Production> grammarRules) {
		checkNotNull(grammarRules);
		checkArgument(grammarRules.size() > 0, grammarRules);
		rules = grammarRules;
	}

	public ImmutableSet<Character> first(final ImmutableList<String> symbols) {
		checkNotNull(symbols);
		checkArgument(symbols.size() > 0);
		LOGGER.info(symbols.toString());
		final ImmutableSet.Builder<Character> result = ImmutableSet.builder();
		final UnmodifiableIterator<String> iterator = symbols.iterator();
		String symbol;
		ImmutableSet<Character> firstOfSymbol;
		boolean isLastSymbol;
		boolean firstOfSymbolContainsEpsilon;
		do {
			symbol = iterator.next();
			firstOfSymbol = first(symbol);
			firstOfSymbolContainsEpsilon = firstOfSymbol.contains(EPSILON);
			result.addAll(filter(firstOfSymbol, CharMatcher.isNot(EPSILON)));
			isLastSymbol = !iterator.hasNext();
		} while (!isLastSymbol && firstOfSymbolContainsEpsilon);
		final boolean lastContainedEpsilon = isLastSymbol
				&& firstOfSymbolContainsEpsilon;
		if (lastContainedEpsilon) {
			result.add(EPSILON);
		}
		return result.build();
	}

	private ImmutableSet<Character> first(final String symbol) {
		checkNotNull(Strings.emptyToNull(symbol));
		LOGGER.info(symbol);
		final ImmutableSet.Builder<Character> result = ImmutableSet.builder();
		if (IS_TERMINAL.apply(symbol)) {
			if ("\'\'".equals(symbol)) {
				result.add(EPSILON);
			} else {
				result.add(symbol.charAt(1));
			}
		} else {
			for (final Production production : rules.get(symbol)) {
				if (production.isEpsilon()) {
					result.add(EPSILON);
				} else {
					result.addAll(first(production.getSymbols()));
				}
			}
		}
		return result.build();
	}

	public ImmutableSet<Character> follow(final String nonTerminal) {
		checkNotNull(nonTerminal);
		checkArgument(!IS_TERMINAL.apply(nonTerminal));
		LOGGER.warning(nonTerminal);
		final ImmutableSet.Builder<Character> result = ImmutableSet.builder();
		if (nonTerminal.equals(getStartSymbol())) {
			result.add(EOF);
		}
		final Predicate<Entry<String, Production>> withNonTerminal = new Predicate<Entry<String, Production>>() {

			@Override
			public boolean apply(final Entry<String, Production> entry) {
				return entry.getValue().getSymbols().contains(nonTerminal);
			}
		};
		final Iterable<Entry<String, Production>> productionsWithNonTerminal = filter(
				rules.entries(), withNonTerminal);
		for (final Entry<String, Production> production : productionsWithNonTerminal) {
			final ImmutableList<String> value = production.getValue()
					.getSymbols();
			final ImmutableList<String> rest = value.subList(
					value.indexOf(nonTerminal) + 1, value.size());
			if (rest.size() > 0) {
				result.addAll(filter(first(rest), CharMatcher.isNot(EPSILON)));
			}
			if ((rest.size() == 0 || first(rest).contains(EPSILON))
					&& nonTerminal != production.getKey()) {
				result.addAll(follow(production.getKey()));
			}
		}
		return result.build();
	}

	public String getStartSymbol() {
		return getFirst(rules.entries(), null).getKey();
	}

	@Override
	public Iterator<Entry<String, Production>> iterator() {
		return rules.entries().iterator();
	}

}
