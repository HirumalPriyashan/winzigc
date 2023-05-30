package Lexer.LexerRules;

import Lexer.Tokens.IntegerToken;
import Lexer.Tokens.Token;

/**
 * Lexer rule to identify integers.
 *
 * A sequence of characters which may contain any combination of digits
 * from 0 to 9.
 *
 * @author Hirumal Priyashan
 * @version 1.0
 * @since 1.0
 */
public class Integer extends AbstractRule {
    @Override
    protected Token applyRuleImplementation(
        String currentSource, int line,int column
    ) {
        String match = matchWithRegex(currentSource, "^[0-9]+");
        if (match != null)
            return new IntegerToken(match, line, column);
        return null;
    }
}
