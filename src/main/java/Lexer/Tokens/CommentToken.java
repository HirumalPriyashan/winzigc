package Lexer.Tokens;

public class CommentToken extends Token {
    private final CommentType commentType;

    public CommentToken(String content, int line, int column, CommentType commentType) {
        super(content, line, column);
        this.commentType = commentType;
    }

    public CommentType getCommentType() {
        return commentType;
    }

    @Override
    public String toString() {
        return commentType == CommentType.TYPE_ONE ? "<CommentTypeOne>" : "<CommentTypeTwo>";
    }
}
