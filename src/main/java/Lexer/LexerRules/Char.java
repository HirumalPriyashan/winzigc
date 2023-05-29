package Lexer.LexerRules;

import Lexer.Tokens.CharToken;
import Lexer.Tokens.Token;

public class Char extends AbstractRule {
    @Override
    protected Token applyRuleImplementation(String currentSource, int line, int column) {
        String match = matchWithRegex(currentSource, "^'[^']{1}'");
        if (match != null)
            return new CharToken(match, line, column);
        return null;
    }
}
