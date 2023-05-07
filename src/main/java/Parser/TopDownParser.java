package Parser;

import Lexer.Tokens.*;
import MyExeptions.ParsingException;
import Node.Node;

import java.util.List;

public class TopDownParser {
    private final List<Token> tokens;
    private int nextTokenId;
    private Node root;

    public TopDownParser(List<Token> tokens) {
        this.tokens = tokens;
        this.nextTokenId = 0;
        this.root = null;
    }

    private boolean hasNext() {
        return nextTokenId <= tokens.size() - 1;
    }

    private Token getNextToken() {
        Token token = null;
        if (this.hasNext())
            token = tokens.get(nextTokenId);
        ++nextTokenId;
        return token;
    }

    private Token peekNextToken() throws ParsingException {
        if (this.hasNext())
            return tokens.get(nextTokenId);
        throw new ParsingException("Token list is over, without completing the parsing");
    }

    private boolean matchWithNextToken(String expected) throws ParsingException {
        return this.peekNextToken().getContent().equals(expected);
    }

    private void consume(String token) throws ParsingException {
        Token nextToken = this.getNextToken();
        if (nextToken == null)
            throw new ParsingException("\nToken list is over, \n\texpected: \"" + token + "\"");
        if (!nextToken.getContent().equals(token))
            throw new ParsingException("\nInvalid token, \n\texpected: \"" + token + "\",\n\treceived: \""
                    + nextToken.getContent() + "\"");
    }

    private void consume(Node parent, Class<?> type) throws ParsingException {
        Token nextToken = this.getNextToken();
        if (nextToken == null)
            throw new ParsingException("\nToken list is over, \n\texpected: \"" + type.getName() + "\"");
        if (!(nextToken.getClass().equals(type)))
            throw new ParsingException("\nInvalid token, \n\texpected: \"" + type.getName() + "\",\n\treceived: \""
                    + nextToken.getClass().getName() + "\"");

        Node node = new Node(nextToken.getContentForNode(), parent);
        Node value = new Node(nextToken.getContent(), node);
    }

    private void Winzig() throws ParsingException {
        Node programNode = new Node("program");
        this.root = programNode;
        consume("program");
        Name(programNode);
        consume(":");
        Consts(programNode);
        Types(programNode);
        Dclns(programNode);
        SubProgs(programNode);
        Body(programNode);
        Name(programNode);
        consume(".");
    }

    private void Consts(Node parent) throws ParsingException {
        Node constsNode = new Node("consts", parent);
        if (this.matchWithNextToken("consts")) {
            consume("const");
            Const(constsNode);
            while (this.matchWithNextToken(",")) {
                consume(",");
                Const(constsNode);
            }
            consume(";");
        }
    }

    private void Const(Node parent) throws ParsingException {
        Node constNode = new Node("const", parent);
        Name(constNode);
        consume("=");
        ConstValue(constNode);
    }

    private void ConstValue(Node parent) throws ParsingException {
        Token nextToken = this.peekNextToken();
        if (nextToken instanceof IntegerToken)
            consume(parent, IntegerToken.class);
        else if (nextToken instanceof CharToken)
            consume(parent, CharToken.class);
        else if (nextToken instanceof IdentifierToken)
            consume(parent, IdentifierToken.class);
        else
            throw new ParsingException("\nInvalid token: \"" + nextToken + "\"");
    }

    private void Types(Node parent) throws ParsingException {
        Node typesNode = new Node("types", parent);
        if (this.matchWithNextToken("type")) {
            consume("type");
            do {
                Type(typesNode);
                consume(";");
            } while (this.peekNextToken() instanceof IdentifierToken);
        }
    }

    private void Type(Node parent) throws ParsingException {
        Node typeNode = new Node("type", parent);
        Name(typeNode);
        consume("=");
        LitList(typeNode);
    }

    private void LitList(Node parent) throws ParsingException {
        Node litNode = new Node("lit", parent);
        consume("(");
        Name(litNode);
        while (this.matchWithNextToken(",")) {
            consume(",");
            Name(litNode);
        }
        consume(")");
    }

    private void SubProgs(Node parent) throws ParsingException {
        Node subProgsNode = new Node("subprogs", parent);
        while (this.matchWithNextToken("function"))
            Fcn(subProgsNode);
    }

    private void Fcn(Node parent) throws ParsingException {
        Node fcnNode = new Node("fcn", parent);
        consume("function");
        Name(fcnNode);
        consume("(");
        Params(fcnNode);
        consume(")");
        consume(":");
        Name(fcnNode);
        consume(";");
        Consts(fcnNode);
        Types(fcnNode);
        Dclns(fcnNode);
        Body(fcnNode);
        Name(fcnNode);
        consume(";");
    }

    private void Params(Node parent) throws ParsingException {
        Node paramsNode = new Node("params", parent);
        Dcln(paramsNode);
        while (this.matchWithNextToken(";")) {
            consume(";");
            Dcln(paramsNode);
        }
    }

    private void Dclns(Node parent) throws ParsingException {
        Node dclnsNode = new Node("dclns", parent);
        if (this.matchWithNextToken("var")) {
            consume("var");
            do {
                Dcln(dclnsNode);
                consume(";");
            } while (this.peekNextToken() instanceof IdentifierToken);
        }
    }

    private void Dcln(Node parent) throws ParsingException {
        Node dclnNode = new Node("var", parent);
        Name(dclnNode);
        while (!this.matchWithNextToken(":")) {
            consume(",");
            Name(dclnNode);
        }
        consume(":");
        Name(dclnNode);
    }

    private void Body(Node parent) throws ParsingException {
        Node bodyNode = new Node("block", parent);
        consume("begin");
        Statement(bodyNode);
        while (this.matchWithNextToken(";")) {
            consume(";");
            Statement(bodyNode);
        }
        consume("end");
    }

    private void Statement(Node parent) throws ParsingException {
        Token nextToken = this.peekNextToken();
        if (nextToken instanceof IdentifierToken) {
            Assignment(parent);
            return;
        }
        switch (nextToken.getContent()) {
            case "output":
                Node outputNode = new Node("output", parent);
                consume("output");
                consume("(");
                OutExp(outputNode);
                while (this.matchWithNextToken(",")) {
                    consume(",");
                    OutExp(outputNode);
                }
                consume(")");
                break;
            case "if":
                Node ifNode = new Node("if", parent);
                consume("if");
                Expression(ifNode);
                consume("then");
                Statement(ifNode);
                if (this.matchWithNextToken("else")) {
                    consume("else");
                    Statement(ifNode);
                }
                break;
            case "while":
                Node whileNode = new Node("while", parent);
                consume("while");
                Expression(whileNode);
                consume("do");
                Statement(whileNode);
                break;
            case "repeat":
                Node repeatNode = new Node("repeat", parent);
                consume("repeat");
                Statement(repeatNode);
                while (this.matchWithNextToken(";")) {
                    consume(";");
                    Statement(repeatNode);
                }
                consume("until");
                Expression(repeatNode);
                break;
            case "for":
                Node forNode = new Node("for", parent);
                consume("for");
                consume("(");
                ForStat(forNode);
                consume(";");
                ForExp(forNode);
                consume(";");
                ForStat(forNode);
                consume(")");
                Statement(forNode);
                break;
            case "loop":
                Node loopNode = new Node("loop", parent);
                consume("loop");
                Statement(loopNode);
                while (this.matchWithNextToken(";")) {
                    consume(";");
                    Statement(loopNode);
                }
                consume("pool");
                break;
            case "case":
                Node caseNode = new Node("case", parent);
                consume("case");
                Expression(caseNode);
                consume("of");
                CaseClauses(caseNode);
                OtherwiseClause(caseNode);
                consume("end");
                break;
            case "read":
                Node readNode = new Node("read", parent);
                consume("read");
                consume("(");
                Name(readNode);
                while (this.matchWithNextToken(",")) {
                    consume(",");
                    Name(readNode);
                }
                consume(")");
                break;
            case "exit":
                Node exitNode = new Node("exit", parent);
                consume("exit");
                break;
            case "return":
                Node returnNode = new Node("return", parent);
                consume("return");
                Expression(returnNode);
                break;
            case "begin":
                Body(parent);
                break;
            default:
                Node nullNode = new Node("<null>", parent);
                break;
        }
    }

    private void OutExp(Node parent) throws ParsingException {
        Token nextToken = this.peekNextToken();
        if (nextToken instanceof StringToken) {
            Node stringNode = new Node("string", parent);
            StringNode(stringNode);
        } else {
            Node integerNode = new Node("integer", parent);
            Expression(integerNode);
        }
    }

    private void StringNode(Node parent) throws ParsingException {
        Token nextToken = this.peekNextToken();
        if (nextToken instanceof StringToken)
            consume(parent, StringToken.class);
        else
            throw new ParsingException("\nInvalid token: \"" + nextToken + "\"");
    }

    private void CaseClauses(Node parent) throws ParsingException {
        do {
            CaseClause(parent);
            consume(";");
        } while (this.peekNextToken() instanceof IntegerToken
                || this.peekNextToken() instanceof CharToken
                || this.peekNextToken() instanceof IdentifierToken
        );
    }

    private void CaseClause(Node parent) throws ParsingException {
        Node caseClauseNode = new Node("case_clause", parent);
        CaseExpression(caseClauseNode);
        while (this.matchWithNextToken(",")) {
            consume(",");
            CaseExpression(caseClauseNode);
        }
        consume(":");
        Statement(caseClauseNode);
    }

    private void CaseExpression(Node parent) throws ParsingException {
        ConstValue(parent);
        if (this.matchWithNextToken("..")){
            Node popChild = parent.popChild();
            Node dotsNode = new Node("..", parent);
            popChild.setParent(dotsNode);
            popChild.setDepth(dotsNode.getDepth() + 1);
            dotsNode.addChild(popChild);
            consume("..");
            ConstValue(dotsNode);
        }
    }

    private void OtherwiseClause(Node parent) throws ParsingException {
        if (this.matchWithNextToken("otherwise")) {
            Node otherwiseNode = new Node("otherwise", parent);
            consume("otherwise");
            Statement(otherwiseNode);
        }
    }

    private void Assignment(Node parent) throws ParsingException {
        Node node = new Node("dummy", parent);
        Name(node);
        if (this.matchWithNextToken(":=")) {
            node.setToken("assign");
            consume(":=");
            Expression(node);
        } else if (this.matchWithNextToken(":=:")) {
            node.setToken("swap");
            consume(":=:");
            Name(node);
        } else {
            throw new ParsingException("\nUnexpected token\n\texpected: \":=\" or \":=:\"\n\treceived: \"" + this.peekNextToken() + "\"");
        }
    }

    private void ForStat(Node parent) throws ParsingException {
        if (this.peekNextToken() instanceof IdentifierToken) {
            Assignment(parent);
        }
        else {
            Node nullNode = new Node("<null>", parent);
        }
    }

    private void ForExp(Node parent) throws ParsingException {
        if (this.matchWithNextToken(";")) {
            Node trueNode = new Node("true", parent);
        }
        else {
            Expression(parent);
        }
    }

    private void Expression(Node parent) throws ParsingException {
        Term(parent);
        String nextToken = this.peekNextToken().getContent();
        switch (nextToken) {
            case "<=":
            case "<":
            case ">=":
            case ">":
            case "=":
            case "<>":
                Node popChild = parent.popChild();
                Node node = new Node(nextToken, parent);
                popChild.setParent(node);
                popChild.setDepth(node.getDepth() + 1);
                node.addChild(popChild);
                consume(nextToken);
                Term(node);
                break;
            default:
                break;
        }
    }

    private void Term(Node parent) throws ParsingException {
        Factor(parent);
        String nextToken = this.peekNextToken().getContent();
        while (nextToken.equals("+") || nextToken.equals("-") || nextToken.equals("or")) {
            switch (nextToken) {
                case "+":
                case "-":
                case "or":
                    Node popChild = parent.popChild();
                    Node node = new Node(nextToken, parent);
                    popChild.setParent(node);
                    popChild.setDepth(node.getDepth() + 1);
                    node.addChild(popChild);
                    consume(nextToken);
                    Factor(node);
                    break;
                default:
                    throw new ParsingException("\nUnexpected token\n\texpected: binary operator\n\treceived: \"" + this.peekNextToken() + "\"");
            }
            nextToken = this.peekNextToken().getContent();
        }
    }

    private void Factor(Node parent) throws ParsingException {
        Primary(parent);
        String nextToken = this.peekNextToken().getContent();
        while (nextToken.equals("*") || nextToken.equals("/")
                || nextToken.equals("and") || nextToken.equals("mod")) {
            switch (nextToken) {
                case "*":
                case "/":
                case "and":
                case "mod":
                    Node popChild = parent.popChild();
                    Node node = new Node(nextToken, parent);
                    popChild.setParent(node);
                    popChild.setDepth(node.getDepth() + 1);
                    node.addChild(popChild);
                    consume(nextToken);
                    Primary(node);
                    break;
                default:
                    throw new ParsingException("\nUnexpected token\n\texpected: binary operator\n\treceived: \"" + this.peekNextToken() + "\"");
            }
            nextToken = this.peekNextToken().getContent();
        }
    }

    private void Primary(Node parent) throws ParsingException {
        Token nextToken = this.peekNextToken();
        if (nextToken instanceof IdentifierToken) {
            Name(parent);
            if (this.matchWithNextToken("(")) {
                Node popChild = parent.popChild();
                Node node = new Node("call", parent);
                popChild.setParent(node);
                popChild.setDepth(node.getDepth() + 1);
                node.addChild(popChild);
                consume("(");
                Expression(node);
                while (this.matchWithNextToken(",")) {
                    consume(",");
                    Expression(node);
                }
                consume(")");
            }
            return;
        } else if (nextToken instanceof IntegerToken) {
            consume(parent, IntegerToken.class);
            return;
        } else if (nextToken instanceof CharToken) {
            consume(parent, CharToken.class);
            return;
        }

        String token = nextToken.getContent();
        switch (token) {
            case "-":
            case "not":
                Node notNode = new Node(token, parent);
                consume(token);
                Primary(notNode);
                break;
            case "+":
                consume(token);
                Primary(parent);
                break;
            case "eof":
                Node eofNode = new Node(token, parent);
                consume(token);
                break;
            case "succ":
            case "pred":
            case "chr":
            case "ord":
                Node node = new Node(token, parent);
                consume(token);
                consume("(");
                Expression(node);
                consume(")");
                break;
            case "(":
                consume("(");
                Expression(parent);
                consume(")");
            default:
                break;
        }
    }

    private void Name(Node parent) throws ParsingException {
        Token nextToken = this.peekNextToken();
        if (nextToken instanceof IdentifierToken)
            consume(parent, IdentifierToken.class);
        else
            throw new ParsingException("\nInvalid token: \"" + nextToken + "\"");
    }

    private void parse() throws ParsingException {
        Winzig();
    }

    public Node getParsedTree() throws ParsingException {
        if (this.root == null)
            this.parse();
        return this.root;
    }
}
