package Lexer.LexerRules;

import Lexer.Tokens.Token;
import Lexer.Tokens.WhiteSpaceToken;

/**
 * Lexer rule to identify white spaces.
 *
 * Any sequence of characters containing any combination of single
 * space, form feed, horizontal tab and vertical tab.
 *
 * @author Hirumal Priyashan
 * @version 1.0
 * @since 1.0
 */
public class WhiteSpace extends AbstractRule {
    @Override
    protected Token applyRuleImplementation(
        String currentSource, int line,int column
    ) {
        String match = matchWithRegex(currentSource, "^[^\\n\\S]+");
        if (match != null)
            return new WhiteSpaceToken(match, line, column);
        return null;
    }
}
