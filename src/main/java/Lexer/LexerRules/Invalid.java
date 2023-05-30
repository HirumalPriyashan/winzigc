package Lexer.LexerRules;

import Lexer.Tokens.InvalidToken;
import Lexer.Tokens.Token;

/**
 * Lexer rule to identify invalid tokens.
 *
 * @author Hirumal Priyashan
 * @version 1.0
 * @since 1.0
 */
public class Invalid extends AbstractRule {
    @Override
    protected Token applyRuleImplementation(
        String currentSource, int line,int column
    ) {
        return new InvalidToken(currentSource, line, column);
    }
}
