package sk.scerbak.lambdainterpreter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite with all unit tests.
 * 
 * @author The0retico
 */
@RunWith(Suite.class)
@SuiteClasses(value = { LambdaVariableTest.class, LambdaAbstractionTest.class })
public class AllTests {
}
