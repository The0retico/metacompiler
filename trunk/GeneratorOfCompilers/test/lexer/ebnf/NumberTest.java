package lexer.ebnf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the number token.
 * 
 * @author The0retico
 * 
 */
public class NumberTest {

	/**
	 * Constant for creating the fixture.
	 */
	private static final Integer VALUE = 1234;

	/**
	 * Fixture object for these tests.
	 */
	private Number fixture;

	/**
	 * Test of Number function equals and getValue.
	 */
	@Test
	public final void equals() {
		assertEquals(fixture, fixture);
		assertEquals(fixture, new Number(VALUE));
		assertFalse(fixture.equals(VALUE));
		assertFalse(fixture.equals(new Number(VALUE + 1)));
	}

	/**
	 * Test the getLength method.
	 */
	@Test
	public final void length() {
		final Integer actualLength = fixture.getLength();
		final Integer expectedLength = VALUE.toString().length();
		assertNotNull(actualLength);
		assertNotNull(expectedLength);
		assertEquals(expectedLength, actualLength);
	}

	/**
	 * Set up fresh fixture before every test, so results of previous tests do
	 * not influence nexte tests.
	 */
	@Before
	public final void setUp() {
		fixture = new Number(VALUE);
	}

	/**
	 * Test the getValue function.
	 */
	@Test
	public final void value() {
		final String actualValue = fixture.getValue();
		assertNotNull(actualValue);
		assertEquals(VALUE.toString(), actualValue);
	}
}
