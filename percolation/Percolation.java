/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int topRoot;
    private int bottomRoot;
    private int size;
    private WeightedQuickUnionUF ufArray;
    private boolean[] grid;
    private int openCount = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        size = n;
        int gridSize = (size * size);
        // Additional 2 is to accomodate top and bottom roots
        int arraySize = gridSize + 2;
        topRoot = gridSize;
        bottomRoot = gridSize + 1;

        ufArray = new WeightedQuickUnionUF(arraySize);
        for (int i = 0; i < n; i++) {
            ufArray.union(topRoot, i);
            ufArray.union(bottomRoot, (gridSize - 1) - i);
        }

        grid = new boolean[gridSize];
        for (int i = 0; i < gridSize; i++) {
            grid[i] = false;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        int selfIndex = gridToIndex(row, col);
        grid[selfIndex] = true;
        openCount++;

        int leftIndex = left(row, col);
        if (leftIndex != -1) {
            checkAndConnect(selfIndex, leftIndex);
        }

        int rightIndex = right(row, col);
        if (rightIndex != -1) {
            checkAndConnect(selfIndex, rightIndex);
        }

        int topIndex = top(row, col);
        if (topIndex != -1) {
            checkAndConnect(selfIndex, topIndex);
        }

        int bottomIndex = bottom(row, col);
        if (bottomIndex != -1) {
            checkAndConnect(selfIndex, bottomIndex);
        }

    }

    private void checkAndConnect(int src, int target) {
        if (grid[target]) {
            ufArray.union(src, target);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (isBadIndex(row, col)) {
            throw new IllegalArgumentException();
        }
        return grid[gridToIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isBadIndex(row, col)) {
            throw new IllegalArgumentException();
        }

        return isOpen(row, col) && ufArray
                .connected(gridToIndex(row, col), topRoot);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return ufArray.connected(bottomRoot, topRoot);
    }

    private int gridToIndex(int row, int col) {
        if (isBadIndex(row, col)) {
            throw new IllegalArgumentException();
        }
        row = row - 1;
        col = col - 1;
        return (size * row) + col;
    }

    private int left(int row, int col) {
        if (col == 1) {
            return -1;
        }
        int leftCol = col - 1;

        return gridToIndex(row, leftCol);
    }

    private int right(int row, int col) {
        if (col == size) {
            return -1;
        }
        int rightCol = col + 1;

        return gridToIndex(row, rightCol);
    }

    private int top(int row, int col) {
        if (row == 1) {
            return -1;
        }
        int topRow = row - 1;

        return gridToIndex(topRow, col);
    }

    private int bottom(int row, int col) {
        if (row == size) {
            return -1;
        }
        int topRow = row + 1;

        return gridToIndex(topRow, col);
    }

    private boolean isBadIndex(int row, int col) {
        if (row <= 0 || col <= 0 || row > size || col > size) {
            return true;
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
