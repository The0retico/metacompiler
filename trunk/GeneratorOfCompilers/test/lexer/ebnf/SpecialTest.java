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
		assertFalse(fixture.equals(null));
		assertFalse(fixture.equals(new Identifier("sa")));
		assertFalse(fixture.equals(new Special("t")));
	}

	/**
	 * Prepare fixture for these tests.
	 */
	@Before
	public final void setUp() {
		fixture = new Special("s");
	}

	/**
	 * Test getValue method.
	 */
	@Test
	public final void value() {
		assertEquals("s", fixture.getValue());
	}

}
