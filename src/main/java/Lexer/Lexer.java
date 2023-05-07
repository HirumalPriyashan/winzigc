package Lexer;

import Lexer.LexerRules.Integer;
import Lexer.LexerRules.*;
import Lexer.Tokens.*;
import MyExeptions.InvalidTokenException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Lexical analyser for parsing
 *
 * @author Hirumal Priyashan
 * @version 1.0
 * @since 1.0
 */
public class Lexer {
    private final AbstractRule lexerRules;
    private final String source;
    private final int line = 1;
    private final int column = 0;
    private List<Token> tokens;
    private int position;

    public Lexer(String source) {
        this.source = source;
        this.lexerRules = this.getLexerRules();
        this.tokens = null;
    }

    private AbstractRule getLexerRules() {
        AbstractRule rules = new Comments();
        rules.setSuccessor(new IdentifierAndSyntax())
                .setSuccessor(new Integer())
                .setSuccessor(new String_())
                .setSuccessor(new Char())
                .setSuccessor(new NewLine())
                .setSuccessor(new WhiteSpace())
                .setSuccessor(new Invalid());
        return rules;
    }

    private String consumeToken(Token token, String currentSource) {
        String content = token.getContent();
        position += content.length();
        return currentSource.replaceFirst(Pattern.quote(content), "");
    }

    private void generateTokens() throws InvalidTokenException {
        this.tokens = new ArrayList<>();
        Token token;
        String currentSource = this.source;
        while (position < this.source.length()) {
            token = this.lexerRules.applyRule(currentSource, line, column);
            if (token instanceof InvalidToken)
                throw new InvalidTokenException("current source: \n" + currentSource);
            currentSource = this.consumeToken(token, currentSource);
            this.tokens.add(token);
        }
        this.tokens.add(new PreDefinedToken("eof", line, column, PreDefinedTokenType.END_OF_FILE));
    }

    public List<Token> getTokens() throws InvalidTokenException {
        if (this.tokens == null) {
            this.generateTokens();
        }
        return this.tokens;
    }

    public List<Token> getScreenedTokens() throws InvalidTokenException {
        List<Token> screened = new ArrayList<>();
        for (Token token : this.getTokens())
            if (!(token instanceof WhiteSpaceToken)
                && !(token instanceof CommentToken)
                && !(
                    (token instanceof PreDefinedToken)
                    && (((PreDefinedToken) token).getType() == PreDefinedTokenType.NEW_LINE)
                )
            )
                screened.add(token);
        return screened;
    }
}
