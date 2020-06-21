import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double FACTOR = 1.96;
    // perform independent trials on an n-by-n grid
    private final int sz;
    private double[] trials;

    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;


    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials < 1) {
            throw new IllegalArgumentException();
        }
        this.sz = n;
        this.trials = new double[trials];
        test();
    }


    private void test() {
        for (int i = 0; i < trials.length; ++i) {
            Percolation t = new Percolation(sz);
            while (!t.percolates()) {
                int row = StdRandom.uniform(1, sz + 1);
                int col = StdRandom.uniform(1, sz + 1);
                t.open(row, col);
            }
            trials[i] = (t.numberOfOpenSites() * 1.0) / (sz * sz);
        }
        mean = StdStats.mean(trials);
        stddev = StdStats.stddev(trials);;
        double factor = FACTOR * Math.sqrt(stddev()) / Math.sqrt(trials.length);
        confidenceLo = mean - factor;
        confidenceHi = mean + factor;
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, t);
        StdOut.println(stats.mean());
        StdOut.println(stats.stddev());
        StdOut.println(stats.confidenceLo());
        StdOut.println(stats.confidenceHi());
    }
}
