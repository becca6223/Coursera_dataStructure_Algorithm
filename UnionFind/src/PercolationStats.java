import edu.princeton.cs.algs4.StdStats;
import java.lang.Math;

public class PercolationStats {
    double[] thresholds;
    int trials;
    int gridSize;
    double meanVal;
    double stddevVal;

    public PercolationStats(int n, int trials) {
        this.trials = trials;
        this.gridSize = n*n;
        this.thresholds = new double[trials];
        this.meanVal = 0;
        this.stddevVal = 0;
        for(int i = 0; i < trials; i++) {
            Percolation newTest = new Percolation(n);
            this.thresholds[i] = newTest.runTillPercolates(newTest) / (double) this.gridSize;
        }
    }

    public double mean() {
        this.meanVal = StdStats.mean(this.thresholds);
        return this.meanVal;
    }

    public double stddev() {
        this.stddevVal = StdStats.stddev(this.thresholds);
        return this.stddevVal;
    }

    public double confidenceLo() {
        return this.meanVal - 1.96 * this.stddevVal / Math.sqrt((double) this.trials);
    }

    public double confidenceHi() {
        return this.meanVal + 1.96 * this.stddevVal / Math.sqrt((double) this.trials);
    }

    public static void main(String[] args) {
        PercolationStats test = new PercolationStats(200, 100);
        System.out.println(test.mean());
        System.out.println(test.stddev());
        System.out.println(test.confidenceLo());
        System.out.println(test.confidenceHi());
    }
}
