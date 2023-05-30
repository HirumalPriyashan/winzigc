package Lexer.LexerRules;

import Lexer.Tokens.PreDefinedToken;
import Lexer.Tokens.TokenType;
import Lexer.Tokens.Token;

/**
 * Lexer rule to identify new line characters.
 *
 * @author Hirumal Priyashan
 * @version 1.0
 * @since 1.0
 */
public class NewLine extends AbstractRule {
    @Override
    protected Token applyRuleImplementation(
        String currentSource, int line,int column
    ) {
        String match = matchWithRegex(currentSource, "^\n");
        if (match != null)
            return new PreDefinedToken(match, TokenType.NEW_LINE, line, column);
        return null;
    }
}
