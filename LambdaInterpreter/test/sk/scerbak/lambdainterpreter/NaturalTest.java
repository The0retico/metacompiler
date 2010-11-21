package sk.scerbak.lambdainterpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static sk.scerbak.lambdainterpreter.Assertions.assertNormalizes;
import static sk.scerbak.lambdainterpreter.Assertions.assertNotFree;
import static sk.scerbak.lambdainterpreter.Assertions.assertReduces;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for lambda integers.
 * 
 * @author The0retico
 * 
 */
public class NaturalTest {

	/**
	 * Fixture for this test.
	 */
	private IExpression fixture;

	/**
	 * Constant integer value for fixture.
	 */
	private final Integer value = 1;

	/**
	 * Integers are constants as well, so they bind no variables.
	 */
	@Test
	public final void hasNoFreeVariables() {
		assertNotFree("x", fixture);
	}

	/**
	 * Integers are not compound, so they have one subterm, itself.
	 */
	@Test
	public final void isItsOwnSubterm() {
		final List<IExpression> subterms = fixture.subterm();
		assertNotNull(fixture + " should contain subterms", subterms);
		assertEquals(1, subterms.size());
		assertEquals(fixture, subterms.get(0));
	}

	/**
	 * Natural cannot be negative.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void naturalIsNotNegative() {
		new Natural(-1);
	}

	/**
	 * Integers cannot be reduced using beta reduction, so they are in normal
	 * form.
	 */
	@Test
	public final void naturalsAreInNormalForm() {
		assertNormalizes(fixture, fixture);
	}

	/**
	 * Integers are not substituted.
	 */
	@Test
	public final void naturalsAreNotSubstituted() {
		final IExpression substituted = fixture.substitute(value.toString(),
				new Mock("M"));
		assertEquals(fixture.toString(), substituted.toString());
	}

	/**
	 * Integers cannot be reduced using beta reduction.
	 */
	@Test
	public final void naturalsAreReducedToThemselves() {
		assertReduces(fixture, fixture);
	}

	/**
	 * Prepare fixture for this tests.
	 */
	@Before
	public final void setUp() {
		fixture = new Natural(value);
	}

	/**
	 * Integers are printed as numbers.
	 */
	@Test
	public final void toStringIsValueToString() {
		assertEquals(String.valueOf(value), Printer.toString(fixture));
	}
}
