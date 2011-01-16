package lexer.ebnf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the special token.
 * 
 * @author The0retico
 * 
 */
public class SpecialTest {

	/**
	 * Fixture for these tests.
	 */
	private IToken fixture;

	/**
	 * Test of Special sequence function equals and getValue.
	 */
	@Test
	public final void equals() {
		assertEquals(fixture, fixture);
		assertFalse(fixture.equals(new Identifier("sa", 1, 0)));
		assertFalse(fixture.equals(new Special("t", 1, 0)));
	}

	/**
	 * Prepare fixture for these tests.
	 */
	@Before
	public final void setUp() {
		fixture = new Special("s", 1, 0);
	}

	/**
	 * Test getValue method.
	 */
	@Test
	public final void value() {
		assertEquals("s", fixture.getValue());
	}

}
