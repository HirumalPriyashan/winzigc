package Lexer.LexerRules;

import Lexer.Tokens.CharToken;
import Lexer.Tokens.Token;

/**
 * Lexer rule to identify chars.
 *
 * A sequence of three characters which contains a single character between two
 * single quotes. The single character between the two quotes can be any
 * character except a single quote itself. For example, ‘’’ is an invalid
 * character.
 *
 * @author Hirumal Priyashan
 * @version 1.0
 * @since 1.0
 */
public class Char extends AbstractRule {
    @Override
    protected Token applyRuleImplementation(
        String currentSource, int line,int column
    ) {
        String match = matchWithRegex(currentSource, "^'[^']{1}'");
        if (match != null)
            return new CharToken(match, line, column);
        return null;
    }
}
