package sk.scerbak.lambdainterpreter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author The0retico Test suite with all unit tests.
 */
@RunWith(Suite.class)
@SuiteClasses(value = { EvaluateTest.class, FromStringTest.class })
public class AllTests {
}
