package sk.scerbak.utility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import sk.scerbak.utility.StringUtility.StringCase;

/**
 * Unit tests for string utilities.
 * 
 * @author The0retico
 * 
 */
public class StringUtilityTest {

	/**
	 * Fixture for this test.
	 */
	private final String fixture = "123";

	/**
	 * Empty strings are not integers.
	 */
	@Test
	public final void emptyStringIsNotInteger() {
		assertFalse("Empty string should not be an integer",
				StringUtility.isInteger(""));
	}

	/**
	 * Numbers are integers.
	 */
	@Test
	public final void numbersAreIntegers() {
		assertTrue(fixture + "should be an integer",
				StringUtility.isInteger(fixture));
	}

	/**
	 * Camel case should be considered undefined string case.
	 */
	@Test
	public final void camelCaseIsUndefined() {
		assertEquals("Camel case should be undefined", StringCase.UNDEFINED,
				StringUtility.caseOf("CamelCase"));
	}
}
