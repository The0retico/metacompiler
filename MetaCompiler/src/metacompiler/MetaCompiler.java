package metacompiler;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map.Entry;

import sk.scerbak.lambdainterpreter.Interpreter;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public final class MetaCompiler {

	public static ImmutableList<Entry<String, ImmutableList<String>>> compile(
			final Reader grammarDefinition, final Reader program)
			throws IOException, ParserException, LexerException {
		Preconditions.checkNotNull(grammarDefinition);
		Preconditions.checkNotNull(program);
		final Grammar grammar = BNFParser.analyze(grammarDefinition);
		final Parser parser = new Parser(grammar);
		final ImmutableList<Entry<String, ImmutableList<String>>> abstractSyntaxTree = parser
				.analyze(program);
		return abstractSyntaxTree;
	}

	/**
	 * @param abstractSyntaxTree
	 */
	public static void execute(
			final ImmutableList<Entry<String, ImmutableList<String>>> abstractSyntaxTree) {
		final String code = generate(abstractSyntaxTree);
		Interpreter.execute(code);
	}

	private static ImmutableList<String> fold(final List<String> head,
			final ImmutableList<Entry<String, ImmutableList<String>>> tail) {
		final int index = head.indexOf(tail.get(0).getKey());
		head.remove(index);
		head.addAll(index, tail.get(0).getValue());
		ImmutableList<String> result;
		if (tail.size() == 1) {
			result = ImmutableList.copyOf(head);
		} else {
			result = fold(head, tail.subList(1, tail.size()));
		}
		return result;
	}

	private static String generate(
			final ImmutableList<Entry<String, ImmutableList<String>>> abstractSyntaxTree) {
		Preconditions.checkNotNull(abstractSyntaxTree);
		final List<String> head = Lists.newLinkedList(abstractSyntaxTree.get(0)
				.getValue());
		final ImmutableList<Entry<String, ImmutableList<String>>> tail = abstractSyntaxTree
				.subList(1, abstractSyntaxTree.size());
		final ImmutableList<String> list = fold(head, tail);
		final StringBuilder result = new StringBuilder();
		for (final String entry : list) {
			String string = entry.substring(1, entry.length() - 1);
			if (string.contains("\0")) {
				string = "";
			}
			result.append(string);
		}
		return result.toString();
	}

	private MetaCompiler() {

	}

}
