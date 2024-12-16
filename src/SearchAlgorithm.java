import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public abstract class SearchAlgorithm {
    protected int boardSize;
    protected int nodeCount = 0; // Oluşturulan düğüm sayısı
    protected int[] dx; // Dinamik hareket dizisi
    protected int[] dy;

    // Farklı hareket dizileri
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

    public SearchAlgorithm(int boardSize, int moveIndex) {
        this.boardSize = boardSize;
        initializeMoves(moveIndex); // Kullanıcı tarafından seçilen hareket dizisini ayarla
    }

    private void initializeMoves(int index) {
        this.dx = MOVES_DX[index];
        this.dy = MOVES_DY[index];
    }

    protected boolean isValidMove(int x, int y, BitSet board) {
        return x >= 1 && y >= 1 && x <= boardSize && y <= boardSize && !board.get((x - 1) * boardSize + (y - 1));
    }

    protected List<Node> generateChildren(Node parent, KnightTourSolver.Strategy strategy) {
        List<Node> children = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int newX = parent.x + dx[i];
            int newY = parent.y + dy[i];

            if (isValidMove(newX, newY, parent.board)) {
                children.add(new Node(newX, newY, parent.board, boardSize, parent.pathCost + 1, parent.path, parent));
                nodeCount++; // Yeni bir düğüm oluşturulduğunda artır
            }
        }

        // Stratejiye göre sıralama işlemleri
        switch (strategy) {
            case BFS:
            case DFS:
                // BFS ve DFS için ek bir sıralama gerekmiyor
                break;

            case H1B:
                // Geçerli hamle sayısına göre sıralama (küçükten büyüğe)
                children.sort((a, b) -> Integer.compare(countValidMoves(a), countValidMoves(b)));
                break;

            case H2:
                // Geçerli hamle sayısına ve köşelere olan mesafeye göre sıralama
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

    private int calculateDistanceToCorner(int x, int y) {
        // Köşeler: (1,1), (1,boardSize), (boardSize,1), (boardSize,boardSize)
        int[][] corners = {{1, 1}, {1, boardSize}, {boardSize, 1}, {boardSize, boardSize}};
        int minDistance = Integer.MAX_VALUE;

        for (int[] corner : corners) {
            int distance = Math.abs(corner[0] - x) + Math.abs(corner[1] - y);
            minDistance = Math.min(minDistance, distance);
        }

        return minDistance;
    }

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

    public abstract void solve(int startX, int startY);
}
