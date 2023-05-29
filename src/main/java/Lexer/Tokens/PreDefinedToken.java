package Lexer.Tokens;

public class PreDefinedToken extends Token {
    public PreDefinedToken(String content, TokenType type, int line, int column) {
        super(content, type, line, column);
    }

    private String tokenTypeToString(TokenType type) {
        switch (type) {
            case NEW_LINE: return "new-line";
            case PROGRAM: return "program";
            case VAR: return "var";
            case CONST: return "const";
            case TYPE: return "type";
            case FUNCTION: return "function";
            case RETURN: return "return";
            case BEGIN: return "begin";
            case END: return "end";
            case OUTPUT_: return "output";
            case IF: return "if";
            case THEN: return "then";
            case ELSE: return "else";
            case WHILE: return "while";
            case DO: return "do";
            case CASE: return "case";
            case OF: return "of";
            case OTHERWISE: return "otherwise";
            case REPEAT: return "repeat";
            case FOR: return "for";
            case UNTIL: return "until";
            case LOOP: return "loop";
            case POOL: return "pool";
            case EXIT: return "exit";
            case MODULUS_OPR: return "mod";
            case AND_OPR: return "and";
            case OR_OPR: return "or";
            case NOT_OPR: return "not";
            case READ: return "read";
            case SUCCESSOR: return "succ";
            case PREDECESSOR: return "pred";
            case CHAR_FUNC: return "chr";
            case ORDINAL_FUNC: return "ord";
            case END_OF_FILE: return "eof";
            case SWAP: return ":=:";
            case ASSIGNMENT: return ":=";
            case DOTS: return "..";
            case LT_EQ_OP: return "<=";
            case NT_EQ_OP: return "<>";
            case LT_OP: return "<";
            case GT_EQ_OP: return ">=";
            case GT_OP: return ">";
            case EQ_OP: return "=";
            case BEGIN_BLOCK: return "{";
            case COLON: return ":";
            case SEMI_COLON: return ";";
            case DOT: return ".";
            case COMMA: return ",";
            case OP_BRCKT: return "(";
            case CL_BRCKT: return ")";
            case PLUS: return "+";
            case MINUS: return "-";
            case MULT: return "*";
            case DIVIDE: return "/";
            default: return "";
        }
    }

    public static TokenType stringToType(String type) {
        switch(type) {
            case "program": return TokenType.PROGRAM;
            case "var": return TokenType.VAR;
            case "const": return TokenType.CONST;
            case "type": return TokenType.TYPE;
            case "function": return TokenType.FUNCTION;
            case "return": return TokenType.RETURN;
            case "begin": return TokenType.BEGIN;
            case "end": return TokenType.END;
            case "output": return TokenType.OUTPUT_;
            case "if": return TokenType.IF;
            case "then": return TokenType.THEN;
            case "else": return TokenType.ELSE;
            case "while": return TokenType.WHILE;
            case "do": return TokenType.DO;
            case "case": return TokenType.CASE;
            case "of": return TokenType.OF;
            case "otherwise": return TokenType.OTHERWISE;
            case "repeat": return TokenType.REPEAT;
            case "for": return TokenType.FOR;
            case "until": return TokenType.UNTIL;
            case "loop": return TokenType.LOOP;
            case "pool": return TokenType.POOL;
            case "exit": return TokenType.EXIT;
            case "mod": return TokenType.MODULUS_OPR;
            case "and": return TokenType.AND_OPR;
            case "or": return TokenType.OR_OPR;
            case "not": return TokenType.NOT_OPR;
            case "read": return TokenType.READ;
            case "succ": return TokenType.SUCCESSOR;
            case "pred": return TokenType.PREDECESSOR;
            case "chr": return TokenType.CHAR_FUNC;
            case "ord": return TokenType.ORDINAL_FUNC;
            case "eof": return TokenType.END_OF_FILE;
            case ":=:": return TokenType.SWAP;
            case ":=": return TokenType.ASSIGNMENT;
            case "..": return TokenType.DOTS;
            case "<=": return TokenType.LT_EQ_OP;
            case "<>": return TokenType.NT_EQ_OP;
            case "<": return TokenType.LT_OP;
            case ">=": return TokenType.GT_EQ_OP;
            case ">": return TokenType.GT_OP;
            case "=": return TokenType.EQ_OP;
            case "{": return TokenType.BEGIN_BLOCK;
            case ":": return TokenType.COLON;
            case ";": return TokenType.SEMI_COLON;
            case ".": return TokenType.DOT;
            case ",": return TokenType.COMMA;
            case "(": return TokenType.OP_BRCKT;
            case ")": return TokenType.CL_BRCKT;
            case "+": return TokenType.PLUS;
            case "-": return TokenType.MINUS;
            case "*": return TokenType.MULT;
            case "/": return TokenType.DIVIDE;
            default: return null;
        }
    }

    @Override
    public int getNumberOfLines() {
        return this.getType() == TokenType.NEW_LINE ? 1 : 0;
    }

    @Override
    public int getColumnUpdate() {
        return this.getType() == TokenType.NEW_LINE ? 1 : this.getColumn() + this.getContent().length();
    }

    @Override
    public String toString() {
        return "<" + this.tokenTypeToString(this.getType()) + ">";
    }
}
