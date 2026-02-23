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
            assertDepth();
        }
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
    }

    /*
     * Based off of the node depth, selects append or insert for the Olympic tree
     */
    public void addChild(TreeNode childNode) {
        // Ensure that the depth is within bounds
        childNode.assertDepth();

        // Select insert or append based on node rank/depth
        if (childNode.depth < 3) {
            // Insert Date, Sports, and Events
            insertChild(childNode);
        } else {
            // Append Winners
            appendChild(childNode);
        }
    }

    /*
     * Gets a child by name
     */
    public TreeNode getChildByName(String name) {
        // Iterate through list to find the child
        for (TreeNode child: children) {
            // If it is the child return it
            if (child.data.equals(name)) {
                return child;
            }
        }
        // If the child does not exist, return null
        return null;
    }

    /*
     * Adds a child to this node (place wise)
     */
    private void appendChild(TreeNode childNode) {
        this.children.add(childNode);
    }

    /*
     * Adds a child to this node (alphabetically)
     */
    private void insertChild(TreeNode childNode) {
        this.children.add(binaryIndexSearch(this.children, childNode.data), childNode);
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
    public ArrayList<TreeNode> getChildren() {
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
        // Ensure that depth is valid
        assertDepth();
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

    /*
     * Compares the string values of the node for the binaryIndexSearch function
     */
    private int compareTo(String str) {
        return this.data.compareTo(str);
    }

    /*
     * Finds the lexicographically correct index for an item to be inserted using a binary search iteratively
     */
    public static int binaryIndexSearch(ArrayList<TreeNode> array, String tgt) {
        // High var for binary search (aka right)
        int hi = array.size() - 1;

        // Ensure list is not empty
        if (hi == -1) {
            return 0;
        }

        // Low var for binary search (aka left)
        int lo = 0;

        // Mid var for binary search (aka pivot)
        int mid_int = 0;

        // Search through the list using binary search iteratively to find where to insert the element
        while (lo <= hi) {
            // Calculate mid/pivot
            mid_int = lo + (hi-lo) / 2;

            // Compare mid to tgt
            int mid_comp = array.get(mid_int).compareTo(tgt);

            // Check if target is at mid
            if (mid_comp == 0) {
                throw new IllegalStateException("Duplicate key");
            }

            // If target is greater, ignore left half
            if (mid_comp > 0) {
                lo = mid_int + 1;
            }

            // If target is smaller, ignore right half
            if (mid_comp < 0) {
                hi = mid_int - 1;
            }
        }

        // Return where to place the item in the list
        return mid_int;
    }

    /*
     * Assertation method that ensure depth is within depth bounds [0, 1, ..., 3]
     */
    private void assertDepth() {
        // Ensure that the depth is within bounds
        assert this.depth < 4;
        assert this.depth > -1;
    }

    @Override
    public String toString() {
        return this.data;
    }
}

