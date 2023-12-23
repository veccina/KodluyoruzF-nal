import java.util.Random;
import java.util.Scanner;

    public class MineSweeper {
        private char[][] board;
        private char[][] solution;
        private int size;
        private int mineCount;

        public MineSweeper(int size, int mineCount) {
            this.size = size;
            this.mineCount = mineCount;
            this.board = new char[size][size];
            this.solution = new char[size][size];
        }

        public void initializeGame() {
            // Oyun tahtalarını başlat
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    board[i][j] = '-';
                    solution[i][j] = '0';
                }
            }

            // Mayınları yerleştir
            placeMines();
        }

        public void placeMines() {
            Random random = new Random();

            for (int i = 0; i < mineCount; i++) {
                int row, col;
                do {
                    row = random.nextInt(size);
                    col = random.nextInt(size);
                } while (solution[row][col] == 'M');

                solution[row][col] = 'M';
            }
        }

        public void displayBoard(char[][] board) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }
        }

        public void playGame() {
            Scanner scanner = new Scanner(System.in);
            boolean gameOver = false;

            while (!gameOver) {
                System.out.println("Oyun Tahtası:");
                displayBoard(board);

                System.out.print("Satır girin: ");
                int row = scanner.nextInt();
                System.out.print("Sütun girin: ");
                int col = scanner.nextInt();

                if (row < 0 || row >= size || col < 0 || col >= size) {
                    System.out.println("Geçersiz hamle. Lütfen tekrar deneyin.");
                    continue;
                }

                if (solution[row][col] == 'M') {
                    System.out.println("Mayına bastınız! Oyun sona erdi.");
                    gameOver = true;
                } else {
                    uncoverCell(row, col);
                    if (isGameWon()) {
                        System.out.println("Tebrikler! Oyunu kazandınız!");
                        gameOver = true;
                    }
                }
            }

            scanner.close();
        }

        public void uncoverCell(int row, int col) {
            if (board[row][col] != '-') return;

            int minesAround = countMinesAround(row, col);
            board[row][col] = (char) (minesAround + '0');

            if (minesAround == 0) {
                int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
                int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

                for (int i = 0; i < 8; i++) {
                    int newRow = row + dr[i];
                    int newCol = col + dc[i];

                    if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
                        uncoverCell(newRow, newCol);
                    }
                }
            }
        }

        public int countMinesAround(int row, int col) {
            int minesAround = 0;
            int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

            for (int i = 0; i < 8; i++) {
                int newRow = row + dr[i];
                int newCol = col + dc[i];

                if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size && solution[newRow][newCol] == 'M') {
                    minesAround++;
                }
            }

            return minesAround;
        }

        public boolean isGameWon() {
            int uncoveredCount = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (board[i][j] != '-') {
                        uncoveredCount++;
                    }
                }
            }
            return uncoveredCount == (size * size - mineCount);
        }
}
