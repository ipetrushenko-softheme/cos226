import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.NoSuchElementException;

import org.junit.Test;

public class BoardTest {
	
	@Test
	public void testToString() {
		int[][] b = {{0, 1, 2},
					 {4, 6, 3},
				     {5, 7, 8}};
		Board board = new Board(b);
		System.out.print(board.toString());
		assertEquals("3   \n 0 1 2 \n 4 6 3 \n 5 7 8 \n", board.toString());
	}
	
	@Test
	public void testTileAt() {
		int[][] b = {{0, 1, 2},
					 {4, 6, 3},
				     {5, 7, 8}};
		Board board = new Board(b);
		assertEquals(8, board.tileAt(2, 2));
	}
	
	@Test
	public void testSize() {
		int[][] b = {{0, 1, 2},
					 {4, 6, 3},
				     {5, 7, 8}};
		Board board = new Board(b);
		assertEquals(9, board.size());
		
		int[][] emptyValues = {{}};
		Board empty = new Board(emptyValues);
		assertEquals(0, empty.size());
	}
	
	@Test
	public void testHamming() {
		int[][] b = {{8, 1, 3},
					 {4, 0, 2},
				     {7, 6, 5}};
		Board board = new Board(b);
		assertEquals(5, board.hamming());
	}
	
	@Test
	public void testIndexToRX() {
		int[][] odd = {{8, 1, 3},
					   {4, 0, 2},
				       {7, 6, 5}};
		Board oddBoard = new Board(odd);
		for(int i = 1; i < 9; ++i) {
			int[] ans = oddBoard.indexToRC(i);
			assertEquals(i, oddBoard.fromRCtoIndex(ans[0], ans[1]));
		}
		
		int[][] even = {{1, 2, 3, 4},
				{5, 6, 0, 8},
				{9, 10, 7, 11},
				{13,14,15, 12}};
		Board evenBoard = new Board(even);
		for(int i = 1; i < 9; ++i) {
			int[] ans = evenBoard.indexToRC(i);
			assertEquals(i, evenBoard.fromRCtoIndex(ans[0], ans[1]));
		}
	}
	
	@Test
	public void testManhattanDistance() {
		int[][] odd = {{8, 1, 3},
					   {4, 0, 2},
				       {7, 6, 5}};
		Board board = new Board(odd);
		assertEquals(10, board.manhattan());
	}
	
	@Test
	public void testIsGoal() {
		
		int[][] odd = {{8, 1, 3},
					   {4, 0, 2},
				       {7, 6, 5}};
		Board board = new Board(odd);
		assertFalse(board.isGoal());
		
		int[][] even = {{1, 2, 3},
				   		{4, 5, 6},
				   		{7, 8, 0}};
	    board = new Board(even);
		assertTrue(board.isGoal());
		
		int[][] failedTest = {{1, 2, 3},
				   			  {4, 5, 6},
				   			  {7, 0, 8}};
		board = new Board(failedTest);
		assertFalse(board.isGoal());
	}

	@Test
	public void testEquals() {
		int[][] first = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
		int[][] second = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
		
		Board a = new Board(first);
		Board b = new Board(second);

		assertTrue(a.equals(b));
	}

	@Test
	public void isSolvable() {
		
		int[][] first = {{1, 2, 3}, 
						{4, 5, 6}, 
						{8, 7, 0}};
		Board a = new Board(first);
		assertFalse(a.isSolvable());
		
		int[][] second = {{1, 2, 3, 4}, 
						  {5, 6, 0, 8}, 
						  {9, 10, 7, 11},
						 {13, 14, 15, 12}};
		Board b = new Board(second);
		assertTrue(b.isSolvable());
		
		int[][] third = {{1,  2,   3,   4}, 
				 		 {5,  6,   7,   8}, 
				 		 {9,  10, 11,  12},
				 		 {13, 15, 14,  0}};
		Board c = new Board(third);
		assertFalse(c.isSolvable());
		
	}
}
