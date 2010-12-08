package lexer.ebnf;

import java.util.HashSet;
import java.util.Set;

/**
 * Lexer/scanner for the metagrammar language (EBNF).
 * 
 * @author sarvasmartin
 * 
 */
public class Lexer {
	/**
	 * Text to be scanned for tokens.
	 */
	private final String input;

	/**
	 * Current position of the scanner in the current line of input.
	 */
	private int currentRow;

	/**
	 * Current index position in the input.
	 */
	private int position;

	/**
	 * Current scanned token from input.
	 */
	private IToken currentToken;

	/**
	 * Counter for current line.
	 */
	private int currentLine;

	/**
	 * Table of symbols, identifiers.
	 */
	private Set<Identifier> identifiersTable;

	/**
	 * @param inputText
	 *            text to be scanned
	 */
	public Lexer(final String inputText) {
		input = inputText;
		currentLine = 1;
		currentRow = 1;
		position = 0;
		identifiersTable = new HashSet<Identifier>();
	}

	/**
	 * @return next token in the inputText
	 * @throws Exception
	 *             if there is not a valid token
	 */
	public final IToken getNextToken() throws Exception {
		skipBlankSymbols();
		if (isKeywordNextToken()) {
			currentToken = scanKeyword();
		} else if (isNumberNextToken()) {
			currentToken = scanNumber();
		} else if (isIdentifierNextToken()) {
			currentToken = scanIdentifier();
		} else if (isTerminalNextToken()) {
			currentToken = scanTerminal();
		} else if (isSpecialNextToken()) {
			currentToken = scanSpecial();
		} else {
			throw new Exception(currentLine + ":" + currentRow + ":'"
					+ input.charAt(position)
					+ "' Error! Does not start a token.");
		}
		return currentToken;
	}

	/**
	 * @return true if the inputText contains another Token, false otherwise
	 */
	public final boolean hasNextToken() {
		skipBlankSymbols();
		return position < input.length();
	}

	/**
	 * @param symbol
	 *            to be checked if it is whitespace or newline or comment
	 * @return true if symbol is blank - whitespace or newline or comment, false
	 *         otherwise
	 */
	private boolean isBlank(final char symbol) {
		return isWhiteSpace(symbol) || isNewLine(symbol) || isComment();
	}

	/**
	 * @return true if next token is an identifier, false otherwise.
	 */
	private boolean isIdentifierNextToken() {
		final char nextSymbol = input.charAt(position);
		return Character.isJavaIdentifierStart(nextSymbol);
	}

	/**
	 * @return true if next token is a keyword, false otherwise
	 */
	private boolean isKeywordNextToken() {
		final Keyword[] keywords = Keyword.values();
		boolean found = false;
		for (int index = 0; index < keywords.length && !found; index++) {
			final Keyword keyword = keywords[index];
			final int end = position - 1 + keyword.getLength();
			if (end < input.length()) {
				final String nextToken = input.substring(position, end + 1);
				found = nextToken.equals(keyword.getValue());
			}
		}
		return found;
	}

	/**
	 * @return true if next token is a Terminal string, false otherwise
	 */
	private boolean isTerminalNextToken() {
		final char nextSymbol = input.charAt(position);
		return nextSymbol == '\'' || nextSymbol == '"';
	}

	/**
	 * @return true if next token is a Special sequence string, false otherwise
	 */
	private boolean isSpecialNextToken() {
		final char nextSymbol = input.charAt(position);
		return nextSymbol == '?';
	}

	/**
	 * @return true if next is a Comment, false otherwise
	 */
	private boolean isComment() {
		final char nextFirstSymbol = input.charAt(position);
		return nextFirstSymbol == '(' && input.length() > position + 1
				&& input.charAt(position + 1) == '*';
	}

	/**
	 * @param symbol
	 *            to be checked if it is a new line character
	 * @return true if the symbol si a newline character, false otherwise
	 */
	private boolean isNewLine(final char symbol) {
		return symbol == '\r' || symbol == '\n';
	}

	/**
	 * @return true if next token is a number, false otherwise
	 */
	private boolean isNumberNextToken() {
		final char nextSymbol = input.charAt(position);
		return Character.isDigit(nextSymbol);
	}

	/**
	 * @param symbol
	 *            to be checked if it is a whitespace
	 * @return true if symbol is whitespace, false otherwise
	 */
	private boolean isWhiteSpace(final char symbol) {
		return symbol == ' ' || symbol == '\t';
	}

	/**
	 * @return next scanned identifier token
	 */
	private IToken scanIdentifier() {
		int end = position + 1;
		while (end < input.length()
				&& Character.isJavaIdentifierPart(input.charAt(end))) {
			end++;
		}
		final String name = input.substring(position, end);

		final Identifier result = new Identifier(name);
		if (!identifiersTable.contains(result)) {
			identifiersTable.add(result);
		}
		currentRow += result.getLength();
		position += result.getLength();
		return result;
	}

	/**
	 * @return a keyword as a next token
	 */
	private IToken scanKeyword() {
		final Keyword[] keywords = Keyword.values();
		Keyword result = null;
		int index = 0;
		for (; index < keywords.length && result == null; index++) {
			final Keyword keyword = keywords[index];
			final int end = position - 1 + keyword.getLength();
			if (end < input.length()) {
				final String nextToken = input.substring(position, end + 1);
				if (nextToken.equals(keyword.getValue())) {
					result = keyword;
				}
			}
		}
		currentRow += result.getLength();
		position += result.getLength();
		return result;
	}

	/**
	 * @return number as the next token.
	 */
	private IToken scanNumber() {
		int end = position + 1;
		while (end < input.length() && Character.isDigit(input.charAt(end))) {
			end++;
		}
		final String nextToken = input.substring(position, end);
		final Number result = new Number(Integer.parseInt(nextToken));
		currentRow += result.getLength();
		position += result.getLength();
		return result;
	}

	/**
	 * @return Terminal string as the next token.
	 */
	private IToken scanTerminal() {
		int end = position + 1;
		char quote = input.charAt(position);
		while (end < input.length() && input.charAt(end) != quote) {
			if (isNewLine(input.charAt(end))) {
				currentLine++;
				currentRow = 1;
			}
			end++;
		}
		final String nextToken = input.substring(position + 1, end);
		final Terminal result = new Terminal(nextToken);
		currentRow += result.getLength() + 2;
		position += result.getLength() + 2;
		return result;
	}

	/**
	 * @return Special sequence string as the next token.
	 */
	private IToken scanSpecial() {
		int end = position + 1;
		while (end < input.length() && input.charAt(end) != '?') {
			if (isNewLine(input.charAt(end))) {
				currentLine++;
				currentRow = 1;
			}
			end++;
		}
		final String nextToken = input.substring(position + 1, end);
		final Special result = new Special(nextToken);
		currentRow += result.getLength() + 2;
		position += result.getLength() + 2;
		return result;
	}

	/**
	 * Moves to the position in input to the end of comment.
	 */
	private void skipComment() {
		int end = position + 2;
		while (end + 1 < input.length()
				&& (input.charAt(end) != '*' || input.charAt(end + 1) != ')')) {
			if (isNewLine(input.charAt(end))) {
				currentLine++;
				currentRow = 1;
			}
			end++;
		}
		currentRow += end - position + 2;
		position += end - position + 1;
	}

	/**
	 * Moves to the position in input until non-whitespace character is found.
	 */
	private void skipBlankSymbols() {
		while (position < input.length() && isBlank(input.charAt(position))) {
			if (isNewLine(input.charAt(position))) {
				currentLine++;
				currentRow = 1;
			} else if (isComment()) {
				skipComment();
			} else {
				currentRow++;
			}
			position++;
		}
	}
}
