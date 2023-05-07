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
    private Node parent;
    private int depth;

    /**
     * Class constructor.
     *
     * @param token token for the node
     */
    public Node(String token) {
        this.token = token;
        this.children = new ArrayList<>();
        this.parent = null;
        this.depth = 0;
    }

    /**
     * Class constructor.
     *
     * @param token token for the node
     * @param parent parent for the node
     */
    public Node(String token, Node parent) {
        this.token = token;
        this.parent = parent;
        this.depth = parent.getDepth() + 1;
        this.children = new ArrayList<>();
        parent.addChild(this);
    }

    /**
     * Setter for token
     *
     * @param token  token in the node
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Getter for token
     *
     * @return token in the node
     */
    public String getToken() {
        return this.token;
    }

    /**
     * Getter for children
     *
     * @return children list for the node
     */
    public List<Node> getChildren() {
        return this.children;
    }

    /**
     * Add a child to the children list
     *
     * @param child child to be added
     */
    public void addChild(Node child) {
        this.children.add(child);
        child.setDepth(this.depth + 1);
        for (Node ch: child.getChildren())
            ch.updateDepth(child);
    }

    private void updateDepth(Node parent) {
        this.setDepth(parent.getDepth() + 1);
        for (Node ch: this.getChildren())
            ch.updateDepth(this);
    }

    /**
     * Pop last children from children list
     */
    public Node popChild() {
        return this.children.remove(this.children.size() - 1);
    }

    /**
     * Setter for parent
     *
     * @param parent node to be set as parent
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Getter for depth
     *
     * @return the depth of the node
     */
    public int getDepth() {
        return this.depth;
    }

    /**
     * Setter for depth
     *
     * @param depth  the depth of the node
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * Prints the structure for the node with its successors
     */
    public void printNode() {
        Logger.log(this);
        for (Node node : this.children) {
            node.printNode();
        }
    }

    @Override
    public String toString() {
        return ". ".repeat(this.depth) + this.token + "(" + this.children.size() + ")";
    }
}
