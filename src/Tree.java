public class Tree {
    // A storage variable for the tree's root
    private TreeNode root;

    // Constructor method that creates a tree with root, root
    public Tree(TreeNode root) {
        // Defines root as the root given
        this.root = root;
    }

    // Allows the user to define a new root
    public void setRoot(TreeNode root) {
        this.root = root;
    }

    // Allows the user to get the current root
    public TreeNode getRoot() {
        return this.root;
    }
}
