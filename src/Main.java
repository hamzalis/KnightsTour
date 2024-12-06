public class Main {
    public static void main(String[] args) {
        Board board = new Board(8); // 8x8'lik bir tahta oluştur
        board.visit(0, 0); // Knight'ı (0,0) karesine yerleştir
        System.out.println("Kare (0,0) ziyaret edildi mi? " + board.isVisited(0, 0)); // true
        board.leave(0, 0); // (0,0) karesini boşalt
        System.out.println("Kare (0,0) ziyaret edildi mi? " + board.isVisited(0, 0)); // false
    }
}
