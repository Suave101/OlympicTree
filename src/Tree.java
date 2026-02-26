/*

  Author: Alexander Doyle
  Email: adoyle2025@my.fit.edu
  Course: Data Structures and Algorithms
  Section: Section E2
  Description of this file: A tree class to store the root as required by the instruction file

 */

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
