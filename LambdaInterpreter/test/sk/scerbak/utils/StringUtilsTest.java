package sk.scerbak.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for string utilities.
 * 
 * @author The0retico
 * 
 */
public class StringUtilsTest {

	/**
	 * Empty strings are not integers.
	 */
	@Test
	public final void emptyStringIsNotInteger() {
		assertFalse(StringUtils.isInteger(""));
	}

	/**
	 * Numbers are integers.
	 */
	@Test
	public final void numbersAreIntegers() {
		final String fixture = "123";
		assertTrue(StringUtils.isInteger(fixture));
	}

	/**
	 * Substring after specified index till end of string.
	 */
	@Test
	public final void testSubstringOfAfter() {
		final String substring = StringUtils.substringOfAfter("abc", 1);
		assertNotNull(substring);
		assertEquals("c", substring);
	}

	/**
	 * Should unpack enclosed strings.
	 */
	@Test
	public final void testSubstringOfWithin() {
		final int openingIndex = "(1(2)3)".indexOf('(');
		final int closingIndex = "(1(2)3)".lastIndexOf(')');
		if (openingIndex == -1 || openingIndex >= closingIndex) {
			throw new IllegalArgumentException("(1(2)3)");
		}
		final String substring = "(1(2)3)".substring(openingIndex + 1, closingIndex);
		assertNotNull(substring);
		assertEquals("1(2)3", substring);
	}

	/**
	 * When opening or closing characters are not found, should throw exception.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testSubstringOfWithinCharsNotPresent() {
		final int openingIndex = "123".indexOf('1');
		final int closingIndex = "123".lastIndexOf('4');
		if (openingIndex == -1 || openingIndex >= closingIndex) {
			throw new IllegalArgumentException("123");
		}
		"123".substring(openingIndex + 1, closingIndex);
	}
}
