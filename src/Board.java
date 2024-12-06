public class Board {
    private final int size; // Tahta boyutu (NxN)
    private final boolean[][] visited; // Ziyaret edilen kareler

    public Board(int size) {
        this.size = size;
        this.visited = new boolean[size][size];
    }

    // Bir kareye knight'ı yerleştir
    public void visit(int x, int y) {
        if (isInBounds(x, y)) {
            visited[x][y] = true;
        }
    }

    // Bir kareyi boşalt
    public void leave(int x, int y) {
        if (isInBounds(x, y)) {
            visited[x][y] = false;
        }
    }

    // Bir kare ziyaret edildi mi?
    public boolean isVisited(int x, int y) {
        return isInBounds(x, y) && visited[x][y];
    }

    // Koordinat tahtanın sınırları içinde mi?
    private boolean isInBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < size && y < size;
    }

    // Tahta boyutunu döndür
    public int getSize() {
        return size;
    }
}