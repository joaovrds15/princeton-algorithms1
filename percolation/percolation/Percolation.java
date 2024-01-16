import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int TOP = 0;
    private final boolean[][] opened;
    private final int size;
    private final int bottom;
    private int openSites;
    private final WeightedQuickUnionUF qf;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.size = n;
        this.bottom = size * size + 1;
        this.qf = new WeightedQuickUnionUF(n * n + 2);
        this.opened = new boolean[n][n];
        this.openSites = 0;
    }

    public void open(int row, int col) {
        checkParametersCondition(row, col);
        if (! this.opened[row - 1][col - 1]) {
            this.opened[row - 1][col - 1] = true;
            this.openSites++;

            if (row == 1) {
                qf.union(getUnionFindIndex(row, col), TOP);
            }

            if (row == size) {
                qf.union(getUnionFindIndex(row, col), bottom);
            }

            if (row > 1 && isOpen(row - 1, col)) {
                qf.union(getUnionFindIndex(row, col), getUnionFindIndex(row - 1, col));
            }

            if (row < size && isOpen(row + 1, col)) {
                qf.union(getUnionFindIndex(row, col), getUnionFindIndex(row + 1, col));
            }

            if (col > 1 && isOpen(row, col - 1)) {
                qf.union(getUnionFindIndex(row, col), getUnionFindIndex(row, col - 1));
            }

            if (col < size && isOpen(row, col + 1)) {
                qf.union(getUnionFindIndex(row, col), getUnionFindIndex(row, col + 1));
            }
        }
    }

    private void checkParametersCondition(int p, int q) {
        if (p <= 0 || q <= 0 || q > size || p > size) {
            System.out.println(size);
            System.out.println(p);
            System.out.println(q);
            throw new IllegalArgumentException();
        }
    }

    public boolean isOpen(int row, int col) {
        checkParametersCondition(row, col);
        return opened[row - 1][col - 1];
    }

    private int getUnionFindIndex(int row, int col) {
        return size * (row - 1) + col;
    }

    public int numberOfOpenSites() {
        return this.openSites;
    }

    public boolean isFull(int row, int col) {
        if ((row > 0 && row <= size) && (col > 0 && col <= size)){
            return qf.find(TOP) == qf.find(getUnionFindIndex(row, col));
        } else {
            throw new IllegalArgumentException();
        }
    }

    public boolean percolates() {
        return qf.find(TOP) == qf.find(bottom);
    }

    public static void main(String[] args) {

    }
}
