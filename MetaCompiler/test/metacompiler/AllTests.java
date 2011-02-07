package metacompiler;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = { BNFParserTest.class, LexerTest.class,
		GrammarTest.class, ParserTest.class })
public class AllTests {

}
