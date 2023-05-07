package Lexer.Tokens;

public class StringToken  extends Token {
    public StringToken(String content, int line, int column) {
        super(content, line, column);
    }

    @Override
    public String toString() {
        return "<string: " + getContent() + ">";
    }

    @Override
    public String getContentForNode() {
        return "<string>";
    }
}
