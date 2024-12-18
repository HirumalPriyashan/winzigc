package Parser;

import Lexer.Tokens.*;
import MyExeptions.ParsingException;
import Node.Node;

import java.util.List;
import java.util.Stack;

/**
 * Class representation for parser
 *
 * @author Hirumal Priyashan
 * @version 1.0
 * @since 1.0
 */
public class Parser {
    private final List<Token> tokens;
    private int nextTokenId;
    private Stack<Node> stack;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.nextTokenId = 0;
        this.stack = new Stack<>();
    }

    private boolean hasNext() {
        return nextTokenId <= tokens.size() - 1;
    }

    private Token getNextToken() {
        if (hasNext())
            return tokens.get(nextTokenId++);
        return null;
    }

    private Token peekNextToken() throws ParsingException {
        if (hasNext())
            return tokens.get(nextTokenId);
        throw new ParsingException(
            "Token list is over, without completing the parsing");
    }

    private boolean matchWithNextToken(TokenType type) throws ParsingException {
        return peekNextToken().getType() == type;
    }

    private void consume(TokenType type) throws ParsingException {
        Token nextToken = getNextToken();
        if (nextToken == null)
            throw new ParsingException(
                "\nToken list is over, \n\texpected: \"" + type.name() + "\"");
        if (nextToken.getType() != type)
            throw new ParsingException(
                "\nInvalid token on \n\tline: " + nextToken.getLine() +
                ", column: " + nextToken.getColumn() +
                ",\n\texpected: \"" + type.name()
                + "\",\n\treceived: \"" + nextToken.getContent() + "\"");
        if (nextToken.getType() == TokenType.IDENTIFIER ||
            nextToken.getType() == TokenType.INTEGER ||
            nextToken.getType() == TokenType.STRING ||
            nextToken.getType() == TokenType.CHAR
        ) {
            stack.push(new Node(nextToken.getContent()));
            build_tree(nextToken.getContentForNode(), 1);
        }
    }

    private void build_tree(String name, int number_of_children)
        throws ParsingException {
        if (stack.size() < number_of_children) {
            for (Node node : stack)
                node.printNode();
            throw new ParsingException("Stack does not contain enough elements,"
                + "\n\texpected: " + number_of_children +
                "\n\tgot: " + stack.size());
        }
        Node parent = new Node(name);
        for (int i = 0; i < number_of_children; i++)
            parent.addChild(stack.pop());
        stack.push(parent);
    }

    private void Winzig() throws ParsingException {
        consume(TokenType.PROGRAM);
        Name();
        consume(TokenType.COLON);
        Consts();
        Types();
        Dclns();
        SubProgs();
        Body();
        Name();
        consume(TokenType.DOT);
        build_tree("program", 7);
    }

    private void Consts() throws ParsingException {
        int number_of_children = 0;
        if (matchWithNextToken(TokenType.CONST)) {
            consume(TokenType.CONST);
            Const();
            number_of_children += 1;
            while (matchWithNextToken(TokenType.COMMA)) {
                consume(TokenType.COMMA);
                Const();
                number_of_children += 1;
            }
            consume(TokenType.SEMI_COLON);
        }
        build_tree("consts", number_of_children);
    }

    private void Const() throws ParsingException {
        Name();
        consume(TokenType.EQ_OP);
        ConstValue();
        build_tree("const", 2);
    }

    private void ConstValue() throws ParsingException {
        Token nextToken = peekNextToken();
        switch (nextToken.getType()) {
            case INTEGER:
                consume(TokenType.INTEGER);
                break;
            case CHAR:
                consume(TokenType.CHAR);
                break;
            case IDENTIFIER:
                consume(TokenType.IDENTIFIER);
                break;
            default:
                throw new ParsingException(
                "\nInvalid token on \n\tline: " + nextToken.getLine() +
                ", column: " + nextToken.getColumn() +
                ",\n\treceived: \"" + nextToken.getContent() + "\"");
        }
    }

    private void Types() throws ParsingException {
        int number_of_children = 0;
        if (matchWithNextToken(TokenType.TYPE)) {
            consume(TokenType.TYPE);
            do {
                Type();
                number_of_children += 1;
                consume(TokenType.SEMI_COLON);
            } while (matchWithNextToken(TokenType.IDENTIFIER));
        }
        build_tree("types", number_of_children);
    }

    private void Type() throws ParsingException {
        Name();
        consume(TokenType.EQ_OP);
        LitList();
        build_tree("type", 2);
    }

    private void LitList() throws ParsingException {
        consume(TokenType.OP_BRCKT);
        Name();
        int number_of_children = 1;
        while (matchWithNextToken(TokenType.COMMA)) {
            consume(TokenType.COMMA);
            Name();
            number_of_children += 1;
        }
        consume(TokenType.CL_BRCKT);
        build_tree("lit", number_of_children);
    }

    private void SubProgs() throws ParsingException {
        int number_of_children = 0;
        while (matchWithNextToken(TokenType.FUNCTION)) {
            Fcn();
            number_of_children += 1;
        }
        build_tree("subprogs", number_of_children);
    }

    private void Fcn() throws ParsingException {
        consume(TokenType.FUNCTION);
        Name();
        consume(TokenType.OP_BRCKT);
        Params();
        consume(TokenType.CL_BRCKT);
        consume(TokenType.COLON);
        Name();
        consume(TokenType.SEMI_COLON);
        Consts();
        Types();
        Dclns();
        Body();
        Name();
        consume(TokenType.SEMI_COLON);
        build_tree("fcn", 8);
    }

    private void Params() throws ParsingException {
        Dcln();
        int number_of_children = 1;
        while (matchWithNextToken(TokenType.SEMI_COLON)) {
            consume(TokenType.SEMI_COLON);
            Dcln();
            number_of_children += 1;
        }
        build_tree("params", number_of_children);
    }

    private void Dclns() throws ParsingException {
        int number_of_children = 0;
        if (matchWithNextToken(TokenType.VAR)) {
            consume(TokenType.VAR);
            do {
                Dcln();
                number_of_children += 1;
                consume(TokenType.SEMI_COLON);
            } while (matchWithNextToken(TokenType.IDENTIFIER));
        }
        build_tree("dclns", number_of_children);
    }

    private void Dcln() throws ParsingException {
        Name();
        int number_of_children = 1;
        while (!matchWithNextToken(TokenType.COLON)) {
            consume(TokenType.COMMA);
            Name();
            number_of_children += 1;
        }
        consume(TokenType.COLON);
        Name();
        number_of_children += 1;
        build_tree("var", number_of_children);
    }

    private void Body() throws ParsingException {
        consume(TokenType.BEGIN);
        Statement();
        int number_of_children = 1;
        while (matchWithNextToken(TokenType.SEMI_COLON)) {
            consume(TokenType.SEMI_COLON);
            Statement();
            number_of_children += 1;
        }
        consume(TokenType.END);
        build_tree("block", number_of_children);
    }

    private void Statement() throws ParsingException {
        int number_of_children = 0;
        Token nextToken = peekNextToken();
        switch (nextToken.getType()) {
            case IDENTIFIER:
                Assignment();
                break;
            case OUTPUT_:
                consume(TokenType.OUTPUT_);
                consume(TokenType.OP_BRCKT);
                OutExp();
                number_of_children = 1;
                while (matchWithNextToken(TokenType.COMMA)) {
                    consume(TokenType.COMMA);
                    OutExp();
                    number_of_children += 1;
                }
                consume(TokenType.CL_BRCKT);
                build_tree("output", number_of_children);
                break;
            case IF:
                consume(TokenType.IF);
                Expression();
                consume(TokenType.THEN);
                Statement();
                number_of_children = 2;
                if (matchWithNextToken(TokenType.ELSE)) {
                    consume(TokenType.ELSE);
                    Statement();
                    number_of_children += 1;
                }
                build_tree("if", number_of_children);
                break;
            case WHILE:
                consume(TokenType.WHILE);
                Expression();
                consume(TokenType.DO);
                Statement();
                build_tree("while", 2);
                break;
            case REPEAT:
                consume(TokenType.REPEAT);
                Statement();
                number_of_children = 1;
                while (matchWithNextToken(TokenType.SEMI_COLON)) {
                    consume(TokenType.SEMI_COLON);
                    Statement();
                    number_of_children += 1;
                }
                consume(TokenType.UNTIL);
                Expression();
                number_of_children += 1;
                build_tree("repeat", number_of_children);
                break;
            case FOR:
                consume(TokenType.FOR);
                consume(TokenType.OP_BRCKT);
                ForStat();
                consume(TokenType.SEMI_COLON);
                ForExp();
                consume(TokenType.SEMI_COLON);
                ForStat();
                consume(TokenType.CL_BRCKT);
                Statement();
                build_tree("for", 4);
                break;
            case LOOP:
                consume(TokenType.LOOP);
                Statement();
                number_of_children = 1;
                while (matchWithNextToken(TokenType.SEMI_COLON)) {
                    consume(TokenType.SEMI_COLON);
                    Statement();
                    number_of_children += 1;
                }
                consume(TokenType.POOL);
                build_tree("loop", number_of_children);
                break;
            case CASE:
                consume(TokenType.CASE);
                Expression();
                number_of_children = 1;
                consume(TokenType.OF);
                number_of_children += CaseClauses();
                number_of_children += OtherwiseClause();
                consume(TokenType.END);
                build_tree("case", number_of_children);
                break;
            case READ:
                consume(TokenType.READ);
                consume(TokenType.OP_BRCKT);
                Name();
                number_of_children = 1;
                while (matchWithNextToken(TokenType.COMMA)) {
                    consume(TokenType.COMMA);
                    Name();
                    number_of_children += 1;
                }
                consume(TokenType.CL_BRCKT);
                build_tree("read", number_of_children);
                break;
            case EXIT:
                consume(TokenType.EXIT);
                build_tree("exit", 0);
                break;
            case RETURN:
                consume(TokenType.RETURN);
                Expression();
                build_tree("return", 1);
                break;
            case BEGIN:
                Body();
                break;
            default:
                build_tree("<null>", 0);
                break;
        }
    }

    private void OutExp() throws ParsingException {
        switch (peekNextToken().getType()) {
            case STRING:
                StringNode();
                build_tree("string", 1);
                break;
            default:
                Expression();
                build_tree("integer", 1);
                break;
        }
    }

    private void StringNode() throws ParsingException {
        if (matchWithNextToken(TokenType.STRING))
            consume(TokenType.STRING);
    }

    private int CaseClauses() throws ParsingException {
        int number_of_cases = 0;
        do {
            CaseClause();
            number_of_cases += 1;
            consume(TokenType.SEMI_COLON);
        } while (matchWithNextToken(TokenType.INTEGER)
                || matchWithNextToken(TokenType.CHAR)
                || matchWithNextToken(TokenType.IDENTIFIER)
        );
        return number_of_cases;
    }

    private void CaseClause() throws ParsingException {
        CaseExpression();
        int number_of_children = 1;
        while (matchWithNextToken(TokenType.COMMA)) {
            consume(TokenType.COMMA);
            CaseExpression();
            number_of_children += 1;
        }
        consume(TokenType.COLON);
        Statement();
        number_of_children += 1;
        build_tree("case_clause", number_of_children);
    }

    private void CaseExpression() throws ParsingException {
        ConstValue();
        if (matchWithNextToken(TokenType.DOTS)) {
            consume(TokenType.DOTS);
            ConstValue();
            build_tree("..", 2);
        }
    }

    private int OtherwiseClause() throws ParsingException {
        if (matchWithNextToken(TokenType.OTHERWISE)) {
            consume(TokenType.OTHERWISE);
            Statement();
            build_tree("otherwise", 1);
            return 1;
        }
        return 0;
    }

    private void Assignment() throws ParsingException {
        Name();
        if (matchWithNextToken(TokenType.ASSIGNMENT)) {
            consume(TokenType.ASSIGNMENT);
            Expression();
            build_tree("assign",2);
        } else if (matchWithNextToken(TokenType.SWAP)) {
            consume(TokenType.SWAP);
            Name();
            build_tree("swap", 2);
        } else {
            throw new ParsingException(
                "\nInvalid token on \n\tline: " + peekNextToken().getLine() +
                ", column: " + peekNextToken().getColumn() +
                ",\n\texpected: \":=\" or \":=:\"\n\treceived: \"" +
                peekNextToken().getContent() + "\"");
        }
    }

    private void ForStat() throws ParsingException {
        if (matchWithNextToken(TokenType.IDENTIFIER))
            Assignment();
        else
            build_tree("<null>", 0);
    }

    private void ForExp() throws ParsingException {
        if (matchWithNextToken(TokenType.SEMI_COLON))
            build_tree("true", 0);
        else
            Expression();
    }

    private void Expression() throws ParsingException {
        Term();
        Token token = peekNextToken();
        switch (token.getType()) {
            case LT_EQ_OP:
            case LT_OP:
            case GT_EQ_OP:
            case GT_OP:
            case EQ_OP:
            case NT_EQ_OP:
                consume(token.getType());
                Term();
                build_tree(token.getContent(), 2);
                break;
            default:
                break;
        }
    }

    private void Term() throws ParsingException {
        Factor();
        while (matchWithNextToken(TokenType.PLUS) ||
            matchWithNextToken(TokenType.MINUS) ||
            matchWithNextToken(TokenType.OR_OPR)
        ) {
            Token token = peekNextToken();
            switch (token.getType()) {
                case PLUS:
                case MINUS:
                case OR_OPR:
                    consume(token.getType());
                    Factor();
                    build_tree(token.getContent(), 2);
                    break;
                default:
                    throw new ParsingException(
                        "\nInvalid token on \n\tline: " + token.getLine() +
                        ", column: " + token.getColumn() +
                        ",\n\texpected: binary operator\n\treceived: \"" +
                        token.getContent() + "\"");
            }
        }
    }

    private void Factor() throws ParsingException {
        Primary();
        while (matchWithNextToken(TokenType.MULT) ||
            matchWithNextToken(TokenType.DIVIDE) ||
            matchWithNextToken(TokenType.AND_OPR) ||
            matchWithNextToken(TokenType.MODULUS_OPR)
        ) {
            Token token = peekNextToken();
            switch (token.getType()) {
                case MULT:
                case DIVIDE:
                case AND_OPR:
                case MODULUS_OPR:
                    consume(token.getType());
                    Primary();
                    build_tree(token.getContent(), 2);
                    break;
                default:
                    throw new ParsingException(
                        "\nInvalid token on \n\tline: " + token.getLine() +
                        ", column: " + token.getColumn() +
                        ",\n\texpected: binary operator\n\treceived: \"" +
                        token.getContent() + "\"");
            }
        }
    }

    private void Primary() throws ParsingException {
        Token token = peekNextToken();
        switch (token.getType()) {
            case IDENTIFIER:
                Name();
                if (matchWithNextToken(TokenType.OP_BRCKT)) {
                    consume(TokenType.OP_BRCKT);
                    Expression();
                    int number_of_children = 2;
                    while (matchWithNextToken(TokenType.COMMA)) {
                        consume(TokenType.COMMA);
                        Expression();
                        number_of_children += 1;
                    }
                    consume(TokenType.CL_BRCKT);
                    build_tree("call", number_of_children);
                }
                break;
            case MINUS:
            case NOT_OPR:
                consume(token.getType());
                Primary();
                build_tree(token.getContent(), 1);
                break;
            case PLUS:
                consume(TokenType.PLUS);
                Primary();
                break;
            case END_OF_FILE:
                consume(TokenType.END_OF_FILE);
                build_tree("eof", 0);
                break;
            case INTEGER:
            case CHAR:
                consume(token.getType());
                break;
            case OP_BRCKT:
                consume(TokenType.OP_BRCKT);
                Expression();
                consume(TokenType.CL_BRCKT);
                break;
            case SUCCESSOR:
            case PREDECESSOR:
            case CHAR_FUNC:
            case ORDINAL_FUNC:
                consume(token.getType());
                consume(TokenType.OP_BRCKT);
                Expression();
                consume(TokenType.CL_BRCKT);
                build_tree(token.getContent(), 1);
                break;
            default:
                break;
        }
    }

    private void Name() throws ParsingException {
        consume(TokenType.IDENTIFIER);
    }

    private void parse() throws ParsingException {
        Winzig();
    }

    public Node getParsedTree() throws ParsingException {
        if (stack.empty())
            parse();
        if (stack.size() > 1) {
            throw new ParsingException("Stack has more than one root.");
        }
        return stack.pop();
    }
}
