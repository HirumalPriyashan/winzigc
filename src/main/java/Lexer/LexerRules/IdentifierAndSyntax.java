package Lexer.LexerRules;

import Lexer.Tokens.IdentifierToken;
import Lexer.Tokens.PreDefinedToken;
import Lexer.Tokens.TokenType;
import Lexer.Tokens.Token;

/**
 * Lexer rule to identify identifier and syntax.
 *
 * Any sequence of characters which may contain alphabets, digits from
 * 0 to 9 or an underscore. The sequence must start with an alphabet or an
 * underscore.
 *
 * @author Hirumal Priyashan
 * @version 1.0
 * @since 1.0
 */
public class IdentifierAndSyntax extends AbstractRule {
    @Override
    protected Token applyRuleImplementation(
        String currentSource, int line,int column
    ) {
        String match = matchWithRegex(currentSource,
            "^[a-zA-Z_][a-zA-Z0-9_]*");
        if (match != null) {
            TokenType type = PreDefinedToken.stringToType(match);
            if (type != null)
                return new PreDefinedToken(match, type, line, column);
            return new IdentifierToken(match, line, column);
        }

        // match length of two ops first
        match = matchWithRegex(currentSource, "^(:=|:=:|..|<=|>=|<>)");
        if (match != null) {
            TokenType type = PreDefinedToken.stringToType(match);
            if (type != null)
                return new PreDefinedToken(match, type, line, column);
        }

        // match single length ops
        match = matchWithRegex(currentSource, "^[:.<>=;,()+\\-*/{]");
        if (match != null) {
            TokenType type = PreDefinedToken.stringToType(match);
            if (type != null)
                return new PreDefinedToken(match, type, line, column);
        }
        return null;
    }
}
