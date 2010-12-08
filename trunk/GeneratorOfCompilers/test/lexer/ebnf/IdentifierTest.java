package lexer.ebnf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the identifier token.
 * 
 * @author The0retico
 * 
 */
public class IdentifierTest {

	/**
	 * Fixture for these tests.
	 */
	private IToken fixture;

	/**
	 * Constant for the fixture.
	 */
	private static final String VALUE = "s";

	/**
	 * Test of Identifier function equals and getValue.
	 * 
	 * @throws Exception
	 */
	@Test
	public final void equals() {
		assertEquals(fixture, fixture);
		assertEquals(fixture, new Identifier("s"));
		assertFalse(fixture.equals(null));
		assertFalse(fixture.equals(new Terminal("sa")));
		assertFalse(fixture.equals(new Identifier(null)));
		assertFalse(fixture.equals(new Identifier("s1234")));
	}

	/**
	 * Prepare fixture for these tests.
	 */
	@Before
	public final void setUp() {
		fixture = new Identifier(VALUE);
	}

	/**
	 * Test the getValue method.
	 */
	@Test
	public final void value() {
		assertEquals("s", fixture.getValue());
	}

}
