import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.*;

public class Main {
    protected static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        configureLogger();

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter board size (n): ");
        int n = sc.nextInt();
        logger.info("Board size: " + n);

        System.out.print("Enter starting X coordinate (1-based): ");
        int startX = sc.nextInt();

        System.out.print("Enter starting Y coordinate (1-based): ");
        int startY = sc.nextInt();
        logger.info("Starting position: (" + startX + ", " + startY + ")");

        System.out.println("Available move sets:");
        for (int i = 0; i < SearchAlgorithm.MOVES_DX.length; i++) {
            System.out.println("Index " + i + ":");
            System.out.println("dx: " + arrayToString(SearchAlgorithm.MOVES_DX[i]));
            System.out.println("dy: " + arrayToString(SearchAlgorithm.MOVES_DY[i]));
            System.out.println();
        }

        System.out.print("Choose move set index (0-4): ");
        int moveIndex = sc.nextInt();
        logger.info("Move set index: X" + Arrays.toString(SearchAlgorithm.MOVES_DX[moveIndex]));
        logger.info("Move set index: Y" + Arrays.toString(SearchAlgorithm.MOVES_DY[moveIndex]));
        System.out.println();

        System.out.print("Choose algorithm (1 for BFS, 2 for DFS, 3 for DFS with h1b, 4 for DFS with h2): ");
        int choice = sc.nextInt();

        KnightTourSolver.Strategy strategy = null;

        // Kullanıcı seçimlerini kontrol et
        switch (choice) {
            case 1:
                strategy = KnightTourSolver.Strategy.BFS;
                break;
            case 2:
                strategy = KnightTourSolver.Strategy.DFS;
                break;
            case 3:
                strategy = KnightTourSolver.Strategy.H1B;
                break;
            case 4:
                strategy = KnightTourSolver.Strategy.H2;
                break;
            default:
                System.out.println("Invalid choice! Exiting...");
                logger.warning("Invalid algorithm choice. Exiting...");
                return;
        }
        logger.info("Algorithm choice: " + strategy);

        // KnightTourSolver başlat ve çözümü çalıştır
        KnightTourSolver solver = new KnightTourSolver(n, strategy, moveIndex);
        solver.solve(startX, startY);
    }

    private static void configureLogger() {
        try {
            FileHandler fileHandler = new FileHandler("max.log", true);
            fileHandler.setFormatter(new SimpleFormatter() {
                @Override
                public String format(LogRecord record) {
                    if (record.getMessage().startsWith("========== New Run")) {
                        return String.format("%s%n", record.getMessage());
                    }
                    return String.format("%s %s: %s%n", java.time.LocalTime.now(), record.getLevel(), record.getMessage());
                }
            });
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            System.err.println("Failed to configure logger: " + e.getMessage());
        }
    }

    private static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int num : array) {
            sb.append(num).append(" ");
        }
        return sb.toString().trim();
    }
}
