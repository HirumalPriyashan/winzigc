package Lexer.Tokens;

public class IdentifierToken extends Token {
    public IdentifierToken(String content, int line, int column) {
        super(content, line, column);
    }

    @Override
    public String toString() {
        return "<identifier: " + getContent() + ">";
    }

    @Override
    public String getContentForNode() {
        return "<identifier>";
    }
}
