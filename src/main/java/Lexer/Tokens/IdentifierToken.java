package Lexer.Tokens;

public class IdentifierToken extends Token {
    public IdentifierToken(String content, int line, int column) {
        super(content, TokenType.IDENTIFIER, line, column);
    }

    @Override
    public String toString() {
        return "<identifier: " + this.getContent() + ">";
    }

    @Override
    public String getContentForNode() {
        return "<identifier>";
    }
}
