package Lexer.Tokens;

public class PreDefinedToken extends Token {
    private final PreDefinedTokenType type;
    public PreDefinedToken(String content, int line, int column, PreDefinedTokenType type) {
        super(content, line, column);
        this.type = type;
    }

    public PreDefinedTokenType getType() {
        return type;
    }

    private String tokenTypeToString(PreDefinedTokenType type) {
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
            case NT_OP: return "<>";
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

    public static PreDefinedTokenType stringToType(String type) {
        switch(type) {
            case "program": return PreDefinedTokenType.PROGRAM;
            case "var": return PreDefinedTokenType.VAR;
            case "const": return PreDefinedTokenType.CONST;
            case "type": return PreDefinedTokenType.TYPE;
            case "function": return PreDefinedTokenType.FUNCTION;
            case "return": return PreDefinedTokenType.RETURN;
            case "begin": return PreDefinedTokenType.BEGIN;
            case "end": return PreDefinedTokenType.END;
            case "output": return PreDefinedTokenType.OUTPUT_;
            case "if": return PreDefinedTokenType.IF;
            case "then": return PreDefinedTokenType.THEN;
            case "else": return PreDefinedTokenType.ELSE;
            case "while": return PreDefinedTokenType.WHILE;
            case "do": return PreDefinedTokenType.DO;
            case "case": return PreDefinedTokenType.CASE;
            case "of": return PreDefinedTokenType.OF;
            case "otherwise": return PreDefinedTokenType.OTHERWISE;
            case "repeat": return PreDefinedTokenType.REPEAT;
            case "for": return PreDefinedTokenType.FOR;
            case "until": return PreDefinedTokenType.UNTIL;
            case "loop": return PreDefinedTokenType.LOOP;
            case "pool": return PreDefinedTokenType.POOL;
            case "exit": return PreDefinedTokenType.EXIT;
            case "mod": return PreDefinedTokenType.MODULUS_OPR;
            case "and": return PreDefinedTokenType.AND_OPR;
            case "or": return PreDefinedTokenType.OR_OPR;
            case "not": return PreDefinedTokenType.NOT_OPR;
            case "read": return PreDefinedTokenType.READ;
            case "succ": return PreDefinedTokenType.SUCCESSOR;
            case "pred": return PreDefinedTokenType.PREDECESSOR;
            case "chr": return PreDefinedTokenType.CHAR_FUNC;
            case "ord": return PreDefinedTokenType.ORDINAL_FUNC;
            case "eof": return PreDefinedTokenType.END_OF_FILE;
            case ":=:": return PreDefinedTokenType.SWAP;
            case ":=": return PreDefinedTokenType.ASSIGNMENT;
            case "..": return PreDefinedTokenType.DOTS;
            case "<=": return PreDefinedTokenType.LT_EQ_OP;
            case "<>": return PreDefinedTokenType.NT_OP;
            case "<": return PreDefinedTokenType.LT_OP;
            case ">=": return PreDefinedTokenType.GT_EQ_OP;
            case ">": return PreDefinedTokenType.GT_OP;
            case "=": return PreDefinedTokenType.EQ_OP;
            case "{": return PreDefinedTokenType.BEGIN_BLOCK;
            case ":": return PreDefinedTokenType.COLON;
            case ";": return PreDefinedTokenType.SEMI_COLON;
            case ".": return PreDefinedTokenType.DOT;
            case ",": return PreDefinedTokenType.COMMA;
            case "(": return PreDefinedTokenType.OP_BRCKT;
            case ")": return PreDefinedTokenType.CL_BRCKT;
            case "+": return PreDefinedTokenType.PLUS;
            case "-": return PreDefinedTokenType.MINUS;
            case "*": return PreDefinedTokenType.MULT;
            case "/": return PreDefinedTokenType.DIVIDE;
            default: return null;
        }
    }

    @Override
    public String toString() {
        return "<" + tokenTypeToString(this.type) + ">";
    }
}
