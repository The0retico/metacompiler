package metacompiler;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.Reader;
import java.util.Map.Entry;
import java.util.logging.Logger;

import sk.scerbak.lambdainterpreter.Interpreter;

import com.google.common.collect.ImmutableList;

public final class MetaCompiler {

	public static final Logger LOGGER = Logger.getLogger(MetaCompiler.class
			.getName());

	public static ImmutableList<Entry<String, Production>> compile(
			final Reader grammarDefinition, final Reader program)
			throws IOException, ParserException, LexerException {
		checkNotNull(grammarDefinition);
		checkNotNull(program);
		// TODO bootstrap - change production to entry production and semantics,
		// create bnf generator in Grammar
		// final Grammar bnfGrammar = Grammar.bnfGrammar();
		// final Parser bnfParser = new Parser(bnfGrammar);
		// final
		// ImmutableList<Entry<String,Entry<ImmutableList<String>,String>>>
		// bnfAST = bnfParser
		// .analyze(grammarDefinition);
		// final Grammar grammar = Grammar.generate(bnfAST);
		final Grammar grammar = GrammarParser.analyze(grammarDefinition);
		LOGGER.warning("BNF grammar parsed.");
		final Parser parser = new Parser(grammar);
		LOGGER.warning("Parser generated.");
		final ImmutableList<Entry<String, Production>> programAST = parser
				.analyze(program);
		LOGGER.warning("Program parsed.");
		return programAST;
	}

	public static String execute(
			final ImmutableList<Entry<String, Production>> result) {
		final String code = generate(result);
		LOGGER.info("Generated code:\n" + code);
		return Interpreter.execute(code);
	}

	private static String generate(
			final ImmutableList<Entry<String, Production>> derivations) {
		checkNotNull(derivations);
		final StringBuilder result = new StringBuilder();
		result.append("(I ");
		for (final Entry<String, Production> entry : derivations) {
			if (!entry.getValue().getSemantic()
					.contains(String.valueOf(Grammar.EPSILON))
					&& !entry.getValue().getSemantic().isEmpty()) {
				result.append(entry.getValue().getSemantic() + " ");
			}
		}
		result.replace(result.length() - 1, result.length(), ")");
		return result.toString();
	}

	private MetaCompiler() {

	}

}
