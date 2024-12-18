import java.util.BitSet;
import java.util.List;

public class Node {
    public int x, y; // The current position of the knight
    public BitSet board; // Represents the visited squares on the board
    public int boardSize; // The size of the board (NxN)
    public int pathCost; // The number of moves made
    public List<String> path; // The move history
    public Node parent; // The parent node

    // Constructor to initialize the node
    public Node(int x, int y, BitSet board, int boardSize, int pathCost, Node parent) {
        this.x = x;
        this.y = y;
        this.boardSize = boardSize;
        this.board = (BitSet) board.clone(); // Clone the board to preserve the original
        this.board.set((x - 1) * boardSize + (y - 1)); // Mark the current position as visited
        this.pathCost = pathCost; // Record the number of moves made
        this.parent = parent; // Set the parent node
    }

    @Override
    public String toString() {
        // Override to provide a string representation of the node
        return "Node at (" + x + ", " + y + ") with path: " + path;
    }
}
