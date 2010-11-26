package sk.scerbak.lambdainterpreter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import sk.scerbak.utility.StringUtilityTest;

/**
 * Test suite with all unit tests.
 * 
 * @author The0retico
 */
@RunWith(Suite.class)
@SuiteClasses(value = { StringUtilityTest.class, UnitTests.class,
		ParserTest.class, ParserFailureTest.class, AcceptanceTests.class,
		ComplexTests.class, DeBruijnPrinterTest.class })
public class AllTests {
};
