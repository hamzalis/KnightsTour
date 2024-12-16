import java.util.*;
import java.util.logging.*;

public class KnightTourSolver extends SearchAlgorithm {

    public enum Strategy {
        BFS, DFS, H1B, H2
    }

    private Strategy strategy;
    private static final int TIME_LIMIT = 900; // 15 dakika, saniye cinsinden
    private int createdNodes = 0; // Oluşturulan düğüm sayısını takip etmek için

    public KnightTourSolver(int boardSize, Strategy strategy, int moveIndex) {
        super(boardSize, moveIndex); // Hareket dizisini üst sınıfa geçir
        this.strategy = strategy;
    }

    @Override
    public void solve(int startX, int startY) {
        Deque<Node> frontier;

        // Ortak veri yapıları ve başlangıç düğümü
        if (strategy == Strategy.BFS) {
            frontier = new LinkedList<>();
        } else {
            frontier = new ArrayDeque<>();
        }

        BitSet initialBoard = new BitSet(boardSize * boardSize);
        Node root = new Node(startX, startY, initialBoard, boardSize, 0, new ArrayList<>(),null);
        frontier.add(root);
        createdNodes++; // İlk düğüm oluşturuldu

        int expandedNodeCount = 0; // Genişletilen düğümleri takip etmek için
        long startTime = System.currentTimeMillis();

        try {
            while (!frontier.isEmpty()) {


                if (System.currentTimeMillis() - startTime > TIME_LIMIT * 1000) { // Zaman sınırı saniye cinsinden alındıysa
                    String timeMsg = "Timeout!\nTotal nodes expanded: " + expandedNodeCount +
                            "\nTotal nodes created: " + createdNodes + "\nTime spent: "+ formatTime(System.currentTimeMillis() - startTime) + "\n------------------------------------------------------------------------------------------------------------------------------\n";
                    System.out.println(timeMsg);
                    Main.logger.warning(timeMsg);
                    return;
                }

                Node current;
                if (strategy == Strategy.BFS) {
                    current = frontier.pollFirst(); // Kuyruğun başından al
                } else {
                    current = frontier.pollLast(); // Kuyruğun sonundan al
                }
                expandedNodeCount++;

                if (current.pathCost == boardSize * boardSize - 1) {
                    String MSG = "Solution found!\nPath: " + current.path + "\nTotal nodes expanded: " + expandedNodeCount +
                            "\nTotal nodes created: " + createdNodes + "\nTime spent: " + formatTime(System.currentTimeMillis() - startTime) + "\n------------------------------------------------------------------------------------------------------------------------------\n";
                    printPath(current);
                    System.out.println(MSG);
                    Main.logger.info(MSG);
                    return;
                }

                // Çocuk düğümleri oluştur ve frontier'a ekle
                List<Node> children = generateChildren(current, strategy);
                createdNodes += children.size(); // Oluşturulan düğüm sayısını artır
                if (strategy == Strategy.BFS) {
                    frontier.addAll(children);
                } else {
                    Collections.reverse(children);
                    frontier.addAll(children);
                }
            }
            String noSolutionMsg = "No solution exists!\nTotal nodes expanded: " + expandedNodeCount +
                    "\nTotal nodes created: " + createdNodes + "\nTime spent: "+ formatTime(System.currentTimeMillis() - startTime) + "\n------------------------------------------------------------------------------------------------------------------------------\n";
            System.out.println(noSolutionMsg);
            Main.logger.info(noSolutionMsg);
        } catch (OutOfMemoryError e) {
            long millis = System.currentTimeMillis() - startTime;
            long seconds = millis / 1000;
            long minutes = seconds / 60;
            seconds %= 60;
            millis %= 1000;
            String outOfMemoryMsg = "Out of Memory!\nTotal nodes expanded: " + expandedNodeCount +
                    "\nTotal nodes created: " + createdNodes + "\nTime spent: "+ String.format("%02d:%02d.%03d", minutes, seconds, millis) + "\n------------------------------------------------------------------------------------------------------------------------------\n";
            System.out.println(outOfMemoryMsg);
            Main.logger.severe(outOfMemoryMsg);
        }
    }

    private void printPath(Node current){
        while (current != null){
            System.out.println(current.x + " " + current.y);
            current = current.parent;
        }
    }

    private String formatTime(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        seconds %= 60;
        millis %= 1000;
        return String.format("%02d:%02d.%03d", minutes, seconds, millis);
    }
}
