package Lexer.LexerRules;

import Lexer.Tokens.Token;
import Lexer.Tokens.WhiteSpaceToken;

public class WhiteSpace extends AbstractRule{
    @Override
    protected Token applyRuleImplementation(String currentSource, int line, int column) {
        String match = matchWithRegex(currentSource, "^[^\\n\\S]+");
        if (match != null) {
            return new WhiteSpaceToken(match, line, column);
        }
        return null;
    }
}
