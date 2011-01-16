package lexer.ebnf;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All tests.;
 * 
 * @author sarvasmartin
 * 
 */
@RunWith(Suite.class)
@SuiteClasses(value = { NumberTest.class, IdentifierTest.class,
		SpecialTest.class, TerminalTest.class,
		LineAndColumnNumberReaderTest.class, LexerTest.class,
		LexerFailureTest.class })
public final class AllTests {
};