import java.util.*;

public class KnightTourSolver extends SearchAlgorithm {

    // Enumeration to define different search strategies
    public enum Strategy {
        BFS, DFS, H1B, H2
    }

    private final Strategy strategy; // The selected strategy for solving the problem
    private static final int TIME_LIMIT = 900; // 15 minutes, in seconds
    private int createdNodes = 0; // Tracks the number of nodes created during the process

    // Constructor to initialize the KnightTourSolver
    public KnightTourSolver(int boardSize, Strategy strategy, int moveIndex) {
        super(boardSize, moveIndex); // Pass the move sequence to the superclass
        this.strategy = strategy;
    }

    @Override
    public void solve(int startX, int startY) {
        Deque<Node> frontier; // The frontier for search

        // Initialize data structures and the root node
        if (strategy == Strategy.BFS) {
            frontier = new LinkedList<>(); // Use a queue for BFS
        } else {
            frontier = new ArrayDeque<>(); // Use a stack for DFS
        }

        BitSet initialBoard = new BitSet(boardSize * boardSize); // Initialize the board
        Node root = new Node(startX, startY, initialBoard, boardSize, 0, null); // Create the root node
        frontier.add(root); // Add the root node to the frontier
        createdNodes++; // Increment the count of created nodes

        int expandedNodeCount = 0; // Tracks the number of nodes expanded
        long startTime = System.currentTimeMillis(); // Start timing

        try {
            while (!frontier.isEmpty()) {

                // Check if the time limit is exceeded
                if (System.currentTimeMillis() - startTime > TIME_LIMIT * 1000) {
                    String timeMsg = "Timeout!\nTotal nodes expanded: " + expandedNodeCount +
                            "\nTotal nodes created: " + createdNodes + "\nTime spent: " + formatTime(System.currentTimeMillis() - startTime) +
                            "\n------------------------------------------------------------------------------------------------------------------------------\n";
                    System.out.println(timeMsg);
                    Main.logger.warning(timeMsg); // Log the timeout message
                    return;
                }

                Node current;
                if (strategy == Strategy.BFS) {
                    current = frontier.pollFirst(); // Remove the node from the front of the queue
                } else {
                    current = frontier.pollLast(); // Remove the node from the back of the deque
                }
                expandedNodeCount++; // Increment the expanded node count

                // Check if the solution is found
                assert current != null;
                if (current.pathCost == boardSize * boardSize - 1) {
                    String path = buildPath(current); // Construct the solution path
                    String MSG = String.format("Solution found!\nPath: %s\nTotal nodes expanded: %d\nTotal nodes created: %d\nTime spent: %s\n",
                            path, expandedNodeCount, createdNodes, formatTime(System.currentTimeMillis() - startTime));

                    System.out.println(MSG);
                    Main.logger.info(MSG); // Log the solution message
                    return;
                }

                // Generate child nodes and add them to the frontier
                List<Node> children = generateChildren(current, strategy);
                createdNodes += children.size(); // Increment the count of created nodes
                if (strategy == Strategy.BFS) {
                    frontier.addAll(children);
                } else {
                    Collections.reverse(children); // Reverse the list for DFS
                    frontier.addAll(children);
                }
            }
            // Log a message if no solution exists
            String noSolutionMsg = "No solution exists!\nTotal nodes expanded: " + expandedNodeCount +
                    "\nTotal nodes created: " + createdNodes + "\nTime spent: " + formatTime(System.currentTimeMillis() - startTime) +
                    "\n------------------------------------------------------------------------------------------------------------------------------\n";
            System.out.println(noSolutionMsg);
            Main.logger.info(noSolutionMsg);
        } catch (OutOfMemoryError e) { // Handle memory overflow errors
            long millis = System.currentTimeMillis() - startTime;
            long seconds = millis / 1000;
            long minutes = seconds / 60;
            seconds %= 60;
            millis %= 1000;
            String outOfMemoryMsg = "Out of Memory!\nTotal nodes expanded: " + expandedNodeCount +
                    "\nTotal nodes created: " + createdNodes + "\nTime spent: " + String.format("%02d:%02d.%03d", minutes, seconds, millis) +
                    "\n------------------------------------------------------------------------------------------------------------------------------\n";
            System.out.println(outOfMemoryMsg);
            Main.logger.severe(outOfMemoryMsg);
        }
    }

    // Method to construct the path of the solution from the current node
    private String buildPath(Node current) {
        List<String> path = new ArrayList<>();
        while (current != null) {
            path.add("(" + current.x + ", " + current.y + ")");
            current = current.parent; // Traverse to the parent node
        }
        Collections.reverse(path); // Reverse the list to get the correct order
        return "[" + String.join(", ", path) + "]"; // Return the formatted path
    }

    // Method to format the time into minutes, seconds, and milliseconds
    private String formatTime(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        seconds %= 60;
        millis %= 1000;
        return String.format("%02d:%02d.%03d", minutes, seconds, millis);
    }
}
