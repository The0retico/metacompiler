package ebnf.lexer;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

public class LineAndColumnNumberReader extends LineNumberReader {

	private int markedColumn;

	private int columnNumber;

	public LineAndColumnNumberReader(final Reader reader) {
		super(reader);
		setLineNumber(1);
		setColumnNumber(0);
	}

	public final int getColumnNumber() {
		return columnNumber;
	}

	@Override
	public void mark(final int i) throws IOException {
		markedColumn = getColumnNumber();
		super.mark(i);
	}

	@Override
	public final int read() throws IOException {
		final int previousLine = getLineNumber();
		final int result = super.read();
		if (result != -1) {
			final int currentLine = getLineNumber();
			if (currentLine - previousLine == 1) {
				columnNumber = 0;
			} else {
				columnNumber++;
			}
		}
		return result;
	}

	@Override
	public final int read(final char[] ac, final int i, final int j)
			throws IOException {
		getLineNumber();
		final int readCharsNumber = super.read(ac, i, j);
		if (readCharsNumber != -1) {
			int columns = 0;
			for (int columnIndex = readCharsNumber - 1; columnIndex >= 0; columnIndex--) {
				if (Lexer.isNewLine(ac[columnIndex])) {
					columnNumber = 0;
					break;
				} else {
					columns++;
				}
			}
			columnNumber += columns;
		}
		return readCharsNumber;
	}

	@Override
	public String readLine() throws IOException {
		final String readResult = super.readLine();
		if (readResult != null) {
			columnNumber = 0;
		}
		return readResult;
	}

	@Override
	public void reset() throws IOException {
		setColumnNumber(markedColumn);
		super.reset();
	}

	private void setColumnNumber(final int column) {
		columnNumber = column;
	}

}
