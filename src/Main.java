import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.*;

public class Main {
    // Define a logger to record information and events
    protected static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        configureLogger(); // Configure the logger

        Scanner sc = new Scanner(System.in); // Create a scanner for user input

        // Prompt the user to enter the size of the chessboard
        System.out.print("Enter board size (n): ");
        int n = sc.nextInt();
        logger.info("Board size: " + n); // Log the board size

        // Prompt the user to enter the starting X coordinate
        System.out.print("Enter starting X coordinate (1-based): ");
        int startX = sc.nextInt();

        // Prompt the user to enter the starting Y coordinate
        System.out.print("Enter starting Y coordinate (1-based): ");
        int startY = sc.nextInt();
        logger.info("Starting position: (" + startX + ", " + startY + ")"); // Log the starting position

        // Display the available sets of moves
        System.out.println("Available move sets:");
        for (int i = 0; i < SearchAlgorithm.MOVES_DX.length; i++) {
            System.out.println("Index " + i + ":");
            System.out.println("dx: " + Arrays.toString(SearchAlgorithm.MOVES_DX[i]));
            System.out.println("dy: " + Arrays.toString(SearchAlgorithm.MOVES_DY[i]));
            System.out.println();
        }

        // Prompt the user to choose a move set by index
        System.out.print("Choose move set index (0-4): ");
        int moveIndex = sc.nextInt();
        logger.info("Move set index: X" + Arrays.toString(SearchAlgorithm.MOVES_DX[moveIndex])); // Log the chosen move set (dx)
        logger.info("Move set index: Y" + Arrays.toString(SearchAlgorithm.MOVES_DY[moveIndex])); // Log the chosen move set (dy)
        System.out.println();

        // Prompt the user to select the algorithm for solving the Knight's Tour problem
        System.out.print("Choose algorithm (1 for BFS, 2 for DFS, 3 for DFS with h1b, 4 for DFS with h2): ");
        int choice = sc.nextInt();

        // Initialize the strategy variable to hold the user's algorithm choice
        KnightTourSolver.Strategy strategy;

        // Validate the user's algorithm choice and set the strategy
        switch (choice) {
            case 1:
                strategy = KnightTourSolver.Strategy.BFS; // Use Breadth-First Search (BFS)
                break;
            case 2:
                strategy = KnightTourSolver.Strategy.DFS; // Use Depth-First Search (DFS)
                break;
            case 3:
                strategy = KnightTourSolver.Strategy.H1B; // Use DFS with heuristic h1b
                break;
            case 4:
                strategy = KnightTourSolver.Strategy.H2; // Use DFS with heuristic h2
                break;
            default:
                System.out.println("Invalid choice! Exiting..."); // Handle invalid input
                logger.warning("Invalid algorithm choice. Exiting...");
                return; // Exit the program
        }
        logger.info("Algorithm choice: " + strategy); // Log the chosen algorithm

        // Initialize the KnightTourSolver and execute the solution
        KnightTourSolver solver = new KnightTourSolver(n, strategy, moveIndex);
        solver.solve(startX, startY); // Solve the Knight's Tour starting from the given position
    }

    private static void configureLogger() {
        try {
            // Set up a file handler for logging, appending to the "MaxSizeTest.log" file
            FileHandler fileHandler = new FileHandler("MaxSizeTest.log", true);
            fileHandler.setFormatter(new SimpleFormatter() {
                @Override
                public String format(LogRecord record) {
                    // Format the log message
                    if (record.getMessage().startsWith("========== New Run")) {
                        return String.format("%s%n", record.getMessage());
                    }
                    return String.format("%s %s: %s%n",
                            java.time.LocalTime.now(),
                            record.getLevel(),
                            record.getMessage());
                }
            });
            logger.addHandler(fileHandler); // Add the file handler to the logger
            logger.setUseParentHandlers(false); // Disable logging to the console
        } catch (IOException e) {
            // Handle exceptions during logger configuration
            System.err.println("Failed to configure logger: " + e.getMessage());
        }
    }
}
