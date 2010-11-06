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
@SuiteClasses(value = { StringUtilityTest.class, VariableTest.class,
		AbstractionTest.class, ApplicationTest.class,
		ConstantTest.class, NaturalTest.class,
		ParserTest.class, AcceptanceTests.class })
public class AllTests {
};
