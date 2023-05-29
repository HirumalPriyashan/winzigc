package Lexer.Tokens;

public class WhiteSpaceToken extends Token{
    public WhiteSpaceToken(String content, int line, int column) {
        super(content, TokenType.WHITE_SPACE, line, column);
    }

    @Override
    public String toString() {
        return "<white-space>";
    }
}
