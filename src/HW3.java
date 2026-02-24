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
        Tree tree = parseInputFile(args[0]);

        // Parses the query file and handles each query
        parseAndHandleQueryFile(args[1], tree.getRoot());
    }

    /**
     * Parses the input file and adds entries to the tree
     */
    public static Tree parseInputFile(String inFile) throws FileNotFoundException {
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
            root = new TreeNode(null, rootList.get(0), new ArrayList<>());

            // Remove first node (root) from the rootList
            rootList.remove(0);

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
        return new Tree(root);
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
            // Concat the string such that it matches the output requirement conventions :)
            athleteString = athleteString + " " + athlete.getData();
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

        // Print the output
        System.out.println("GetGoldMedalistAndCountryBySportAndEvent " + sport + " " + event + " " + eventNode.getChildren().get(0).getData());
        // GetGoldMedalistAndCountryBySportAndEvent sport event athlete: country
    }

    /*
     * Method that prints the athlete with the most metals
     */
    public static void getAthleteWithMostMedals(TreeNode root) {
        // Print athlete with most metals
        System.out.println("GetAthleteWithMostMedals " + getMostNMetals(root, CountBy.TOTAL, Count.ATHLETES));
    }

    /*
     * Method that prints the athlete with the most gold metals
     */
    public static void getAthleteWithMostGoldMedals(TreeNode root) {
        // Print athlete with most gold metals
        System.out.println("GetAthleteWithMostMedals " + getMostNMetals(root, CountBy.GOLDEN, Count.ATHLETES));
    }

    /*
     * Method that prints the country with the most metals
     */
    public static void getCountryWithMostMedals(TreeNode root) {
        System.out.println("GetCountryWithMostMedals " + getMostNMetals(root, CountBy.TOTAL, Count.COUNTRIES));
    }

    /*
     * Method that prints the country with the most gold metals
     */
    public static void getCountryWithMostGoldMedals(TreeNode root) {
        System.out.println("GetCountryWithMostGoldMedals " + getMostNMetals(root, CountBy.GOLDEN, Count.COUNTRIES));
    }

    /*
     * Method that prints the sport and event of an athlete
     */
    public static void getSportAndEventByAthlete(TreeNode root, String athlete) {
        // Create a list to store all names
        ArrayList<String> sportAndEventStrings = new ArrayList<>();

        // Get all athlete level child TreeNodes and add them to the list of name occurrences
        for (TreeNode sport: root.getChildren()) {
            for (TreeNode event: sport.getChildren()) {
                for (TreeNode athleteNode: event.getChildren()) {
                    if (athleteNode.getData().split(":")[0].equals(athlete)) {
                        sportAndEventStrings.add(sport.getData() + ": " + event.getData());
                    }
                }
            }
        }

        // Sort lexicographically
        Collections.sort(sportAndEventStrings);

        String sportAndEventString = "";

        for (String sportAndEvent : sportAndEventStrings) {
            sportAndEventString = sportAndEventString + sportAndEvent;
        }

        System.out.println("GetSportAndEventByAthlete " + sportAndEventString);
    }

    /*
     * Print the most (total or gold) metals per (athlete or country) depending on args
     */
    public static String getMostNMetals(TreeNode root, CountBy countBy, Count count) {
        // Create a list to store all names
        ArrayList<String> countByNames = new ArrayList<>();

        // Get all athlete level child TreeNodes and add them to the list of name occurrences
        for (TreeNode sport: root.getChildren()) {
            for (TreeNode event: sport.getChildren()) {
                // Get objects and add them to athletes list
                if (countBy == CountBy.GOLDEN) {
                    // If By gold metals, only get the first place of the event
                    if (count == Count.ATHLETES) {
                        // If by athlete, add the entire string
                        countByNames.add(event.getChildren().get(0).getData());
                    } else if (count == Count.COUNTRIES) {
                        // If by countries, only add the country
                        countByNames.add(event.getChildren().get(0).getData().split(":")[1]);
                    }
                } else if (countBy == CountBy.TOTAL) {
                    // If By total metals, iterate through all winners
                    // Store event children temporarily to iterate through
                    ArrayList<TreeNode> eventChildren = event.getChildren();
                    // Iterate through the children and add their data
                    for (TreeNode child : eventChildren) {
                        if (count == Count.COUNTRIES) {
                            // If counting by countries, only add the country
                            countByNames.add(child.getData().split(":")[1]);
                        } else if (count == Count.ATHLETES) {
                            // If counting by athlete, add the entire string
                            countByNames.add(child.getData());
                        }
                    }
                }
            }
        }

        // Ensure that there are objects
        if (countByNames.isEmpty()) {
            throw new IllegalStateException("There must exist children.");
        }

        // Sort the names alphabetically (lexicographically), grouping names for span calculation
        Collections.sort(countByNames);

        // Find the best athletes using a span calculation
        int mostMetals = 0;
        ArrayList<String> bestNames = new ArrayList<>();

        int curCount = 0;
        for (int i = 0; i < countByNames.size(); i++) {
            // Increment for metal
            curCount++;

            // Get name
            String name = countByNames.get(i);

            // Check if the streak ends in the next item
            // It ends the streak if its the last item or the next item is different
            if ((i == countByNames.size() - 1) || !name.equals(countByNames.get(i + 1))) {
                if (curCount > mostMetals) {
                    // If name better than current record
                    // Set the new record
                    mostMetals = curCount;
                    // remove all other athletes from the best
                    bestNames.clear();
                    // add the cur best name
                    bestNames.add(name);
                } else if (curCount == mostMetals) {
                    // If it is a tie, add the name to the tie
                    bestNames.add(name);
                }
                // Reset for the new name
                curCount = 0;
            }
        }

        // String to store the athlete and country list
        String bestNameString = "";

        // Iterate through athletes and add them to the string
        for (String name: bestNames) {
            if (count == Count.ATHLETES) {
                // If we are counting athletes, then we must parse the names into the correct
                // printing convention

                // Split the string at the colon
                String[] athleteCountryPair = name.split(":");

                // Concat the string such that it matches the output requirement conventions :)
                bestNameString = bestNameString + " " + athleteCountryPair[0];
            } else {
                // Else it is by country so just add the country
                // Concat the string such that it matches the output requirement conventions :)
                bestNameString = bestNameString + " " + name;
            }
        }

        // GetAthleteWithMostMedals numberOfMedals athlete [athlete2 ... in alphabetical order if ties exist]
        return mostMetals + bestNameString;
    }

    enum CountBy {
        GOLDEN,
        TOTAL
    }

    enum Count {
        COUNTRIES,
        ATHLETES
    }
}
