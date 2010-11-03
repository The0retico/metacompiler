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
		assertTrue(StringUtils.isInteger("123"));
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
		final String substring = StringUtils.substringOfWithin("(1(2)3)", '(',
				')');
		assertNotNull(substring);
		assertEquals("1(2)3", substring);
	}

	/**
	 * When opening or closing characters are not found, should throw exception.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testSubstringOfWithinCharsNotPresent() {
		StringUtils.substringOfWithin("123", '1', '4');
	}
}
