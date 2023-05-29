package Node;

import java.util.ArrayList;
import java.util.List;

import Logger.Logger;

/**
 * Class representation for node in syntax tree
 *
 * @author Hirumal Priyashan
 * @version 1.0
 * @since 1.0
 */
public class Node {
    private String token;
    private List<Node> children;
    private int depth;

    public Node(String token) {
        this.token = token;
        this.children = new ArrayList<>();
        this.depth = 0;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addChild(Node child) {
        children.add(0, child);
        child.setDepth(this.depth + 1);
        for (Node ch: child.getChildren())
            ch.updateDepth(child);
    }

    private void updateDepth(Node parent) {
        this.setDepth(parent.getDepth() + 1);
        for (Node ch: this.getChildren())
            ch.updateDepth(this);
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void printNode() {
        Logger.log(this);
        for (Node node : children) {
            node.printNode();
        }
    }

    @Override
    public String toString() {
        return ". ".repeat(depth) + token + "(" +
            children.size() + ")";
    }
}
