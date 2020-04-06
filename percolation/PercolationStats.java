import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    private double mean;
    private double standardDeviation;
    private double confLow;
    private double confHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        double[] thresholds = new double[trials];
        for (int i = 0; i < thresholds.length; i++) {
            thresholds[i] = computeThreshold(n);
        }

        mean = StdStats.mean(thresholds);
        standardDeviation = StdStats.stddev(thresholds);

        confLow = mean - confidenceHelper(trials, standardDeviation);
        confHi = mean + confidenceHelper(trials, standardDeviation);
    }

    private double computeThreshold(int gridSize) {
        Percolation p = new Percolation(gridSize);
        while (!p.percolates()) {
            int row = StdRandom.uniform(1, gridSize + 1);
            int col = StdRandom.uniform(1, gridSize + 1);
            p.open(row, col);
        }
        return p.numberOfOpenSites() / (double) (gridSize * gridSize);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return standardDeviation;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confLow;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confHi;
    }

    private double confidenceHelper(int trials, double stdev) {
        return ((1.96 * stdev) / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        int gridSize = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(gridSize, trials);
        StdOut.printf("mean                    = %17.15f\n", percStats.mean());
        StdOut.printf("stddev                  = %17.15f\n", percStats.stddev());
        StdOut.printf("95%% confidence interval = [ %17.15f,%17.15f ]\n", percStats.confidenceLo(),
                      percStats.confidenceHi());
    }

}
