import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import java.lang.Math;

public class PercolationStats {
    private int n;
    private int trials;
    private double[] trialResults;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.trials = trials;
        this.trialResults = new double[trials];
        this.performTrials();
    }

    private double performTrial() {
        Percolation perc = new Percolation(n);
        int randomRow = 0;
        int randomCol = 0;
        while(!perc.percolates()) {
            randomRow = StdRandom.uniformInt(1, n + 1);
            randomCol = StdRandom.uniformInt(1, n + 1);
            perc.open(randomRow, randomCol);
        }
        return (double) perc.numberOfOpenSites() / (n * n);
    }

    private void performTrials() {
        double result = 0;
        int arrayPostion = 0;
        for (int i = 0; i < trials; i++) {
            result = performTrial();
            trialResults[arrayPostion] = result;
            arrayPostion++;
        }
    }

    public double mean() {
        return StdStats.mean(trialResults);
    }

    public double stddev() {
        return StdStats.stddev(trialResults);
    }

    public double confidenceLo() {
        double stddev = stddev();
        double mean = mean();
        return mean - ((1.96 * stddev)/Math.sqrt(trials));
    }

    public double confidenceHi() {
        double stddev = Math.sqrt(stddev());
        double mean = mean();
        return mean + ((1.96 * stddev)/Math.sqrt(trials));
    }

    public static void main(String[] args) {
        int gridSize = Integer.parseInt(args[0]);
        int numberOfTrials = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(gridSize, numberOfTrials);
        double meanResult = percStats.mean();
        double stddevResult = percStats.stddev();
        double ciLow = percStats.confidenceLo();
        double ciHigh = percStats.confidenceHi();

        StdOut.println("mean    = " + meanResult);
        StdOut.println("stddev  = " + stddevResult);
        StdOut.println("95% confidence interval = [" +ciLow + ", " + ciHigh + "]");

    }
}
