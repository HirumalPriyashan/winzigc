package Lexer.Tokens;

public class IntegerToken extends Token {
    public IntegerToken(String content, int line, int column) {
        super(content, TokenType.INTEGER, line, column);
    }

    @Override
    public String toString() {
        return "<integer: " + this.getContent() + ">";
    }

    @Override
    public String getContentForNode() {
        return "<integer>";
    }
}
