/*

  Author: Alexander Doyle
  Email: adoyle2025@my.fit.edu
  Course: Data Structures and Algorithms
  Section: Section E2/E3
  Description of this file: A class for tree nodes that have children and a parent

 */
import java.util.List;

/*
 * A class for tree nodes that have children and a parent
 */
class TreeNode {
    // The parent node
    private TreeNode parent;

    // The data
    private String data;

    // The children of the node
    private List<TreeNode> children;

    /*
     * Defines the node and stores the data and pointers
     */
    public TreeNode(TreeNode parent, String data, List<TreeNode> children) {
        this.parent = parent;
        this.data = data;
        this.children = children;
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
    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    /*
     * Setter method for TreeNode parent
     */
    public void setParent(TreeNode parent) {
        this.parent = parent;
    }
}
