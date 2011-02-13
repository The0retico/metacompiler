package metacompiler;

import java.util.Map.Entry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

public class Production {
	private final Entry<ImmutableList<String>, String> production;

	public Production(final ImmutableList<String> sentenceForm,
			final String semantic) {
		production = Maps.immutableEntry(sentenceForm, semantic);
	}

	public final String getSemantic() {
		return production.getValue();
	}

	public final ImmutableList<String> getSymbols() {
		return production.getKey();
	}

	public final boolean isEpsilon() {
		return getSymbols().size() == 1
				&& getSymbols().get(0) == String.valueOf(Grammar.EPSILON);
	}

}
