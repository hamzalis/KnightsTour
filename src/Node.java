import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class Node {
    public int x, y; // Şövalyenin mevcut pozisyonu
    public BitSet board; // Ziyaret edilen kareleri temsil eden tahta
    public int boardSize; // Tahtanın boyutu (NxN)
    public int pathCost; // Yapılan hamle sayısı
    public List<String> path; // Hamle geçmişi
    public Node parent; // Ebeveyn düğüm

    public Node(int x, int y, BitSet board, int boardSize, int pathCost, List<String> path, Node parent) {
        this.x = x;
        this.y = y;
        this.boardSize = boardSize;
        this.board = (BitSet) board.clone(); // Board'u kopyala
        this.board.set((x - 1) * boardSize + (y - 1)); // Şu anki pozisyonu işaretle
        this.pathCost = pathCost;
        this.parent = parent;
        //this.path = new ArrayList<>(path);
        //this.path.add("(" + x + ", " + y + ")");
    }

    @Override
    public String toString() {
        return "Node at (" + x + ", " + y + ") with path: " + path;
    }
}
