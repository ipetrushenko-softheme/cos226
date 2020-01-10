
public interface IBoard {               
    // string representation of this board
    public String toString();

    // tile at (row, col) or 0 if blank
    public int tileAt(int row, int col);

    // board size n
    public int size();

    // number of tiles out of place
    public int hamming();

    // sum of Manhattan distances between tiles and goal
    public int manhattan();

    // is this board the goal board?
    public boolean isGoal();

    // does this board equal y?
    public boolean equals(Object y);

    // all neighboring boards
    public Iterable<Board> neighbors();

    // is this board solvable?
    public boolean isSolvable();
}
