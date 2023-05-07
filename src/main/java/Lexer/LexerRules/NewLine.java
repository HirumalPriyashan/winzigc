package Lexer.LexerRules;

import Lexer.Tokens.PreDefinedToken;
import Lexer.Tokens.PreDefinedTokenType;
import Lexer.Tokens.Token;

public class NewLine extends AbstractRule {
    @Override
    protected Token applyRuleImplementation(String currentSource, int line, int column) {
        String match = matchWithRegex(currentSource, "^\n");
        if (match != null) {
            return new PreDefinedToken(match, line, column, PreDefinedTokenType.NEW_LINE);
        }
        return null;
    }
}
