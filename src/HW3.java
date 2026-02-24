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
//        // Parses the file input and saves the pointer to the root node
//        TreeNode root = parseInputFile(args[0]);
//
//        parseAndHandleQueryFile(args[1], root);
        // Debuggin
        TreeNode root = parseInputFile("C:\\Users\\Alexander Doyle\\IdeaProjects\\OlympicTree\\src\\hw3in2data.txt");
        parseAndHandleQueryFile("C:\\Users\\Alexander Doyle\\IdeaProjects\\OlympicTree\\src\\hw3in2queries.txt", root);
        // TODO: UNDEBUG SCRIPT
    }

    /**
     * Parses the input file and adds entries to the tree
     */
    public static TreeNode parseInputFile(String inFile) throws FileNotFoundException {
        // The input file object that allows for access of the input file
        File inputFile = new File(inFile);

        // The root node of the tree we are parsing
        TreeNode root;

        // Current parent node for each line
        TreeNode parentNode = null;

        // Current branch nodes to be parents
        ArrayList<TreeNode> curNodes = new ArrayList<>();

        try (Scanner fileScanner = new Scanner(inputFile)) {
            // --- Handle root node ---

            // Get root line in file and parse it into
            ArrayList<String> rootList = new ArrayList<>(Arrays.asList(fileScanner.nextLine().split(" ")));

            // Create root node
            root = new TreeNode(null, rootList.getFirst(), new ArrayList<>());

            // Remove first node (root) from the rootList
            rootList.removeFirst();

            // Add children (sports) to root
            for (String rootChild: rootList) {
                root.addChild(new TreeNode(root, rootChild, null));
            }

            ArrayList<TreeNode> parentNodes = new ArrayList<>(root.getChildren());

            // Loop through each line of the file after the root line
            while (fileScanner.hasNextLine()) {
                // Get next line in file
                String line = fileScanner.nextLine();

                try (Scanner lineScanner = new Scanner(line)) {
                    if (lineScanner.hasNext()) {
                        // First token is a parent
                        String parentName = lineScanner.next();

                        // Find the parent node in the parent nodes
                        for (TreeNode node: parentNodes) {
                            if (node.getData().equals(parentName)) {
                                parentNode = node;
                                break;
                            }
                        }

                        // Check if the depth has changed
                        if (parentNode == null) {
                            // Clear old parents, Add nodes to be parent nodes, and Clear old cur nodes
                            parentNodes.clear();
                            parentNodes.addAll(curNodes);
                            curNodes.clear();

                            // Get the parent node from the next layer
                            for (TreeNode node: parentNodes) {
                                if (node.getData().equals(parentName)) {
                                    parentNode = node;
                                    break;
                                }
                            }
                            if (parentNode == null) {
                                throw new IllegalStateException("Parent " + parentName + " does not exist!");
                            }
                        }

                        // Iterate through each entry in line and add them as children to the current parent node
                        while (lineScanner.hasNext()) {
                            // Create new child node
                            TreeNode child = new TreeNode(parentNode, lineScanner.next(), null);

                            // Add child node to parent
                            parentNode.addChild(child);
                        }
                        // Add nodes to be parent nodes
                        curNodes.addAll(parentNode.getChildren());

                        // Empty the parent node
                        parentNode = null;
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
        System.out.println(sport);
        System.out.println(root.getChildren());
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
        // Get Sports
        List<TreeNode> sports = root.getChildren();

        // Create a map var with the node and the number of occurrences
        Map<String, Integer> athleteMap = new HashMap<>();

        // Get all athlete nodes and map them to the number of times they occur
        for (TreeNode sport: sports) {
            for (TreeNode event: sport.getChildren()) {
                for (TreeNode athlete: event.getChildren()) {
                    // Get athlete name
                    String athleteStr = athlete.getData();
                    // If athlete isn't mapped yet, add the athlete to the map
                    if (!athleteMap.containsKey(athleteStr)) {
                        athleteMap.put(athleteStr, 1);
                    } else {
                        // Else, increment the athlete's metal count
                        athleteMap.put(athleteStr, athleteMap.get(athleteStr) + 1);
                    }
                }
            }
        }

        // Most metals storage var
        int mostMetals = -1;

        // List of best athletes
        ArrayList<String> bestAthletes = new ArrayList<>();

        // For each athlete, find the one with the most metals
        for (String athlete: athleteMap.keySet()) {
            // Store the athletes number of metals
            int numberOfMetals = athleteMap.get(athlete);

            // If the athlete has more metals, empty the best athletes and add this athlete
            if (numberOfMetals > mostMetals) {
                bestAthletes.clear();
                bestAthletes.add(athlete);
                mostMetals = numberOfMetals;
            } else if (numberOfMetals == mostMetals) {
                // If athlete ties, add the athlete to the bestAthletes lexiographically
                bestAthletes.add(binaryIndexSearch(bestAthletes, athlete), athlete);
            }
            // If not better or equal, do nothing
        }

        // Parse the strings

        // String to store the athlete and country list
        String bestAthleteString = "";

        // Iterate through athletes and add them to the string
        for (String athlete: bestAthletes) {
            // Split the string at the colon
            String[] athleteCountryPair = athlete.split(":");

            // Concat the string such that it matches the output requirement conventions :)
            bestAthleteString = bestAthleteString + " " + athleteCountryPair[0];
        }

        // GetAthleteWithMostMedals numberOfMedals athlete [athlete2 ... in alphabetical order if ties exist]
        System.out.println("GetAthleteWithMostMedals " + mostMetals + bestAthleteString);
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

    /*
     * Finds the lexicographically correct index for an item to be inserted using a binary search iteratively
     */
    public static int binaryIndexSearch(ArrayList<String> array, String tgt) {
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
}
