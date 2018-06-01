import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.Math;
import java.lang.Integer;


public class PercolationStats {
    private final double[] thresholds;
    private final int trials;
    private double meanVal;
    private double stddevVal;
    private final int sizeN;
    private static double CONFIDENCE_95 = 1.96;

    public PercolationStats(int n, int trials) {
        if (trials <= 0) {
            throw new IllegalArgumentException("trials should be greater than 0!");
        }
        this.sizeN = n;
        this.trials = trials;
        this.thresholds = new double[trials];
        for(int i = 0; i < trials; i++) {
            Percolation newTest = new Percolation(n);
            this.thresholds[i] = this.runTillPercolates(newTest) / (double) (n*n);
        }
        this.meanVal = StdStats.mean(this.thresholds);
        this.stddevVal = StdStats.stddev(this.thresholds);
    }

    public double mean() {
        return this.meanVal;
    }

    public double stddev() {
        return this.stddevVal;
    }

    public double confidenceLo() {
        return this.meanVal - CONFIDENCE_95 * this.stddevVal / Math.sqrt((double) this.trials);
    }

    public double confidenceHi() {
        return this.meanVal + CONFIDENCE_95 * this.stddevVal / Math.sqrt((double) this.trials);
    }

    private int[] test_xy() {
        int[] coord = new int[2];
        coord[0] = StdRandom.uniform(this.sizeN) + 1;
        coord[1] = StdRandom.uniform(this.sizeN) + 1;
        //System.out.print(coord[0] + " ");
        //System.out.println(coord[1]);
        return coord;
    }

    private int runTillPercolates(Percolation n_n_percolation_test) {
        while(!n_n_percolation_test.percolates()) {
            int[] xy  = this.test_xy();
            n_n_percolation_test.open(xy[0], xy[1]);
        }

        return n_n_percolation_test.numberOfOpenSites();
    }

    public static void main(String[] args) {
        PercolationStats test = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println(test.mean());
        System.out.println(test.stddev());
        System.out.println(test.confidenceLo());
        System.out.println(test.confidenceHi());

    }
}
