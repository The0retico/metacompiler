package metacompiler;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.UnmodifiableIterator;

class Grammar implements Iterable<Entry<String, ImmutableList<String>>> {

	public static final Logger LOGGER = Logger.getLogger(Grammar.class
			.getName());

	public static final Character EOF = '$';

	public static final Character EPSILON = '\0';

	public static final Predicate<String> IS_TERMINAL = new Predicate<String>() {

		@Override
		public boolean apply(final String symbol) {
			Preconditions.checkNotNull(Strings.emptyToNull(symbol));
			final char first = symbol.charAt(0);
			final char last = symbol.charAt(symbol.length() - 1);
			return first == last && isQuote(first);

		}
	};

	public static final boolean isQuote(final char character) {
		return character == '\'' || character == '\"';
	}

	private final ImmutableListMultimap<String, ImmutableList<String>> rules;

	public Grammar(
			final ImmutableListMultimap<String, ImmutableList<String>> grammarRules) {
		Preconditions.checkNotNull(grammarRules);
		Preconditions.checkArgument(grammarRules.size() > 0, grammarRules);
		rules = grammarRules;
	}

	public ImmutableSet<Character> first(final ImmutableList<String> symbols) {
		Preconditions.checkNotNull(symbols);
		Preconditions.checkArgument(symbols.size() > 0);
		LOGGER.info(symbols.toString());
		final ImmutableSet.Builder<Character> result = ImmutableSet.builder();
		final UnmodifiableIterator<String> iterator = symbols.iterator();
		String currentSymbol;
		ImmutableSet<Character> firstOfCurrentSymbol;
		do {
			currentSymbol = iterator.next();
			firstOfCurrentSymbol = first(currentSymbol);
			result.addAll(Iterables.filter(firstOfCurrentSymbol,
					CharMatcher.isNot(EPSILON)));
		} while (iterator.hasNext() && firstOfCurrentSymbol.contains(EPSILON));
		if (!iterator.hasNext() && firstOfCurrentSymbol.contains(EPSILON)) {
			result.add(EPSILON);
		}
		return result.build();
	}

	private ImmutableSet<Character> first(final String symbol) {
		Preconditions.checkNotNull(Strings.emptyToNull(symbol));
		LOGGER.info(symbol);
		final ImmutableSet.Builder<Character> result = ImmutableSet.builder();
		if (IS_TERMINAL.apply(symbol)) {
			if ("\'\'".equals(symbol)) {
				result.add(EPSILON);
			} else {
				result.add(symbol.charAt(1));
			}
		} else {
			for (final ImmutableList<String> production : rules.get(symbol)) {
				if (production.size() == 1
						&& production.get(0) == String.valueOf(EPSILON)) {
					result.add(EPSILON);
				} else {
					Preconditions.checkNotNull(production);
					Preconditions.checkArgument(production.size() > 0);
					Preconditions.checkNotNull(result);
					final UnmodifiableIterator<String> iterator = production
							.iterator();
					String currentSymbol;
					ImmutableSet<Character> firstOfCurrentSymbol;
					do {
						currentSymbol = iterator.next();
						firstOfCurrentSymbol = first(currentSymbol);
						result.addAll(Iterables.filter(firstOfCurrentSymbol,
								CharMatcher.isNot(EPSILON)));
					} while (iterator.hasNext()
							&& firstOfCurrentSymbol.contains(EPSILON));
					if (!iterator.hasNext()
							&& firstOfCurrentSymbol.contains(EPSILON)) {
						result.add(EPSILON);
					}
				}
			}
		}
		return result.build();
	}

	public ImmutableSet<Character> follow(final String nonTerminal) {
		Preconditions.checkNotNull(nonTerminal);
		Preconditions.checkArgument(!IS_TERMINAL.apply(nonTerminal));
		LOGGER.warning(nonTerminal);
		final ImmutableSet.Builder<Character> result = ImmutableSet.builder();
		if (nonTerminal.equals(getStartSymbol())) {
			result.add(EOF);
		}
		final Predicate<Entry<String, ImmutableList<String>>> withNonTerminal = new Predicate<Entry<String, ImmutableList<String>>>() {

			@Override
			public boolean apply(
					final Entry<String, ImmutableList<String>> entry) {
				return entry.getValue().contains(nonTerminal);
			}
		};
		final Iterable<Entry<String, ImmutableList<String>>> productionsWithNonTerminal = Iterables
				.filter(rules.entries(), withNonTerminal);
		for (final Entry<String, ImmutableList<String>> production : productionsWithNonTerminal) {
			final ImmutableList<String> value = production.getValue();
			final ImmutableList<String> rest = value.subList(
					value.indexOf(nonTerminal) + 1, value.size());
			if (rest.size() > 0) {
				result.addAll(Iterables.filter(first(rest),
						CharMatcher.isNot(EPSILON)));
			}
			if ((rest.size() == 0 || first(rest).contains(EPSILON))
					&& nonTerminal != production.getKey()) {
				result.addAll(follow(production.getKey()));
			}
		}
		return result.build();
	}

	public String getStartSymbol() {
		return Iterables.getFirst(rules.entries(), null).getKey();
	}

	@Override
	public Iterator<Entry<String, ImmutableList<String>>> iterator() {
		return rules.entries().iterator();
	}

}
