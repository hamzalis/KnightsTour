import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public abstract class SearchAlgorithm {
    protected int boardSize; // Size of the chessboard (NxN)
    protected int nodeCount = 0; // Number of nodes created
    protected int[] dx; // Dynamic array for x-coordinate moves
    protected int[] dy; // Dynamic array for y-coordinate moves

    // Different move sets for the knight
    public static final int[][] MOVES_DX = {
            {-2, -1, 1, 2, 2, 1, -1, -2},
            {-2, -1, 2, 1, 2, 1, -1, -2},
            {-2, 1, 2, -1, 2, 1, -2, -1},
            {2, -1, 1, 2, -2, 1, -1, -2},
            {-1, -2, -2, -1, 1, 2, 2, 1}
    };

    public static final int[][] MOVES_DY = {
            {1, 2, 2, 1, -1, -2, -2, -1},
            {1, 2, 1, 2, -1, -2, -2, -1},
            {1, 2, 1, 2, -1, -2, -1, -2},
            {-1, 2, 2, 1, 1, -2, -2, -1},
            {-2, -1, 1, 2, 2, 1, -1, -2}
    };

    // Constructor to initialize the SearchAlgorithm
    public SearchAlgorithm(int boardSize, int moveIndex) {
        this.boardSize = boardSize;
        initializeMoves(moveIndex); // Set the move sequence based on user selection
    }

    // Initialize the move arrays based on the selected index
    private void initializeMoves(int index) {
        this.dx = MOVES_DX[index];
        this.dy = MOVES_DY[index];
    }

    // Check if a move is valid
    protected boolean isValidMove(int x, int y, BitSet board) {
        return x >= 1 && y >= 1 && x <= boardSize && y <= boardSize && !board.get((x - 1) * boardSize + (y - 1));
    }

    // Generate child nodes from the current parent node
    protected List<Node> generateChildren(Node parent, KnightTourSolver.Strategy strategy) {
        List<Node> children = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int newX = parent.x + dx[i];
            int newY = parent.y + dy[i];

            // Add valid moves as new child nodes
            if (isValidMove(newX, newY, parent.board)) {
                children.add(new Node(newX, newY, parent.board, boardSize, parent.pathCost + 1, parent));
                nodeCount++; // Increment the node count when a new node is created
            }
        }

        // Sort children based on the strategy
        switch (strategy) {
            case BFS:
            case DFS:
                // No additional sorting required for BFS and DFS
                break;

            case H1B:
                // Sort by the number of valid moves (ascending)
                children.sort((a, b) -> Integer.compare(countValidMoves(a), countValidMoves(b)));
                break;

            case H2:
                // Sort by valid moves and distance to corners
                children.sort((a, b) -> {
                    int validMovesA = countValidMoves(a);
                    int validMovesB = countValidMoves(b);
                    if (validMovesA != validMovesB) {
                        return Integer.compare(validMovesA, validMovesB);
                    }
                    int distanceA = calculateDistanceToCorner(a.x, a.y);
                    int distanceB = calculateDistanceToCorner(b.x, b.y);
                    return Integer.compare(distanceA, distanceB);
                });
                break;

            default:
                throw new IllegalArgumentException("Unknown strategy: " + strategy);
        }

        return children;
    }

    // Calculate the distance of a position to the closest corner
    private int calculateDistanceToCorner(int x, int y) {
        // Corners: (1,1), (1,boardSize), (boardSize,1), (boardSize,boardSize)
        int[][] corners = {{1, 1}, {1, boardSize}, {boardSize, 1}, {boardSize, boardSize}};
        int minDistance = Integer.MAX_VALUE;

        for (int[] corner : corners) {
            int distance = Math.abs(corner[0] - x) + Math.abs(corner[1] - y);
            minDistance = Math.min(minDistance, distance);
        }

        return minDistance;
    }

    // Count the number of valid moves for a node
    private int countValidMoves(Node node) {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            int newX = node.x + dx[i];
            int newY = node.y + dy[i];
            if (isValidMove(newX, newY, node.board)) {
                count++;
            }
        }
        return count;
    }

    // Abstract method to be implemented for solving the Knight's Tour problem
    public abstract void solve(int startX, int startY);
}
