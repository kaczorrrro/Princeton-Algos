import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by sebas on 01.03.2017.
 */
public class PercolationStats {
    private double [] results;
    private int t;


    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        if (trials <= 0 )
            throw new IllegalArgumentException();

        this.t = trials;
        results = new double[trials];

        for (int i=0;i<trials;i++){
            Percolation p = new Percolation(n);
            while (!p.percolates()){
                int row = StdRandom.uniform(n)+1; // can this return 0 - lol, yes
                int col = StdRandom.uniform(n)+1;
                p.open(row, col);
            }

            results[i] = p.numberOfOpenSites()/(double)(n*n);
        }
    }
    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(results);
    }

    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(results);
    }
    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return mean()-(1.96*stddev()/Math.sqrt(t));
    }
    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return mean()+(1.96*stddev()/Math.sqrt(t));
    }


    public static void main(String[] args)        // test client (described below)
    {
        PercolationStats ps = new PercolationStats(100,30);
        System.out.println(ps.confidenceLo() + " - " + ps.confidenceHi());
    }
}
