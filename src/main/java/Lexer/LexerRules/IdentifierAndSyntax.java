package Lexer.LexerRules;

import Lexer.Tokens.IdentifierToken;
import Lexer.Tokens.PreDefinedToken;
import Lexer.Tokens.PreDefinedTokenType;
import Lexer.Tokens.Token;

public class IdentifierAndSyntax extends AbstractRule{
    @Override
    protected Token applyRuleImplementation(String currentSource, int line, int column) {
        String match = matchWithRegex(currentSource, "^[a-zA-Z_][a-zA-Z0-9_]*");
        if (match != null) {
            PreDefinedTokenType type = PreDefinedToken.stringToType(match);
            if (type != null)
                return new PreDefinedToken(match, line, column, type);
            return new IdentifierToken(match, line, column);
        }

        // match length of two ops first
        match = matchWithRegex(currentSource, "^(:=|:=:|..|<=|>=|<>)");
        if (match != null) {
            PreDefinedTokenType type = PreDefinedToken.stringToType(match);
            if (type != null)
                return new PreDefinedToken(match, line, column, type);
        }

        // match single length ops
        match = matchWithRegex(currentSource, "^[:.<>=;,()+\\-*/{]");
        if (match != null) {
            PreDefinedTokenType type = PreDefinedToken.stringToType(match);
            if (type != null)
                return new PreDefinedToken(match, line, column, type);
        }
        return null;
    }
}
