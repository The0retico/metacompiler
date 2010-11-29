package sk.scerbak.lambdainterpreter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Unit tests for the model of the lambda calculus.
 * 
 * @author The0retico
 * 
 */
@RunWith(Suite.class)
@SuiteClasses(value = { VariableTest.class, AbstractionTest.class,
		ApplicationTest.class })
public class UnitTests {

}
