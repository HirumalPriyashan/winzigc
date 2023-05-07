package Lexer.LexerRules;

import Lexer.Tokens.InvalidToken;
import Lexer.Tokens.Token;

public class Invalid extends AbstractRule {
    @Override
    protected Token applyRuleImplementation(String currentSource, int line, int column) {
        return new InvalidToken(currentSource, line, column);
    }
}
