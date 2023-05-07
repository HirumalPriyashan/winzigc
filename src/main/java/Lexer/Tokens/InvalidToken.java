package Lexer.Tokens;

public class InvalidToken extends Token {
    public InvalidToken(String content, int line, int column) {
        super(content, line, column);
    }

    @Override
    public String toString() {
        return "<invalid: " + getContent() + ">";
    }
}
