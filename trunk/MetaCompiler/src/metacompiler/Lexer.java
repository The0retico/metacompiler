package metacompiler;

import java.io.IOException;
import java.io.Reader;

import util.LineColumnNumberReader;

import com.google.common.base.Preconditions;

class Lexer {

	public char lookAhead(final Reader input) throws IOException {
		Preconditions.checkNotNull(input);
		Preconditions.checkArgument(input.markSupported(), input);
		input.mark(1);
		final int result = input.read();
		input.reset();
		return result == -1 ? Grammar.EOF : (char) result;
	}

	public boolean match(final String terminal,
			final LineColumnNumberReader input) throws IOException,
			LexerException {
		boolean result = false;
		if (("\'" + Grammar.EPSILON + "\'").equals(terminal)) {
			result = true;
		} else if (("\'" + Grammar.EOF + "\'").equals(terminal)) {
			result = input.read() == -1;
		} else {
			for (int i = 1; i < terminal.length() - 1; i++) {
				final char character = (char) input.read();
				if (terminal.charAt(i) != character) {
					throw new LexerException("Match error!\n\texpected:\'"
							+ terminal.charAt(i) + "\'\n\tactual:\'"
							+ character + "\'\n\tline:" + input.getLineNumber()
							+ ", column:" + input.getColumnNumber()
							+ "\n\tmatching:\"" + terminal + "\"");
				}
			}
			result = true;
		}
		return result;
	}
}
