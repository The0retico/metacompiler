package lexer.ebnf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import lexer.ebnf.Keyword.Type;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Unit tests for the lexer of the metagrammar langauge (EBNF).
 * 
 * @author sarvasmartin
 * 
 */
@RunWith(Parameterized.class)
public class LexerFileTest {

	/**
	 * @return pairs of path and arrays of respective tokens as parameters for
	 *         this test
	 */
	@Parameters
	public static final Collection<Object[]> symbols() {
		final Object[][] parameters = new Object[][] {
				{
						"grammars/EBNF.ebnf",
						new IToken[] {
								new Identifier("EBNFGrammar", 1, 0), // EBNFGrammar
								new Keyword(Type.DEFINITION, 1, 12), // =
								new Keyword(Type.LEFT_REPETITION, 1, 14), // {
								new Identifier("Rule", 1, 15), // Rule
								new Keyword(Type.RIGHT_REPETITION, 1, 19), // }
								new Keyword(Type.TERMINATION, 1, 20), // ;

								new Identifier("Rule", 2, 0), // Rule
								new Keyword(Type.DEFINITION, 2, 5), // =
								new Identifier("NonTerminal", 2, 7), // NonTerminal
								new Keyword(Type.CONCATENATION, 2, 18), // ,
								new Terminal("=", 2, 20), // '='
								new Keyword(Type.CONCATENATION, 2, 23), // ,
								new Identifier("Expression", 2, 25), // Expression
								new Keyword(Type.CONCATENATION, 2, 35), // ,
								new Terminal(";", 2, 37), // ';'
								new Keyword(Type.TERMINATION, 2, 40), // ;

								new Identifier("Expression", 3, 0), // Expression
								new Keyword(Type.DEFINITION, 3, 11), // =
								new Identifier("Term", 3, 13), // Term
								new Keyword(Type.CONCATENATION, 3, 17), // ,
								new Keyword(Type.LEFT_REPETITION, 3, 19), // {
								new Terminal("|", 3, 20), // '|'
								new Identifier("Term", 3, 24), // Term
								new Keyword(Type.RIGHT_REPETITION, 3, 28), // }
								new Keyword(Type.TERMINATION, 3, 29), // ;

								new Identifier("Term", 4, 0), // Term
								new Keyword(Type.DEFINITION, 4, 5), // =
								new Identifier("Factor", 4, 7), // Factor
								new Keyword(Type.CONCATENATION, 4, 13), // ,
								new Keyword(Type.LEFT_REPETITION, 4, 15), // {
								new Terminal(",", 4, 16), // ','
								new Identifier("Factor", 4, 20), // Factor
								new Keyword(Type.RIGHT_REPETITION, 4, 26), // }
								new Keyword(Type.TERMINATION, 4, 27), // ;

								new Identifier("Factor", 5, 0), // Factor
								new Keyword(Type.DEFINITION, 5, 7), // =
								new Identifier("NonTerminal", 5, 9), // NonTerminal
								new Keyword(Type.ALTERNATION, 5, 21), // |

								new Identifier("Terminal", 6, 5), // Terminal
								new Keyword(Type.ALTERNATION, 2, 14), // |

								new Terminal("(", 7, 5), // '('
								new Identifier("Expression", 7, 9), // Expression
								new Terminal(")", 7, 20), // ')'
								new Keyword(Type.ALTERNATION, 7, 24), // |

								new Terminal("[", 8, 5), // '['
								new Identifier("Expression", 8, 9), // Expression
								new Terminal("]", 8, 20), // ']'
								new Keyword(Type.ALTERNATION, 8, 24), // |

								new Terminal("{", 9, 5), // '{'
								new Identifier("Expression", 9, 9), // Expression
								new Terminal("}", 9, 20), // '}'
								new Keyword(Type.ALTERNATION, 9, 24), // |

								new Identifier("Number", 10, 5), // Number
								new Terminal("*", 10, 12), // '*'
								new Identifier("Expression", 10, 16), // Expression
								new Keyword(Type.ALTERNATION, 10, 27), // |

								new Terminal("(*", 11, 5), // '(*'
								new Keyword(Type.CONCATENATION, 11, 9), // ,
								new Keyword(Type.LEFT_REPETITION, 11, 11), // {
								new Identifier("Character", 11, 12), // Character
								new Keyword(Type.RIGHT_REPETITION, 11, 21), // }
								new Keyword(Type.CONCATENATION, 11, 22), // ,
								new Terminal("*)", 11, 24), // '*)'
								new Keyword(Type.ALTERNATION, 11, 29), // |

								new Terminal("?", 12, 5), // '?'
								new Keyword(Type.CONCATENATION, 12, 8), // ,
								new Keyword(Type.LEFT_REPETITION, 12, 10), // {
								new Identifier("Character", 12, 11), // Character
								new Keyword(Type.RIGHT_REPETITION, 12, 20), // }
								new Keyword(Type.CONCATENATION, 12, 21), // ,
								new Terminal("?", 12, 23), // '?'
								new Keyword(Type.ALTERNATION, 12, 27), // |
								new Terminal("-", 13, 5), // '-'
								new Identifier("Expression", 13, 9), // Expression
								new Keyword(Type.TERMINATION, 13, 19), // ;

								new Identifier("NonTerminal", 14, 0), // NonTerminal
								new Keyword(Type.DEFINITION, 14, 12), // =
								new Identifier("Character", 14, 14), // Character
								new Keyword(Type.CONCATENATION, 14, 23), // ,
								new Keyword(Type.LEFT_REPETITION, 14, 25), // {
								new Identifier("Character", 14, 26), // Character
								new Keyword(Type.RIGHT_REPETITION, 14, 35), // }
								new Keyword(Type.TERMINATION, 14, 36), // ;

								new Identifier("Terminal", 15, 0), // Terminal
								new Keyword(Type.DEFINITION, 15, 9), // =
								new Terminal("'", 15, 11), // "'"
								new Identifier("Character", 15, 15), // Character
								new Keyword(Type.CONCATENATION, 15, 24), // ,
								new Keyword(Type.LEFT_REPETITION, 15, 26), // {
								new Identifier("Character", 15, 27), // Character
								new Keyword(Type.RIGHT_REPETITION, 15, 36), // }
								new Keyword(Type.CONCATENATION, 15, 37), // ,
								new Terminal("'", 15, 39), // "'"
								new Keyword(Type.ALTERNATION, 15, 43), // |
								new Terminal("\"", 16, 7), // '"'
								new Identifier("Character", 16, 11), // Character
								new Keyword(Type.CONCATENATION, 16, 20), // ,
								new Keyword(Type.LEFT_REPETITION, 16, 22), // {
								new Identifier("Character", 16, 23), // Character
								new Keyword(Type.RIGHT_REPETITION, 16, 32), // }
								new Keyword(Type.CONCATENATION, 16, 33), // ,
								new Terminal("\"", 16, 35), // '"'
								new Keyword(Type.TERMINATION, 16, 38), // ;
								// Character = 'a' | 'b' |'c' | 'd' |'e' | 'f'
								// |'g' |
								// 'h' | 'i' |
								new Identifier("Character", 17, 0),
								new Keyword(Type.DEFINITION, 17, 10),
								new Terminal("a", 17, 12),
								new Keyword(Type.ALTERNATION, 17, 16),
								new Terminal("b", 17, 18),
								new Keyword(Type.ALTERNATION, 17, 22),
								new Terminal("c", 17, 24),
								new Keyword(Type.ALTERNATION, 17, 28),
								new Terminal("d", 17, 30),
								new Keyword(Type.ALTERNATION, 17, 34),
								new Terminal("e", 17, 36),
								new Keyword(Type.ALTERNATION, 17, 40),
								new Terminal("f", 17, 42),
								new Keyword(Type.ALTERNATION, 17, 46),
								new Terminal("g", 17, 48),
								new Keyword(Type.ALTERNATION, 17, 52),
								new Terminal("h", 17, 54),
								new Keyword(Type.ALTERNATION, 17, 58),
								new Terminal("i", 17, 60),
								new Keyword(Type.ALTERNATION, 17, 64),
								// 'j' | 'k' | 'l' | 'm' | 'n' | 'o' | 'p' | 'q'
								// | 'r' |
								new Terminal("j", 18, 8),
								new Keyword(Type.ALTERNATION, 18, 12),
								new Terminal("k", 18, 14),
								new Keyword(Type.ALTERNATION, 18, 18),
								new Terminal("l", 18, 20),
								new Keyword(Type.ALTERNATION, 18, 24),
								new Terminal("m", 18, 26),
								new Keyword(Type.ALTERNATION, 18, 30),
								new Terminal("n", 18, 32),
								new Keyword(Type.ALTERNATION, 18, 36),
								new Terminal("o", 18, 38),
								new Keyword(Type.ALTERNATION, 18, 42),
								new Terminal("p", 18, 44),
								new Keyword(Type.ALTERNATION, 18, 48),
								new Terminal("q", 18, 50),
								new Keyword(Type.ALTERNATION, 18, 54),
								new Terminal("r", 18, 56),
								new Keyword(Type.ALTERNATION, 18, 60),
								// 's' | 't' | 'u' | 'v' | 'w' | 'x' | 'y' |
								// 'z';
								new Terminal("s", 19, 8),
								new Keyword(Type.ALTERNATION, 19, 12),
								new Terminal("t", 19, 14),
								new Keyword(Type.ALTERNATION, 19, 18),
								new Terminal("u", 19, 20),
								new Keyword(Type.ALTERNATION, 19, 24),
								new Terminal("v", 19, 26),
								new Keyword(Type.ALTERNATION, 19, 30),
								new Terminal("w", 19, 32),
								new Keyword(Type.ALTERNATION, 19, 36),
								new Terminal("x", 19, 38),
								new Keyword(Type.ALTERNATION, 19, 42),
								new Terminal("y", 19, 44),
								new Keyword(Type.ALTERNATION, 19, 48),
								new Terminal("z", 19, 50),
								new Keyword(Type.TERMINATION, 19, 53),
								// Number = NonZeroDigit, {'0' | NonZeroDigit};
								new Identifier("Number", 20, 0),
								new Keyword(Type.DEFINITION, 20, 7),
								new Identifier("NonZeroDigit", 20, 9),
								new Keyword(Type.CONCATENATION, 20, 21),
								new Keyword(Type.LEFT_REPETITION, 20, 23),
								new Terminal("0", 20, 24),
								new Keyword(Type.ALTERNATION, 20, 28),
								new Identifier("NonZeroDigit", 20, 30),
								new Keyword(Type.RIGHT_REPETITION, 20, 42),
								new Keyword(Type.TERMINATION, 20, 43),
								// NonZeroDigit = '1' | '2' | '3' | '4' | '5' |
								// '6' |'7' | '8' | '9';
								new Identifier("NonZeroDigit", 21, 0),
								new Keyword(Type.DEFINITION, 21, 13),
								new Terminal("1", 21, 15),
								new Keyword(Type.ALTERNATION, 21, 19),
								new Terminal("2", 21, 21),
								new Keyword(Type.ALTERNATION, 21, 25),
								new Terminal("3", 21, 27),
								new Keyword(Type.ALTERNATION, 21, 31),
								new Terminal("4", 21, 33),
								new Keyword(Type.ALTERNATION, 21, 37),
								new Terminal("5", 21, 39),
								new Keyword(Type.ALTERNATION, 21, 43),
								new Terminal("6", 21, 45),
								new Keyword(Type.ALTERNATION, 21, 49),
								new Terminal("7", 21, 51),
								new Keyword(Type.ALTERNATION, 21, 55),
								new Terminal("8", 21, 57),
								new Keyword(Type.ALTERNATION, 21, 61),
								new Terminal("9", 21, 63),
								new Keyword(Type.TERMINATION, 21, 66) } },
				{
						"grammars/LambdaCalculus.ebnf",
						new IToken[] {
								// Expression = Atom | Abstraction |
								// Application;
								new Identifier("Expression", 1, 0),
								new Keyword(Type.DEFINITION, 1, 11),
								new Identifier("Atom", 1, 13),
								new Keyword(Type.ALTERNATION, 1, 18),
								new Identifier("Abstraction", 1, 20),
								new Keyword(Type.ALTERNATION, 1, 32),
								new Identifier("Application", 1, 34),
								new Keyword(Type.TERMINATION, 1, 45),
								// Abstraction = "(" Variable "|" Expression
								// ")";
								new Identifier("Abstraction", 2, 0),
								new Keyword(Type.DEFINITION, 2, 12),
								new Terminal("(", 2, 14),
								new Identifier("Variable", 2, 18),
								new Terminal("|", 2, 27),
								new Identifier("Expression", 2, 31),
								new Terminal(")", 2, 42),
								new Keyword(Type.TERMINATION, 2, 45),
								// Application = "(" Expression " " Expression
								// ")";
								new Identifier("Application", 3, 0),
								new Keyword(Type.DEFINITION, 3, 12),
								new Terminal("(", 3, 14),
								new Identifier("Expression", 3, 18),
								new Terminal(" ", 3, 29),
								new Identifier("Expression", 3, 33),
								new Terminal(")", 3, 44),
								new Keyword(Type.TERMINATION, 3, 47),
								// Atom = Character, {Character};
								new Identifier("Atom", 4, 0),
								new Keyword(Type.DEFINITION, 4, 5),
								new Identifier("Character", 4, 7),
								new Keyword(Type.CONCATENATION, 4, 16),
								new Keyword(Type.LEFT_REPETITION, 4, 18),
								new Identifier("Character", 4, 19),
								new Keyword(Type.RIGHT_REPETITION, 4, 28),
								new Keyword(Type.TERMINATION, 4, 29),
								// Character = 'a' | 'b' | 'c' | 'd' | 'e' | 'f'
								// | 'g' |
								// 'h' | 'i' |
								new Identifier("Character", 5, 0),
								new Keyword(Type.DEFINITION, 5, 10),
								new Terminal("a", 5, 12),
								new Keyword(Type.ALTERNATION, 5, 16),
								new Terminal("b", 5, 18),
								new Keyword(Type.ALTERNATION, 5, 22),
								new Terminal("c", 5, 24),
								new Keyword(Type.ALTERNATION, 5, 28),
								new Terminal("d", 5, 30),
								new Keyword(Type.ALTERNATION, 5, 34),
								new Terminal("e", 5, 36),
								new Keyword(Type.ALTERNATION, 5, 40),
								new Terminal("f", 5, 42),
								new Keyword(Type.ALTERNATION, 5, 46),
								new Terminal("g", 5, 48),
								new Keyword(Type.ALTERNATION, 5, 52),
								new Terminal("h", 5, 54),
								new Keyword(Type.ALTERNATION, 5, 58),
								new Terminal("i", 5, 60),
								new Keyword(Type.ALTERNATION, 5, 64),
								// 'j' | 'k' | 'l' | 'm' | 'n' | 'o' | 'p' | 'q'
								// | 'r' |
								new Terminal("j", 6, 8),
								new Keyword(Type.ALTERNATION, 6, 12),
								new Terminal("k", 6, 14),
								new Keyword(Type.ALTERNATION, 6, 18),
								new Terminal("l", 6, 20),
								new Keyword(Type.ALTERNATION, 6, 24),
								new Terminal("m", 6, 26),
								new Keyword(Type.ALTERNATION, 6, 30),
								new Terminal("n", 6, 32),
								new Keyword(Type.ALTERNATION, 6, 36),
								new Terminal("o", 6, 38),
								new Keyword(Type.ALTERNATION, 6, 42),
								new Terminal("p", 6, 44),
								new Keyword(Type.ALTERNATION, 6, 48),
								new Terminal("q", 6, 50),
								new Keyword(Type.ALTERNATION, 6, 54),
								new Terminal("r", 6, 56),
								new Keyword(Type.ALTERNATION, 6, 60),
								// 's' | 't' | 'u' | 'v' | 'w' | 'x' | 'y' |
								// 'z';
								new Terminal("s", 7, 8),
								new Keyword(Type.ALTERNATION, 7, 12),
								new Terminal("t", 7, 14),
								new Keyword(Type.ALTERNATION, 7, 18),
								new Terminal("u", 7, 20),
								new Keyword(Type.ALTERNATION, 7, 24),
								new Terminal("v", 7, 26),
								new Keyword(Type.ALTERNATION, 7, 30),
								new Terminal("w", 7, 32),
								new Keyword(Type.ALTERNATION, 7, 36),
								new Terminal("x", 7, 38),
								new Keyword(Type.ALTERNATION, 7, 42),
								new Terminal("y", 7, 44),
								new Keyword(Type.ALTERNATION, 7, 48),
								new Terminal("z", 7, 50),
								new Keyword(Type.TERMINATION, 7, 53), } } };
		return Arrays.asList(parameters);
	}

	/**
	 * Lexer with parameterized input.
	 */
	private final Lexer lexer;

	/**
	 * Type of the token for the symbol in the input of lexer.
	 */
	private final List<IToken> tokens;

	/**
	 * @param input
	 *            text parameter containing path to the grammar for this test
	 * @param tokenTypes
	 *            for the symbols in the input
	 * @throws FileNotFoundException
	 *             if path doesn't point to a grammar file
	 */
	public LexerFileTest(final String path, final IToken[] tokenTypes)
			throws FileNotFoundException {
		lexer = new Lexer(new File(path));
		tokens = new LinkedList<IToken>(Arrays.asList(tokenTypes));
	}

	/**
	 * Test symbols in the input being scanned properly.
	 * 
	 * @throws Exception
	 *             if symbol could not be recognized
	 */
	@Test
	public final void symbolToToken() {
		for (final IToken expectedToken : tokens) {
			assertTrue(expectedToken.getValue()
					+ " symbol should be recognized as a token",
					lexer.hasNext());
			final IToken actualToken = lexer.next();
			assertNotNull(actualToken);
			assertEquals(expectedToken, actualToken);
		}
		assertFalse("Lexer should not offer any more tokens", lexer.hasNext());
	}
}
