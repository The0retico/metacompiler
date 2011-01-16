package lexer.ebnf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import lexer.ebnf.Keyword.Type;

/**
 * Lexer/scanner for the metagrammar language (EBNF).
 * 
 * @author sarvasmartin
 * 
 */
public class Lexer implements Iterator<IToken> {
	/**
	 * Streamed input Source of bytes for scanning tokens.
	 */
	private final LineNumberReader input;

	/**
	 * Current position of the scanner in the current line of input.
	 */
	private int currentColumn;

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
		input = new LineNumberReader(reader);
		input.setLineNumber(1);
		currentColumn = 1;
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
		return peek() == -1;
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
		return isWhiteSpace() || isNewLine();
	}

	/**
	 * @return true if next token is an identifier, false otherwise.
	 * @throws IOException
	 *             if I/O error occours
	 */
	private boolean isIdentifierNextToken() throws IOException {
		final char nextSymbol = (char) peek();
		return Character.isJavaIdentifierStart(nextSymbol);
	}

	/**
	 * @return true if next token is a keyword, false otherwise
	 * @throws IOException
	 *             if I/O error occours
	 */
	private boolean isKeywordNextToken() throws IOException {
		final Type[] keywordTypes = Keyword.values();
		Arrays.sort(keywordTypes, new Comparator<Type>() {

			@Override
			public int compare(final Type first, final Type second) {
				return second.getLength() - first.getLength();
			}
		});
		boolean found = false;
		for (int index = 0; index < keywordTypes.length && !found; index++) {
			final Type keywordType = keywordTypes[index];
			final int keywordLength = keywordType.getLength();
			input.mark(keywordLength);
			final char[] nextToken = new char[keywordLength];
			if (input.read(nextToken, 0, keywordLength) == keywordLength) {
				found = keywordType.getValue()
						.equals(String.valueOf(nextToken));
			}
			input.reset();
		}
		return found;
	}

	/**
	 * @return true if the symbol si a newline character, false otherwise
	 * @throws IOException
	 *             if I/O error occours
	 */
	private boolean isNewLine() throws IOException {
		final char currentSymbol = (char) peek();
		return currentSymbol == '\r' || currentSymbol == '\n';
	}

	/**
	 * @return true if next token is a number, false otherwise
	 * @throws IOException
	 *             if I/O error occours
	 */
	private boolean isNumberNextToken() throws IOException {
		final char nextSymbol = (char) peek();
		return Character.isDigit(nextSymbol);
	}

	/**
	 * @return true if next token is a Special sequence string, false otherwise
	 * @throws IOException
	 *             if I/O error occours
	 */
	private boolean isSpecialNextToken() throws IOException {
		final char nextSymbol = (char) peek();
		return nextSymbol == '?';
	}

	/**
	 * @return true if next token is a Terminal string, false otherwise
	 * @throws IOException
	 *             if I/O error occours
	 */
	private boolean isTerminalNextToken() throws IOException {
		final char currentSymbol = (char) peek();
		return currentSymbol == '\'' || currentSymbol == '"';
	}

	/**
	 * @return true if symbol is whitespace, false otherwise
	 * @throws IOException
	 *             if I/O error occours
	 */
	private boolean isWhiteSpace() throws IOException {
		final char currentSymbol = (char) peek();
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
			} else if (isKeywordNextToken()) {
				result = scanKeyword();
			} else if (isNumberNextToken()) {
				result = scanNumber();
			} else if (isIdentifierNextToken()) {
				result = scanIdentifier();
			} else if (isTerminalNextToken()) {
				result = scanTerminal();
			} else if (isSpecialNextToken()) {
				result = scanSpecial();
			} else {
				result = null;
				throw new NoSuchElementException(input.getLineNumber() + ":"
						+ currentColumn + ":'" + (char) peek()
						+ "' Error! Does not start a token.");
			}
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final NoSuchElementException e) {
			throw e;
		}
		return result;
	}

	/**
	 * @return next character in the stream
	 * @throws IOException
	 *             if I/O error occours
	 */
	private int peek() throws IOException {
		input.mark(1);
		final int result = input.read();
		input.reset();
		return result;
	}

	@Override
	public final void remove() {
		throw new UnsupportedOperationException(
				"Lexer does not support removing of tokens.");
	}

	/**
	 * @return next scanned identifier token
	 * @throws IOException
	 *             if I/O error occours
	 */
	private IToken scanIdentifier() throws IOException {
		int nextChar = (char) input.read();
		final StringBuilder value = new StringBuilder();
		while (nextChar != -1 && Character.isJavaIdentifierPart(nextChar)) {
			value.append((char) nextChar);
			nextChar = input.read();
		}

		final Identifier result = new Identifier(value.toString(),
				input.getLineNumber(), currentColumn);
		if (!identifiersTable.contains(result)) {
			identifiersTable.add(result);
		}
		currentColumn += result.getLength();
		return result;
	}

	/**
	 * @return a keyword as a next token
	 * @throws IOException
	 *             if I/O error occours
	 */
	private IToken scanKeyword() throws IOException {
		final Type[] keywordTypes = Keyword.values();
		Type result = null;
		int index = 0;
		for (; index < keywordTypes.length && result == null; index++) {
			final Type keywordType = keywordTypes[index];
			final int keywordLength = keywordType.getLength();
			input.mark(keywordLength);
			final char[] nextToken = new char[keywordLength];
			if (input.read(nextToken, 0, keywordLength) == keywordLength) {
				if (keywordType.getValue().equals(String.valueOf(nextToken))) {
					result = keywordType;
				} else {
					input.reset();
				}
			}
		}
		currentColumn += result.getLength();
		return new Keyword(result, input.getLineNumber(), currentColumn);
	}

	/**
	 * @return number as the next token.
	 * @throws IOException
	 *             if I/O error occours
	 */
	private IToken scanNumber() throws IOException {
		int nextChar = input.read();
		final StringBuilder nextToken = new StringBuilder();
		while (nextChar != -1 && Character.isDigit((char) nextChar)) {
			nextToken.append((char) nextChar);
			nextChar = input.read();
		}
		final int intValue = Integer.parseInt(nextToken.toString());
		final Number result = new Number(intValue, input.getLineNumber(),
				currentColumn);
		currentColumn += result.getLength();
		return result;
	}

	/**
	 * @return Special sequence string as the next token.
	 * @throws IOException
	 *             if I/O error occours
	 */
	private IToken scanSpecial() throws IOException {
		input.skip(1);
		int nextChar = input.read();
		final StringBuilder nextToken = new StringBuilder();
		while (nextChar != -1 && (char) nextChar != '?') {
			if (isNewLine()) {
				currentColumn = 1;
			}
			nextToken.append((char) nextChar);
			nextChar = input.read();
		}
		final Special result = new Special(nextToken.toString(),
				input.getLineNumber(), currentColumn);
		currentColumn += result.getLength() + 2;
		return result;
	}

	/**
	 * @return Terminal string as the next token.
	 * @throws IOException
	 *             if I/O error occours
	 */
	private IToken scanTerminal() throws IOException {
		final char quote = (char) input.read();
		int nextChar = input.read();
		final StringBuilder nextToken = new StringBuilder();
		while (nextChar != -1 && nextChar != quote) {
			if (isNewLine()) {
				currentColumn = 1;
			}
			nextToken.append((char) nextChar);
			nextChar = input.read();
		}
		final Terminal result = new Terminal(nextToken.toString(),
				input.getLineNumber(), currentColumn);
		currentColumn += result.getLength() + 2;
		return result;
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
			} else if (isNewLine()) {
				currentColumn = 1;
				input.skip(1);
			} else {
				currentColumn++;
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
				&& ((char) nextChar != '*' || (char) peek() != ')')) {
			if (isNewLine()) {
				currentColumn = 1;
			}
			nextChar = input.read();
			currentColumn++;
		}
		input.skip(1);
		currentColumn++;
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
