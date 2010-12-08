package lexer.ebnf;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import lexer.ebnf.LexerTest;
import lexer.ebnf.LexerTest2;

@RunWith(Suite.class)
@SuiteClasses(value = { LexerTest.class, LexerTest2.class })
public class AllTests {
};