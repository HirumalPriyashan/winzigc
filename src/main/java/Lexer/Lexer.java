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
    private int line = 1;
    private int column = 1;
    private List<Token> tokens;
    private int position;

    /**
     * Class constructor.
     *
     * @param source Source string
     */
    public Lexer(String source) {
        this.source = source;
        this.lexerRules = getLexerRules();
        this.tokens = null;
    }

    /**
     * Prepare and return lexical rule set.
     *
     * @return The set of lexical rules
     */
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

    /**
     * Consume given token from current source
     *
     * @param token Token to consume
     * @param currentSource Source to be consumed
     * @return Source after consuming token
     */
    private String consumeToken(Token token, String currentSource) {
        String content = token.getContent();
        position += content.length();
        line += token.getNumberOfLines();
        column = token.getColumnUpdate();
        return currentSource.replaceFirst(Pattern.quote(content), "");
    }

    /**
     * Generate tokens from the source
     *
     * @throws InvalidTokenException
     */
    private void generateTokens() throws InvalidTokenException {
        tokens = new ArrayList<>();
        Token token;
        String currentSource = source;
        while (position < source.length()) {
            token = lexerRules.applyRule(currentSource, line, column);
            if (token instanceof InvalidToken)
                throw new InvalidTokenException("current source: \n" +
                    currentSource);
            currentSource = consumeToken(token, currentSource);
            tokens.add(token);
        }
        tokens.add(new PreDefinedToken("eof", TokenType.END_OF_FILE,
            line, column));
    }

    /**
     * Returns token for the source
     *
     * @return Tokens for the source
     * @throws InvalidTokenException
     */
    public List<Token> getTokens() throws InvalidTokenException {
        if (tokens == null) {
            generateTokens();
        }
        return tokens;
    }

    /**
     * Returns screened tokens
     *
     * @return Screened tokens for the source
     * @throws InvalidTokenException
     */
    public List<Token> getScreenedTokens() throws InvalidTokenException {
        List<Token> screened = new ArrayList<>();
        for (Token token : getTokens())
            if (!(token.getType() == TokenType.WHITE_SPACE ||
                    token.getType() == TokenType.COMMENT ||
                    token.getType() == TokenType.NEW_LINE))
                screened.add(token);
        return screened;
    }
}
