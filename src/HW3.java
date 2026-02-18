/*

  Author: Alexander Doyle
  Email: adoyle2025@my.fit.edu
  Course: Data Structures and Algorithms
  Section: Section E2/E3
  Description of this file: Creates a tree to make query to for the Olympics

 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class HW3
{
    public static void main(String[] args) throws FileNotFoundException {
        // Parses the file input and saves the pointer to the root node
        TreeNode root = parseInputFile("C:\\Users\\Alexander Doyle\\IdeaProjects\\OlympicTree\\src\\hw3in2data.txt");

        parseAndHandleQueryFile("C:\\Users\\Alexander Doyle\\IdeaProjects\\OlympicTree\\src\\hw3in2queries.txt", root);
    }

    /**
     * Parses the input file and adds entries to the tree
     */
    public static TreeNode parseInputFile(String inFile) throws FileNotFoundException {
        // The input file object that allows for access of the input file
        File inputFile = new File(inFile);

        // The root node of the tree we are parsing
        TreeNode root = null;

        // Current parent node for each line
        TreeNode parentNode;

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
                            parentNode.addChild(child);

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
    public static void parseAndHandleQueryFile(String queryFile, TreeNode root) throws FileNotFoundException {
        // The input file object that allows for access of the query file
        File inputFile = new File(queryFile);

        try (Scanner fileScanner = new Scanner(inputFile)) {

            // Loop through each query of the file
            while (fileScanner.hasNextLine()) {
                // Get next query in file
                String query = fileScanner.nextLine();

                // Handle the query
                handleCommand(query, root);
            }
        }
    }

    public static void handleCommand(String line, TreeNode root) {
        // Separate the command from its args
        String[] command = line.split(" ");

        // Ensure the line is not blank
        assert command.length != 0;

        // Determine which function to run based off of the command and run it with its args
        switch (command[0]) {
            case "GetEventsBySport":
                // Ensure args for command exist
                assert command.length == 2;
                // Run command with args
                getEventsBySport(root, command[1]);
                break;
            case "GetWinnersAndCountriesBySportAndEvent":
                // Ensure args for command exist
                assert command.length == 3;
                // Run command with args
                getWinnersAndCountriesBySportAndEvent(root, command[1], command[2]);
                break;
            case "GetGoldMedalistAndCountryBySportAndEvent":
                // Ensure args for command exist
                assert command.length == 3;
                // Run command with args
                getGoldMedalistAndCountryBySportAndEvent(root, command[1], command[2]);
                break;
            case "GetAthleteWithMostMedals":
                // Run command with no args
                getAthleteWithMostMedals(root);
                break;
            case "GetAthleteWithMostGoldMedals":
                // Run command with no args
                getAthleteWithMostGoldMedals(root);
                break;
            case "GetCountryWithMostMedals":
                // Run command with no args
                getCountryWithMostMedals(root);
                break;
            case "GetCountryWithMostGoldMedals":
                // Run command with no args
                getCountryWithMostGoldMedals(root);
                break;
            case "GetSportAndEventByAthlete":
                // Ensure command args exist
                assert command.length == 2;
                // Run command with args
                getSportAndEventByAthlete(root, command[1]);
                break;
            default:
                // Ensure command exists
                throw new IllegalStateException("Command must exist");
        }
    }

    /*
     * Method that prints the events within the given sport
     */
    public static void getEventsBySport(TreeNode root, String sport) {
        // Get the event nodes
        List<TreeNode> eventNodes = root.getChildByName(sport).getChildren();

        // Create a string to store the events
        String events = "";

        // Iterate through the events, adding them to the string
        for (TreeNode event: eventNodes) {
            events = events + " " + event.getData();
        }

        // Print the events by sport string
        System.out.println("GetEventsBySport " + sport + events);
    }

    /*
     * Method that prints winners and countries of the given event within the given sport
     */
    public static void getWinnersAndCountriesBySportAndEvent(TreeNode root, String sport, String event) {
        // Get the athlete nodes
        List<TreeNode> athleteNodes = root.getChildByName(sport).getChildByName(event).getChildren();

        // String to store the athlete and country list
        String athleteString = "";

        // Iterate through athlete nodes and add them to the string
        for (TreeNode athlete: athleteNodes) {
            // Split the string at the colon
            String[] athleteCountryPair = athlete.getData().split(":");

            // Concat the string such that it matches the output requirement conventions :)
            athleteString = athleteString + " " + athleteCountryPair[0] + ": " + athleteCountryPair[1];
        }

        // Prints the winners and countries by sport and event
        System.out.println("GetWinnersAndCountriesBySportAndEvent " + sport + " " + event + athleteString);
        // GetWinnersAndCountriesBySportAndEvent sport event athleteG: countryG athleteS: countryS athleteB: countryB
    }

    /*
     * Method that prints the gold medalist and country of the given event within the given sport
     */
    public static void getGoldMedalistAndCountryBySportAndEvent(TreeNode root, String sport, String event) {
        // Get the sport node
        TreeNode sportNode = root.getChildByName(sport);

        // Get event node
        TreeNode eventNode = sportNode.getChildByName(event);

        // Get the gold medalist name and country and split it for concatenation
        String[] goldMedalistAthleteCountryPair = eventNode.getChildren().get(0).getData().split(":");

        // Concat the string the correct way
        String goldMedalist = goldMedalistAthleteCountryPair[0] + ": " + goldMedalistAthleteCountryPair[1];

        // Print the output
        System.out.println("GetGoldMedalistAndCountryBySportAndEvent " + sport + " " + event + goldMedalist);
        // GetGoldMedalistAndCountryBySportAndEvent sport event athlete: country
    }

    /*
     * Method that prints the athlete with the most metals
     */
    public static void getAthleteWithMostMedals(TreeNode root) {
        // GetAthleteWithMostMedals numberOfMedals athlete [athlete2 ... in alphabetical order if ties exist]
        System.out.println("");
    }

    /*
     * Method that prints the athlete with the most gold metals
     */
    public static void getAthleteWithMostGoldMedals(TreeNode root) {
        // GetAthleteWithMostGoldMedals numberOfGoldMedals athlete [athlete2 ... in alphabetical order if ties exist]
        System.out.println("");
    }

    /*
     * Method that prints the country with the most metals
     */
    public static void getCountryWithMostMedals(TreeNode root) {
        // GetCountryWithMostMedals numberOfMedals country [country2 ... in alphabetical order if ties exist]
        System.out.println("");
    }

    /*
     * Method that prints the country with the most gold metals
     */
    public static void getCountryWithMostGoldMedals(TreeNode root) {
        // GetCountryWithMostGoldMedals numberOfGoldMedals country [country2 ... in alphabetical order if ties exist]
        System.out.println("");
    }

    /*
     * Method that prints the sport and event of an athlete
     */
    public static void getSportAndEventByAthlete(TreeNode root, String athlete) {
        // GetSportAndEventByAthlete athlete sport1: event1 ... in alphabetical order [none]
        System.out.println("");
    }
}
