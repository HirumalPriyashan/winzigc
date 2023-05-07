package Lexer.LexerRules;

import Lexer.Tokens.StringToken;
import Lexer.Tokens.Token;

public class String_ extends AbstractRule {
    @Override
    protected Token applyRuleImplementation(String currentSource, int line, int column) {
        String match = matchWithRegex(currentSource, "^\"[^\"]*\"");
        if (match != null) {
            return new StringToken(match, line, column);
        }
        return null;
    }
}
