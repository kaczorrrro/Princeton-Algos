import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    boolean[][] grid;
    int n;
    int openCount = 0;
    WeightedQuickUnionUF unionFind;

    public Percolation(int n) {                // create n-by-n grid, with all sites blocked
        this.n = n;
        grid = new boolean[n][n];
        unionFind = new WeightedQuickUnionUF(n*n+2);   // + 2 for extra  top and bottom node
    }
    public void open(int row, int col) {    // open site (row, col) if it is not open already
        if (!grid[row-1][col-1]) {
            ++openCount;
            updateUnionFind(row, col);
        }
        grid[row-1][col-1] = true;


    }
    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        return grid[row-1][col-1];
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        for (int i=1;i<=n;i++) {
            if (unionFind.connected(toIndex(row, col), toIndex(1, i))) {
                System.out.printf("%d %d is connected to %d %d\n", row, col, 1 ,i);
                return true;
            }
        }
        return false;
    }

    public int numberOfOpenSites()       // number of open sites
    {
        return openCount;
    }

    public boolean percolates()              // does the system percolate?
    {
        for (int i=1;i<=n;i++)
            if (isFull(n,i))
                return true;
        return false;
    }

    private void updateUnionFind(int row, int col){
        int[] is  = {-1,0,0,1};
        int[] js = {0, -1, -1,0};
        for (int i: is){
            for(int j: js){
                int col1 = col+i;
                int row1 = row+j;

                if (row1 < 1 ||  row1 > n || col1 < 1 || col1 > n)
                    continue;

                if (isOpen(row1,col1)){
                    unionFind.union(toIndex(row, col), toIndex(row1, col1));
                    System.out.printf("%d %d unioned to %d %d\n", row, col, 1 ,i);
                }

            }
        }

    }

    private int toIndex(int row, int col){
        return (row-1)*n + col-1;
    }

    private void printState(){
        for (int i=1; i<=n;i++) {
            for (int j = 1; j <= n; j++) {
                if (isOpen(i,j))
                    System.out.print("O");
                else
                    System.out.print("X");
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args)   // test client (optional)
    {
        Percolation percolation = new Percolation(4);
        percolation.printState();

        percolation.open(1,1);
        percolation.open(2,1);
        percolation.open(3,1);
        percolation.open(4,1);

        percolation.printState();
        System.out.println(percolation.percolates());

    }
}