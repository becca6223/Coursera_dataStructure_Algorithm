import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

class CoordXY {
    int x;
    int y;

    //default constructor
    public CoordXY (int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Percolation {
    //fields
    int size;
    int openSites;
    CoordXY [][] n_n_grid;
    CoordXY top, bottom;

    //default constructor
    public Percolation (int n) {
        this.n_n_grid = new CoordXY[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                this.n_n_grid[i][j] = new CoordXY(i+1, j+1);
            }
        }
        this.size = n;
        this.openSites = 0;
        this.top = new CoordXY(0, 0);
        this.bottom = new CoordXY(this.size + 1, this.size + 1);
    }

    public void open (int row, int col) {
        int openRow = StdRandom.uniform(this.size);
        int openCol = StdRandom.uniform(this.size);
        int x =  this.n_n_grid[openRow][openCol].x;
        int y =  this.n_n_grid[openRow][openCol].y;

        if (x == openRow - 1 && y == openCol - 1) {
            //open the site


            this.openSites++;
        }
    }

    public boolean isOpen (int row, int col) {
        return true;
    }
    public boolean isFull (int row, int col) {
        return true;
    }
    public int numberOfOpenSites() {
        return this.openSites;
    }
    public boolean percoltaes() {
        return true;
    }

    //helper methods for public methods

    //Methods for testing purpose
    private void print_n_n_grid() {
        for(int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size; j++) {
                System.out.print(this.n_n_grid[i][j].x);
                System.out.print(this.n_n_grid[i][j].y + " ");
            }
            System.out.println();
        }
    }

    //Main method
    public static void main (String[] args)
    {
        Percolation n_n_percolation_test = new Percolation(6);
        n_n_percolation_test.print_n_n_grid();
        WeightedQuickUnionUF test = new WeightedQuickUnionUF(6);
        System.out.print(test);
    }
}
