package Lexer.LexerRules;

import Lexer.Tokens.CommentToken;
import Lexer.Tokens.CommentType;
import Lexer.Tokens.Token;

public class Comments extends AbstractRule {
    @Override
    protected Token applyRuleImplementation(String currentSource, int line, int column) {
        // Type 1: a sequence of characters which starts with # and ends with a newline.
        String match = matchWithRegex(currentSource, "^#[^\n]*");
        if (match != null) {
            return new CommentToken(match, line, column, CommentType.TYPE_ONE);
        }

        // Type2: begins with ‘{‘, continues with any character (including end-of-line), and ends with ‘}’.
        match = matchWithRegex(currentSource, "^\\{[\\s\\S]*?}");
        if (match != null) {
            return new CommentToken(match, line, column, CommentType.TYPE_TWO);
        }
        return null;
    }
}
