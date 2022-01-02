package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[] status;

    private final int length;

    private final WeightedQuickUnionUF topBottomSite;

    /**
     * To avoid the backwash, create the uf with only top site to test isFull
     */
    private final WeightedQuickUnionUF topSite;

    /*
    * virtual top site*/
    private final int top;

    /*
     * virtual bottom site*/
    private final int bottom;

    private int numOpen = 0;

    /*
    * helper method to convert the location of grid to an index of 1D array
    * */
    private int xyTo1D(int row, int col) {
        return length * row + col;
    }

    /*
    * helper method to check the index range
    * */
    private void validateIndex(int row, int col) {
        if (row < 0 || row >= length || col < 0 || col >= length) {
            throw new IndexOutOfBoundsException(
                   "the row and column should be between 0 and " + (length - 1)
            );
        }
    }

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must greater than zero");
        }
        length = N;
        status = new boolean[N * N];
        for (int i = 0; i < N * N; i += 1) {
            status[i] = false;
        }
        topSite = new WeightedQuickUnionUF(N * N + 1);
        topBottomSite = new WeightedQuickUnionUF(N * N + 2);
        top = N * N;
        bottom = N * N + 1;
        for (int j = 0; j < N; j++) {
            topBottomSite.union(top, xyTo1D(0, j));
            topBottomSite.union(bottom, xyTo1D(length - 1, j));
            topSite.union(top, xyTo1D(0, j));
        }
    }

    /**
     * validate the adjacent open site and connect
     * @param row
     * @param col
     * @param neighborRow
     * @param neighborCol
     */
    private void unionOpenNeighbor(int row, int col, int neighborRow, int neighborCol) {
        if (neighborRow < 0 || neighborRow >= length || neighborCol < 0 || neighborCol >= length) {
            return;
        }
        int index1D = xyTo1D(row, col);
        int neighbor = xyTo1D(neighborRow, neighborCol);
        if (status[neighbor]) {
            topBottomSite.union(index1D, neighbor);
            topSite.union(index1D, neighbor);
        }
    }

    public void open(int row, int col) {
        validateIndex(row, col);
        int index1D = xyTo1D(row, col);
        if (status[index1D]) {
            return;
        }
        status[index1D] = true;
        numOpen += 1;
        unionOpenNeighbor(row, col, row - 1, col);
        unionOpenNeighbor(row, col, row + 1, col);
        unionOpenNeighbor(row, col, row, col + 1);
        unionOpenNeighbor(row, col, row, col - 1);
    }


    public boolean isFull(int row, int col) {
        validateIndex(row, col);
        int index = xyTo1D(row, col);
        return status[index] && topSite.connected(index, top);
    }

    public boolean isOpen(int row, int col) {
        validateIndex(row, col);
        int index1D = xyTo1D(row, col);
        return status[index1D];
    }

    public boolean percolates() {
        if (length == 1) {
            return isOpen(0, 0);
        }
        return topBottomSite.connected(top, bottom);
    }

    public int numberOfOpenSites() {
        return numOpen;
    }

    public static void main(String[] args) {

    }
}

