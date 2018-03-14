import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    //fields
    private final int sizeN;
    private int openSites;
    private int [][] n_n_grid;
    private final WeightedQuickUnionUF unionFindArray;
    private final WeightedQuickUnionUF unionFindFull;
    private final int top;
    private final int bottom;

    //default constructor
    public Percolation (int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n has to be an int that is greater than 0 !");
        }
        this.n_n_grid = new int [n][n];
        this.unionFindArray = new WeightedQuickUnionUF(n*n + 2);
        this.unionFindFull = new WeightedQuickUnionUF(n*n+1);
        this.sizeN = n;
        this.openSites = 0;
        this.top = 0;
        this.bottom = n*n + 1;
    }

    public void open (int row, int col) {
        checkError(row, col);
        if (!isOpen(row, col)) {
            //open the site
            this.n_n_grid[row - 1][col - 1] = 1;

            //union adjacent sites if opened
            checkAdjAndUnion(row, col, row - 1, col);
            checkAdjAndUnion(row, col, row + 1, col);
            checkAdjAndUnion(row, col, row, col - 1);
            checkAdjAndUnion(row, col, row, col + 1);

            //connect to virtual sites if opened site is the first/last row
            if(row == 1 && !unionFindArray.connected(ufArrayIndex(row, col), this.top)) {
                unionFindArray.union(ufArrayIndex(row, col), this.top);
                unionFindFull.union(ufArrayIndex(row, col), this.top);
            }
            if(row == this.sizeN && !unionFindArray.connected(ufArrayIndex(row, col), this.bottom)) {
                unionFindArray.union(ufArrayIndex(row, col), this.bottom);
            }

            this.openSites++;
        }
    }

    public boolean isOpen (int row, int col) {
        checkError(row, col);
        if (this.n_n_grid[row - 1][col - 1] == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isFull (int row, int col) {
        //if it is connected to the top
        //unionFindFull don't connect to the bottom
        checkError(row, col);
        return unionFindFull.connected(ufArrayIndex(row, col), this.top);
    }

    public int numberOfOpenSites() {
        return this.openSites;
    }

    public boolean percolates() {
        return unionFindArray.connected(this.top, this.bottom);
    }

    //helper methods for public methods
    private int ufArrayIndex(int row, int col) {
        //convert n by n grid x,y coord. into 1 dimensional index value
        return (row - 1) * this.sizeN + (col - 1) + 1;
    }

    private void checkAdjAndUnion(int oriRow, int oriCol, int row, int col) {
        if(!((row - 1) < 0 || (row - 1 >= this.sizeN) || (col - 1) < 0 || (col - 1) >= this.sizeN)) {
            //check boundary of the site for checkAdjAndUnion func
            if (isOpen(row, col) && !this.unionFindArray.connected(ufArrayIndex(oriRow, oriCol), ufArrayIndex(row, col))) {
                this.unionFindArray.union(ufArrayIndex(oriRow, oriCol), ufArrayIndex(row, col));
            }
            if (isOpen(row, col) && !this.unionFindFull.connected(ufArrayIndex(oriRow, oriCol), ufArrayIndex(row, col))) {
                this.unionFindFull.union(ufArrayIndex(oriRow, oriCol), ufArrayIndex(row, col));
            }
        }
    }

    private void checkError(int row, int col) {
        if(row <= 0 || row > this.sizeN || col <= 0 || col > this.sizeN) {
            throw new IllegalArgumentException("The row and column value should be integer in [1, n]");
        }
    }
    //Methods for testing purpose
    private void print_n_n_grid_val() {
        for(int i = 0; i < this.sizeN; i++) {
            for(int j = 0; j < this.sizeN; j++) {
                System.out.print(this.n_n_grid[i][j] + " ");
            }
            System.out.println();
        }
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
            int[] xy  = n_n_percolation_test.test_xy();
            n_n_percolation_test.open(xy[0], xy[1]);
        }

        return n_n_percolation_test.numberOfOpenSites();
    }

    //Main method
    public static void main (String[] args)
    {
        //create a 6 by 6 grid
        Percolation n_n_percolation_test = new Percolation(1);
        //n_n_percolation_test.print_n_n_grid_xy();
        //n_n_percolation_test.print_n_n_grid_val();

        //System.out.println(n_n_percolation_test.runTillPercolates(n_n_percolation_test));
        //n_n_percolation_test.print_n_n_grid_val();
        //System.out.print(n_n_percolation_test.isFull(15,6));
        n_n_percolation_test.open(1,1);
        n_n_percolation_test.print_n_n_grid_val();
        System.out.println(n_n_percolation_test.percolates());
        n_n_percolation_test.isOpen(2,3);
    }
}
