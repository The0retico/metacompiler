package sk.scerbak.lambdainterpreter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import sk.scerbak.utils.StringUtilsTest;

/**
 * Test suite with all unit tests.
 * 
 * @author The0retico
 */
@RunWith(Suite.class)
@SuiteClasses(value = { StringUtilsTest.class, LambdaVariableTest.class,
		LambdaAbstractionTest.class, LambdaApplicationTest.class,
		LambdaConstantTest.class, LambdaIntegerTest.class,
		ParenthesisPipeSyntaxTest.class, AcceptanceTests.class })
public class AllTests {
};
