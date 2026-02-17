/*

  Author: Alexander Doyle
  Email: adoyle2025@my.fit.edu
  Course: Data Structures and Algorithms
  Section: Section E2/E3
  Description of this file: A class for tree nodes that have children and a parent

 */
import java.util.ArrayList;
import java.util.List;

/*
 * A class for tree nodes that have children and a parent
 */
public class TreeNode {
    // The parent node
    private TreeNode parent;

    // The data
    private String data;

    // The children of the node
    private ArrayList<TreeNode> children;

    // Depth of the node
    private int depth = 0;

    /*
     * Defines the node and stores the data and pointers
     */
    public TreeNode(TreeNode parent, String data, ArrayList<TreeNode> children) {
        // Set the values to the given values
        this.parent = parent;
        this.data = data;
        this.children = children;

        // Set the depth if there is a parent node
        if (this.parent != null) {
            this.depth = parent.depth + 1;
        }
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
    }

    /*
     * Adds a child to this node (addition wise)
     */
    public void appendChild(TreeNode childNode) {
        this.children.add(childNode);
    }

    /*
     * Adds a child to this node (alphabetically)
     */
    public void insertChild(TreeNode childNode) {
        this.children.addFirst(childNode);
    }

    /*
     * Getter method for TreeNode parent
     */
    public TreeNode getParent() {
        return this.parent;
    }

    /*
     * Getter method for TreeNode Children
     */
    public List<TreeNode> getChildren() {
        return this.children;
    }

    /*
     * Getter method for TreeNode Data
     */
    public String getData() {
        return this.data;
    }

    /*
     * Setter method for TreeNode data
     */
    public void setData(String data) {
        this.data = data;
    }

    /*
     * Setter method for TreeNode children
     */
    public void setChildren(ArrayList<TreeNode> children) {
        this.children = children;
    }

    /*
     * Setter method for TreeNode parent
     */
    public void setParent(TreeNode parent) {
        this.parent = parent;
        this.depth = parent.depth + 1;
    }

    /*
     * Getter method for TreeNode depth
     */
    public int getDepth() {
        return this.depth;
    }

    /*
     * Recursively prints tree with current node as root
     */
    public void printTree() {
        System.out.println(this.data);
        for (TreeNode child: this.getChildren()) {
            child.printTree();
        }
    }
}
