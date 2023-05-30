package Lexer.Tokens;

public class StringToken  extends Token {
    public StringToken(String content, int line, int column) {
        super(content, TokenType.STRING, line, column);
    }

    @Override
    public String toString() {
        return "<string: " + this.getContent() + ">";
    }

    @Override
    public String getContentForNode() {
        return "<string>";
    }
}
