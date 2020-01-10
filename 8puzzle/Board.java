import java.util.Stack;

public class Board {
    private int[][] board;

    public Board(int[][] tiles) {
        board = tiles;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int n = board.length;
        sb.append(n);
        for (int i = 0; i < n; ++i) {
            sb.append(" ");
        }
        sb.append("\n");
        for (int i = 0; i < n; ++i) {
            sb.append(" ");
            for (int j = 0; j < n; ++j) {
                sb.append(board[i][j] + " ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public int tileAt(int row, int col) {
        return board[row][col];
    }

    public int size() {
        return board.length * board[0].length;
    }

    public int hamming() {
        int count = 0;
        int rows = board.length;
        int columns = board[0].length;
        for (int r = 0; r < rows; ++r) {
            for (int c = 0; c < columns; ++c) {
                int val = board[r][c];
                if (val == 0) {
                    continue;
                }
                if (fromRCtoIndex(r, c) != val) {
                    count++;
                }
            }
        }

        return count;
    }

    public int manhattan() {
        int count = 0;
        int rows = board.length;
        int columns = board[0].length;
        for (int r = 0; r < rows; ++r) {
            for (int c = 0; c < columns; ++c) {
                int val = board[r][c];
                if (val == 0) {
                    continue;
                }
                int[] rowCol = indexToRC(val);
                count += (Math.abs(r - rowCol[0]) +
                        Math.abs(c - rowCol[1]));
            }
        }

        return count;
    }

    public boolean isGoal() {
        int rows = board.length;
        int columns = board[0].length;
        int expectedVal = 1;
        for (int r = 0; r < rows; ++r) {
            for (int c = 0; c < columns; ++c) {
                int val = board[r][c];
                if (val == 0 && (expectedVal == rows * columns)) {
                    return true;
                }
                if (val != expectedVal) {
                    return false;
                }
                ++expectedVal;
            }
        }
        return expectedVal == rows * columns;
    }

    public int fromRCtoIndex(int row, int col) {
        return board.length * row + col + 1;
    }

    public int[] indexToRC(int index) {
        int row = 0;
        int col = 0;
        if (index % board.length == 0) {
            row = (index / board.length) - 1;
            col = board.length - 1;
        } else {
            row = index / board.length;
            col = (index % board.length) - 1;
        }
        int[] ans = {row, col};
        return ans;
    }

    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;

        Board that = (Board) other;
        return (this.size() == that.size()) && (this.toString().equals(that.toString()));
    }

    public Iterable<Board> neighbors() {
        int[] rowOrder = {0, 0, -1, 1};
        int[] colOrder = {-1, 1, 0, 0};
        Stack<Board> stack = new Stack<>();
        int[] rc = findEmptySlot();
        int emptyRow = rc[0];
        int emptyCol = rc[1];
        for (int i = 0; i < rowOrder.length; ++i) {
            int newRow = emptyRow + rowOrder[i];
            int newCol = emptyCol + colOrder[i];
            if (inGrid(newRow, newCol)) {
                int val = this.tileAt(newRow, newCol);
                int[][] newBoard = new int[board.length][board.length];
                for (int r = 0; r < board.length; r++) {
                    for (int c = 0; c < board.length; c++) {
                        newBoard[r][c] = board[r][c];
                    }
                }
                newBoard[newRow][newCol] = 0;
                newBoard[emptyRow][emptyCol] = val;
                Board b = new Board(newBoard);
                stack.add(b);
            }
        }

        return stack;
    }


    private boolean inGrid(int row, int col) {
        return (row >= 0 && col >= 0) &&
                (row < board.length && col < board.length);
    }

    private int[] findEmptySlot() {
        int rows = board.length;
        int columns = board[0].length;
        int[] ans = new int[2];
        for (int r = 0; r < rows; ++r) {
            for (int c = 0; c < columns; ++c) {
                int val = board[r][c];
                if (val == 0) {
                    ans[0] = r;
                    ans[1] = c;
                }
            }
        }
        return ans;
    }

    // when n is even (2,4,6), an n-by-n board is solvable if and only if the number of inversions plus the row of the blank square is odd.
    // when n is odd  (1,3,5), an n-by-n board is solvable if and only if its number of inversions is even.
    public boolean isSolvable() {
        int inversions = findNumberOfInversions();
        if (this.size() % 2 == 0) {
            int[] empty = findEmptySlot();
            return (inversions + empty[0]) % 2 != 0;
        }

        return inversions % 2 == 0;
    }

    private int findNumberOfInversions() {
        int[] arr = new int[board.length * board.length - 1];
        int k = 0;
        for (int r = 0; r < board.length; ++r) {
            for (int c = 0; c < board[0].length; ++c) {
                if (board[r][c] != 0) {
                    arr[k] = board[r][c];
                    ++k;
                }
            }
        }

        int count = 0;
        for (int i = 0; i < arr.length - 1; ++i) {
            for (int j = i + 1; j < arr.length; ++j) {
                if (arr[i] > arr[j]) {
                    count++;
                }
            }
        }
        return count;
    }
}
