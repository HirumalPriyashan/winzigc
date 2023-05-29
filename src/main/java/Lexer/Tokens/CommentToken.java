package Lexer.Tokens;

public class CommentToken extends Token {
    private final CommentType commentType;

    public CommentToken(String content, int line, int column, CommentType commentType) {
        super(content, TokenType.COMMENT, line, column);
        this.commentType = commentType;
    }

    public CommentType getCommentType() {
        return commentType;
    }

    @Override
    public int getNumberOfLines() {
        return commentType == CommentType.TYPE_ONE ? 1 : this.getNewLineCount();
    }

    private int getNewLineCount() {
        int count = 0;
        for (int i = 0; i < this.getContent().length(); i++) {
            if (this.getContent().charAt(i) == '\n')
                count++;
        }
        return count;
    }

    @Override
    public String toString() {
        return commentType == CommentType.TYPE_ONE ? "<CommentTypeOne>" : "<CommentTypeTwo>";
    }
}
