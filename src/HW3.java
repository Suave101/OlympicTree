/*

  Author: Alexander Doyle
  Email: adoyle2025@my.fit.edu
  Course: Data Structures and Algorithms
  Section: Section E2/E3
  Description of this file: Creates a tree to make query to for the Olympics

 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HW3
{
    public static void main(String[] args) throws FileNotFoundException {
        // Parses the file input and saves the pointer to the root node
        TreeNode root = parseInputFile("C:\\Users\\Alexander Doyle\\IdeaProjects\\OlympicTree\\src\\hw3in1data.txt");

        root.printTree();
    }

    /**
     * Parses the input file and adds entries to the tree
     */
    public static TreeNode parseInputFile(String inFile) throws FileNotFoundException {
        // The root node of the tree we are parsing
        TreeNode root = null;

        // Current parent node for each line
        TreeNode parentNode;

        // The input file object that allows for access of the input file
        File inputFile = new File(inFile);

        // Map of nodes so we can find parents
        Map<String,TreeNode> nodes = new HashMap<>();

        try (Scanner fileScanner = new Scanner(inputFile)) {

            // Loop through each line of the file
            while (fileScanner.hasNextLine()) {
                // Get next line in file
                String line = fileScanner.nextLine();

                try (Scanner lineScanner = new Scanner(line)) {
                    if (lineScanner.hasNext()) {
                        // First token is a parent
                        String parentName = lineScanner.next();

                        // The string in the file is the root
                        if (root == null) {
                            // Create root node
                            root = new TreeNode(null, parentName, null);

                            // Name root node as current parent
                            parentNode = root;

                            // Add root node to the map of nodes
                            nodes.put(parentName, root);
                        } else {
                            // Get the current parent from the tree
                            parentNode = nodes.get(parentName);
                        }

                        // Iterate through each entry in line and add them as children to the current parent node
                        while (lineScanner.hasNext()) {
                            // Create new child node
                            TreeNode child = new TreeNode(parentNode, lineScanner.next(), null);

                            // Add child node to parent
                            parentNode.appendChild(child);

                            // Add child node to the map
                            nodes.put(child.getData(), child);
                        }
                    }
                }

            }
        }
        return root;
    }

    /**
     * Parses the query file and prints the output based off of the queries
     */
    public static void parseQueryFile(String queryFile) {}

}
