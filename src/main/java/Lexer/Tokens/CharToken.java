package Lexer.Tokens;

public class CharToken extends Token {
    public CharToken(String content, int line, int column) {
        super(content, TokenType.CHAR, line, column);
    }

    @Override
    public String toString() {
        return "<char: " + this.getContent() + ">";
    }

    @Override
    public String getContentForNode() {
        return "<char>";
    }
}
