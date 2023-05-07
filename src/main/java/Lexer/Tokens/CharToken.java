package Lexer.Tokens;

public class CharToken extends Token {
    public CharToken(String content, int line, int column) {
        super(content, line, column);
    }

    @Override
    public String toString() {
        return "<char: " + getContent() + ">";
    }

    @Override
    public String getContentForNode() {
        return "<char>";
    }
}
