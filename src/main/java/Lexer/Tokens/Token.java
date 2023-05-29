package Lexer.Tokens;

public abstract class Token {
    private final String content;
    private final int line;
    private final int column;
    private final TokenType type;

    public Token(String content, TokenType tokenType, int line, int column) {
        this.content = content;
        this.line = line;
        this.column = column;
        this.type = tokenType;
    }

    public String getContent() {
        return content;
    }

    public String getContentForNode() {
        return "<" + this.getContent() + ">";
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public TokenType getType() {
        return type;
    }

    public int getNumberOfLines() {
        return 0;
    }

    public int getColumnUpdate() {
        return this.getColumn() + this.getContent().length();
    }

    @Override
    public String toString() {
        return "Token{" +
                "content='" + content + '\'' +
                ", line=" + line +
                ", column=" + column +
                '}';
    }
}
