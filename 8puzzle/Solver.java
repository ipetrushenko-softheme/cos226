import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Solver {
	private Board board;
	
	private class SearchNode implements Comparable<SearchNode>{
		int moves;
		Board currBoard;
		SearchNode prevBoard;
		
		public SearchNode(int m, Board curr, SearchNode prev) {
			moves = m;
			currBoard = curr;
			prevBoard = prev;
		}

		@Override
		public int compareTo(SearchNode o) {
			int p1 = this.currBoard.manhattan() + this.moves;
			int p2 = o.currBoard.manhattan() + o.moves;
			return Integer.compare(p1 , p2);
		}

	}
	
    public Solver(Board initial) {
    		board = initial;
    }
    
    public Iterable<Board> solve(){
    		MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        Set<Board> set = new HashSet<>();
    		SearchNode source = new SearchNode(0, board, null);
    		pq.insert(source);
    		set.add(board);
    		
    		while(!pq.isEmpty()) {
    			SearchNode currNode = pq.min();
    			Board currBoard = currNode.currBoard;
    			if (currBoard.isGoal()) {
    				break;
    			}
    			pq.delMin();
    			System.out.println("Size set: " + set.size());
    			for(Board b: currBoard.neighbors()) {
    				if (!set.contains(b)) {
    					SearchNode node = new SearchNode(currNode.moves + 1, b, currNode);
        				pq.insert(node);
        				set.add(b);
    				}
    			}
    		}
    		
    		SearchNode goal = pq.delMin();
    		Stack<Board> stack = new Stack<Board>();
    		while(goal.prevBoard != null) {
    			stack.add(goal.currBoard);	
    			goal = goal.prevBoard;
    		}
    		
    		stack.add(goal.currBoard);
    		return stack;
    }
    
    public static void main(String[] args) {
		int[][] b = {{1, 0, 2},
			 	     {7, 5, 4},
			 	     {8, 6, 3}};

		Board board = new Board(b);
		
		Solver s = new Solver(board);
		for (Board curr: s.solve()) {
			System.out.println(curr.toString());
		}
    }

}