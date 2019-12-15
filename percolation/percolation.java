import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[] rowOrder = { 0, 0, -1, 1 };
    private int[] colOrder = { -1, 1, 0, 0 };

    private int openSites = 0;
    private int cellNum;
    private int source;
    private int sink;
    private WeightedQuickUnionUF uf;
    private boolean[][] grid;

    public Percolation(int n) {
        cellNum = n;
        source = n * n;
        sink = n * n + 1;

        // 2 extra points for source and dest
        uf = new WeightedQuickUnionUF(cellNum * cellNum + 2);
        grid = new boolean[n][n];

        for (int i = 0; i < n; ++i) {
            int point = fromXYTo1D(0, i);
            uf.union(point, source);
        }

        for (int i = 0; i < n; ++i) {
            int point = fromXYTo1D(n - 1, i);
            uf.union(point, sink);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        grid[row][col] = true;
        openSites++;
        int p1 = fromXYTo1D(row, col);
        for (int i = 0; i < rowOrder.length; ++i) {
            int newRow = row + rowOrder[i];
            int newCol = col + colOrder[i];
            if (inGrid(newRow, newCol) && isOpen(newRow, newCol)) {
                int p2 = fromXYTo1D(newRow, newCol);
                uf.union(p1, p2);
            }
        }
    }

    // is the point in Grid?
    public boolean inGrid(int row, int col) {
        return (row >= 0 && col >= 0) &&
                (row < cellNum && col < cellNum);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return this.grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isOpen(row, col)) {
            int point = fromXYTo1D(row, col);
            return uf.connected(point, source) ||
                    uf.connected(point, sink);
        }

        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(source, sink);
    }

    private int fromXYTo1D(int row, int col) {
        return cellNum * row + col;
    }
}

