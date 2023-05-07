package Lexer.Tokens;

public abstract class Token {
    private final String content;
    private final int line;
    private final int column;

    public Token(String content, int line, int column) {
        this.content = content;
        this.line = line;
        this.column = column;
    }

    public String getContent() {
        return content;
    }
    public String getContentForNode() {
        return content;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "Token<" +
                "content='" + content + '\'' +
                ", line=" + line +
                ", column=" + column +
                '}';
    }
}
