package ebnf.lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Lexer/scanner for the metagrammar language (EBNF).
 * 
 * @author sarvasmartin
 * 
 */
public class Lexer implements Iterator<IToken> {
	/**
	 * @param symbol
	 *            character to be tested if it is a newline character
	 * @return true if the symbol is a newline character, false otherwise
	 */
	public static boolean isNewLine(final char symbol) {
		return symbol == '\r' || symbol == '\n';
	}

	/**
	 * @param reader
	 *            TODO
	 * @return next character in the stream
	 * @throws IOException
	 *             if I/O error occours
	 */
	static int peek(final LineAndColumnNumberReader reader) throws IOException {
		reader.mark(1);
		final int result = reader.read();
		reader.reset();
		return result;
	}

	/**
	 * Streamed input Source of bytes for scanning tokens.
	 */
	private final LineAndColumnNumberReader input;

	/**
	 * Current scanned token from input.
	 */
	private IToken currentToken;

	/**
	 * Table of symbols, identifiers.
	 */
	private final Set<Identifier> identifiersTable;

	/**
	 * Construct a new lexer from a grammar in a file.
	 * 
	 * @param file
	 *            where is a grammar to be scanned.
	 * @throws FileNotFoundException
	 *             if file cannot be found
	 */
	public Lexer(final File file) throws FileNotFoundException {
		this(new FileReader(file));
	}

	/**
	 * Private constructor to be used uniformly for strings and files.
	 * 
	 * @param reader
	 *            to be used as a source for input.
	 */
	private Lexer(final Reader reader) {
		input = new LineAndColumnNumberReader(reader);
		identifiersTable = new HashSet<Identifier>();
	}

	/**
	 * @param inputText
	 *            text to be scanned
	 */
	public Lexer(final String inputText) {
		this(new StringReader(inputText));
	}

	/**
	 * @return true if the lexer position is at the end of input, false
	 *         otherwise
	 * @throws IOException
	 *             if I/O error occurs
	 */
	private boolean atEnd() throws IOException {
		return peek(input) == -1;
	}

	@Override
	public final boolean hasNext() {
		final boolean result;
		boolean atEnd = true;
		try {
			atEnd = atEnd();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		if (atEnd) {
			result = false;
		} else {
			try {
				currentToken = next();
			} catch (final NoSuchElementException e) {
				currentToken = null;
			}
			result = currentToken != null;
		}
		return result;
	}

	/**
	 * @return true if symbol is blank - whitespace or newline or comment, false
	 *         otherwise
	 * @throws IOException
	 *             if I/O error occours
	 */
	private boolean isBlank() throws IOException {
		return isWhiteSpace() || isNewLine((char) peek(input));
	}

	/**
	 * @return true if symbol is whitespace, false otherwise
	 * @throws IOException
	 *             if I/O error occours
	 */
	private boolean isWhiteSpace() throws IOException {
		final char currentSymbol = (char) peek(input);
		return currentSymbol == ' ' || currentSymbol == '\t';
	}

	@Override
	public final IToken next() {
		IToken result = null;
		try {
			skipBlankSymbols();
			if (currentToken != null) {
				result = currentToken;
				currentToken = null;
			} else if (Keyword.isNextIn(input)) {
				result = Keyword.scanFrom(input);
			} else if (Number.isNextIn(input)) {
				result = Number.scanFrom(input);
			} else if (Identifier.isNextIn(input)) {
				final Identifier identifier = Identifier.scanFrom(input);
				if (!identifiersTable.contains(result)) {
					identifiersTable.add(identifier);
				}
				result = identifier;
			} else if (Terminal.isNextIn(input)) {
				result = Terminal.scanFrom(input);
			} else if (Special.isNextIn(input)) {
				result = Special.scanFrom(input);
			} else {
				result = null;
				throw new NoSuchElementException(input.getLineNumber() + ":"
						+ input.getColumnNumber() + ":'" + (char) peek(input)
						+ "' Error! Does not start a token.");
			}
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final NoSuchElementException e) {
			throw e;
		}
		return result;
	}

	@Override
	public final void remove() {
		throw new UnsupportedOperationException(
				"Lexer does not support removing of tokens.");
	}

	/**
	 * Moves to the position in input until non-whitespace character is found.
	 * 
	 * @throws IOException
	 *             if I/O error occours
	 */
	private void skipBlankSymbols() throws IOException {
		while (!atEnd() && (isBlank() || startsComment())) {
			if (startsComment()) {
				skipComment();
			} else {
				input.skip(1);
			}
		}
	}

	/**
	 * Moves to the position in input to the end of comment.
	 * 
	 * @throws IOException
	 *             if I/O error occours
	 */
	private void skipComment() throws IOException {
		input.skip(2);
		int nextChar = input.read();
		while (nextChar != -1
				&& ((char) nextChar != '*' || (char) peek(input) != ')')) {
			nextChar = input.read();
		}
		input.skip(1);
	}

	/**
	 * @return true if next is a Comment, false otherwise
	 * @throws IOException
	 *             if I/O error occours
	 */
	private boolean startsComment() throws IOException {
		boolean result = false;
		final int commentTagsLength = 4;
		final char[] nextTwoChars = new char[commentTagsLength];
		input.mark(commentTagsLength);
		if (input.read(nextTwoChars, 0, commentTagsLength) == commentTagsLength) {
			result = nextTwoChars[0] == '(' && nextTwoChars[1] == '*';
		}
		input.reset();
		return result;
	}

}
