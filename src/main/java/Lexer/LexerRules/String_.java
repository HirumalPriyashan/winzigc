package Lexer.LexerRules;

import Lexer.Tokens.StringToken;
import Lexer.Tokens.Token;

/**
 * Lexer rule to identify strings.
 *
 * A sequence of characters which contains any number of characters
 * between two double quotes. The character sequence between the two double
 * quotes can be any character except a double quote itself. For example,
 * “Hello”how” is an invalid string.
 *
 * @author Hirumal Priyashan
 * @version 1.0
 * @since 1.0
 */
public class String_ extends AbstractRule {
    @Override
    protected Token applyRuleImplementation(
        String currentSource, int line,int column
    ) {
        String match = matchWithRegex(currentSource, "^\"[^\"]*\"");
        if (match != null)
            return new StringToken(match, line, column);
        return null;
    }
}
