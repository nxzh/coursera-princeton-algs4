import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private final int n;
    private int blank;
    private final int[][] tiles;
    private Board twin;

    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException("Tiles is null");
        }
        this.n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    blank = i * n + j;
                }
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(n).append(System.lineSeparator());
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                builder.append(" " + tiles[i][j]);
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int index = i * n + j;
                if (index != blank) {
                    int expectedVal = index + 1;
                    if (tiles[i][j] != expectedVal) {
                        hamming++;
                    }
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int index = i * n + j;
                if (index != blank) {
                    int expectRow = (tiles[i][j] - 1) / n;
                    int expectCol = (tiles[i][j] - 1) % n;
                    manhattan += Math.abs(expectRow - i) + Math.abs(expectCol - j);
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y.getClass() != Board.class) {
            return false;
        }
        Board that = (Board) y;
        if (this.n != that.n) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private int[][] copyTitles(int[][] from) {
        int[][] to = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                to[i][j] = from[i][j];
            }
        }
        return to;
    }

    private void swapTitle(int[][] titles, int i1, int j1, int i2, int j2) {
        int temp = titles[i1][j1];
        titles[i1][j1] = titles[i2][j2];
        titles[i2][j2] = temp;
    }

    private Board neighbor(int i1, int j1, int i2, int j2) {
        int[][] newTitles = copyTitles(this.tiles);
        swapTitle(newTitles, i1, j1, i2, j2);
        return new Board(newTitles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new Queue<>();
        int row = blank / n;
        int col = blank % n;
        if (row != 0) {
            neighbors.enqueue(neighbor(row - 1, col, row, col)); // up
        }
        if (row != n - 1) {
            neighbors.enqueue(neighbor(row, col, row + 1, col)); // down
        }
        if (col != 0) {
            neighbors.enqueue(neighbor(row, col - 1, row, col)); // left
        }
        if (col != n - 1) {
            neighbors.enqueue(neighbor(row, col, row, col + 1)); // right
        }
        return neighbors;
    }

    private boolean isBlank(int i, int j) {
        return i * n + j == blank;
    }
    private Board makeTwin() {
        int i1 = StdRandom.uniform(n);
        int j1 = StdRandom.uniform(n);
        int i2 = StdRandom.uniform(n);
        int j2 = StdRandom.uniform(n);
        if (i1 == i2 && j1 == j2 || isBlank(i1, j1) || isBlank(i2, j2)) {
            return makeTwin();
        }
        int[][] newTitles = copyTitles(this.tiles);
        swapTitle(newTitles, i1, j1, i2, j2);
        return new Board(newTitles);
    }
    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (twin == null) {
            twin = makeTwin();
        }
        return twin;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] titles = new int[][]{
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        };
        Board board = new Board(titles);
        StdOut.println(board);

        StdOut.println(board.dimension());
        StdOut.println(board.manhattan());
        StdOut.println(board.hamming());

        Iterable<Board> neighbors = board.neighbors();
        for (Board b : neighbors) {
            StdOut.println(b);
        }
        StdOut.println(board.twin());
    }
}
