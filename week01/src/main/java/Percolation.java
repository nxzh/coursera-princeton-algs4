import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private boolean[] grid;
    private int openNum;
    private final WeightedQuickUnionUF wquf;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0 || n > Integer.MAX_VALUE - 1) {
            throw new IllegalArgumentException();
        }
        openNum = 0;
        this.n = n;

        wquf = new WeightedQuickUnionUF(2 * this.n * this.n + 2);
        grid = new boolean[this.n * this.n + 2];
        for (int i = 0; i < grid.length; ++i) {
            grid[i] = false;
        }
        initFirstRow();
        initLastRow();
    }

    private void initFirstRow() {
        for (int i = 1; i <= n; ++i) {
            wquf.union(i, 0);
        }
    }

    private void initLastRow() {
        for (int i = n * (2 * n - 1) + 1; i <= n * 2 * n; ++i) {
            wquf.union(i, 2 * n * n + 1);
        }
    }

    private void validate(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) {
            throw new IllegalArgumentException();
        }
    }

    public void open(int row, int col) {
        validate(row, col);
        int elem = (row - 1) * n + col;
        int mirror = (mirrowRow(row) - 1) * n + col;
        if (grid[elem]) {
            return;
        }
        grid[elem] = true;
        openNum++;
        if (row != 1) {
            int up = elem - n;
            if (grid[up]) {
                wquf.union(up, elem);
                wquf.union(mirror + n, mirror);
            }
        }
        if (row != n) {
            int down = (row) * n + col;
            if (grid[down]) {
                wquf.union(down, elem);
                wquf.union(mirror - n, mirror);
            }
        } else {
            wquf.union(mirror, elem);
        }

        if (col != 1) {
            int left = elem - 1;
            if (grid[left]) {
                wquf.union(left, elem);
                wquf.union(mirror - 1, mirror);
            }
        }

        if (col != n) {
            int right = elem + 1;
            if (grid[right]) {
                wquf.union(right, elem);
                wquf.union(mirror + 1, mirror);
            }
        }

    }

    private int mirrowRow(int row) {
        return 2 * n - row + 1;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[(row - 1) * n + col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int elem = (row - 1) * n + col;
        return grid[elem] && wquf.find(elem) == wquf.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openNum;
    }

    // does the system percolate?
    public boolean percolates() {
        return wquf.find(0) == wquf.find(2 * n * n + 1);
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(2);
        percolation.open(1, 1);
        if (percolation.isFull(1, 1)) {
            StdOut.println("Opened");
        }
        if (percolation.percolates()) {
            StdOut.println("percolates.");
        }
        percolation.open(2, 1);
        if (percolation.isFull(2, 1)) {
            StdOut.println("Opened");
        }
        if (percolation.percolates()) {
            StdOut.println("percolates.");
        }
    }
}
