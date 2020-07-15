import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    /**
     * the numbers of rows/columns of the grid
     */
    private final int n;

    /**
     * n*n grid
     */
    private boolean[] grid;

    /**
     * how many items are opened
     */
    private int openNum;

    /**
     * UF now is storing a structure of size (1 + n*n + n*n + 1), which is a virtual top node, following by a n*n grid, following by n*n mirror grip, then a virtual bottom.
     * The following diagram illustrate this structure:
     * <p>
     * ------- v-top --------  <- v-top is a virtual node on the top
     * 1  2  .  .  .  .  .  n
     * 2  .  .  .  .  .  .  n
     * .  .  .  .  .  .  .  n
     * .  .  .  .  .  .  .  n
     * .  .  .  .  .  .  .  n
     * n  .  .  .  .  .  .  n
     * ----------------------  <- the n*n below this line is a mirror of the grid of n*n above
     * n  .  .  .  .  .  .  n
     * .  .  .  .  .  .  .  n
     * .  .  .  .  .  .  .  n
     * .  .  .  .  .  .  .  n
     * 2  .  .  .  .  .  .  n
     * 1  2  .  .  .  .  .  n
     * ------ v-bottom ------  <- v-bottom is a virtual node at the very bottom
     */
    private final WeightedQuickUnionUF wquf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0 || n > Integer.MAX_VALUE - 1) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        wquf = new WeightedQuickUnionUF(1 + n * n + n * n + 1);
        grid = new boolean[1 + n * n + 1];
        initFirstRow();
        initLastRow();
    }

    // connect virtual top to the first row of mirror grid
    private void initFirstRow() {
        for (int i = 1; i <= n; ++i) {
            wquf.union(i, 0);
        }
    }

    // connect virtual bottom to the last row of mirror grid
    private void initLastRow() {
        int mirrorStart = 1 + n * n;
        for (int i = mirrorStart + n * (n - 1); i < mirrorStart + n * n; ++i) {
            wquf.union(i, mirrorStart + n * n);
        }
    }

    private void validate(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) {
            throw new IllegalArgumentException();
        }
    }

    private int elem(int row, int col) {
        return (row - 1) * n + col;
    }

    private int mirrorElem(int row, int col) {
        int mirrorRow = 2 * n - row + 1;
        return (mirrorRow - 1) * n + col;
    }

    public void open(int row, int col) {
        validate(row, col);
        int elem = elem(row, col);
        int mirrorElem = mirrorElem(row, col);
        if (grid[elem]) {
            return;
        }
        grid[elem] = true;
        openNum++;
        if (row != 1 && grid[elem - n]) { // (elem - n) -> up
            wquf.union(elem - n, elem);
            wquf.union(mirrorElem + n, mirrorElem);
        }
        if (row != n) {
            if (grid[row * n + col]) { // (row * n + col) -> down
                wquf.union(row * n + col, elem);
                wquf.union(mirrorElem - n, mirrorElem);
            }
        } else {
            wquf.union(mirrorElem, elem);
        }
        if (col != 1 && grid[elem - 1]) { // (elem - 1) -> left
            wquf.union(elem - 1, elem);
            wquf.union(mirrorElem - 1, mirrorElem);
        }
        if (col != n && grid[elem + 1]) { // (elem + 1) -> right
            wquf.union(elem + 1, elem);
            wquf.union(mirrorElem + 1, mirrorElem);
        }
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
}
