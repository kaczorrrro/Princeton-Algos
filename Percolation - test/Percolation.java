import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int n;
    private int openCount = 0;
    private WeightedQuickUnionUF unionFind;
    private final int topGroup;

    public Percolation(int n) {                // create n-by-n grid, with all sites blocked
        this.n = n;
        grid = new boolean[n][n];
        unionFind = new WeightedQuickUnionUF(n*n+1);   // + 1 for extra  top
        topGroup = n*n;
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
        if (!isOpen(row, col))
            return false;

        return unionFind.connected(toIndex(row, col), topGroup);
//        for (int i=1;i<=n;i++) {
//            if (, toIndex(1, i))) {
//                //System.out.printf("%d %d is connected to %d %d\n", row, col, 1 ,i);
//                return true;
//            }
//        }
//        return false;
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
        class Point{
            public int x,y;
            Point(int x, int y){this.x = x; this.y =y;}
        }
        Point[] points = {new Point(-1,0), new Point(1, 0), new Point(0,1), new Point(0,-1)};

        for (Point p:points) {
            int col1 = col + p.x;
            int row1 = row + p.y;

            if(row1 < 1) {
                unionFind.union(toIndex(row, col), topGroup);
                continue;
            }

            if (col1 < 1 || row1 > n || col1 > n)
                continue;

            if (isOpen(row1, col1)) {
                unionFind.union(toIndex(row, col), toIndex(row1, col1));
                //System.out.printf("%d %d unioned to %d %d\n", row, col, 1 ,i);
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
        percolation.open(2,2);
        percolation.open(3,1);
        percolation.open(4,1);

        System.out.println();
        percolation.printState();
        System.out.println(percolation.percolates());

    }
}