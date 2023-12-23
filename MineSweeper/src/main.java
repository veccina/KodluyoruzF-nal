import java.util.Scanner;
public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Oyun tahtası boyutunu girin (örnek: 5): ");
        int size = scanner.nextInt();
        System.out.print("Mayın sayısını girin (örnek: 10): ");
        int mineCount = scanner.nextInt();

        MineSweeper game = new MineSweeper(size, mineCount);
        game.initializeGame();
        game.playGame();

        scanner.close();
    }
}

