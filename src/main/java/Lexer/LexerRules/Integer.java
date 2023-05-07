package Lexer.LexerRules;

import Lexer.Tokens.IntegerToken;
import Lexer.Tokens.Token;

public class Integer extends AbstractRule {
    @Override
    protected Token applyRuleImplementation(String currentSource, int line, int column) {
        String match = matchWithRegex(currentSource, "^[0-9]+");
        if (match != null) {
            return new IntegerToken(match, line, column);
        }
        return null;
    }
}
