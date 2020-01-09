import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.NoSuchElementException;

import org.junit.Test;

public class TestDeque {	
	@Test
	public void testEmpty() {
		Deque<Integer> deque = new Deque<Integer>();
		assertEquals(deque.size(), 0);
		assertTrue(deque.isEmpty());
	}
	
	@Test
	public void testSingle() {
		Deque<String> deque = new Deque<String>("test");
		assertEquals(deque.size(), 1);
		assertFalse(deque.isEmpty());
	}
	
	@Test
	public void testAddFirst() {
		Deque<Integer> deque = new Deque<Integer>();
		for(int i = 0; i < 10; ++i) {
			deque.addFirst(i);
			int val = deque.getFirst();
			assertEquals(val, i);
		}
		assertEquals(deque.size(), 10);
	}
	
	@Test
	public void testAddLast() {
		Deque<Integer> deque = new Deque<Integer>();
		deque.addLast(2);
		int val = deque.getLast();
		assertEquals(val, 2);
		
		deque.addFirst(1);
		val = deque.getLast();
		assertEquals(val, 2);
		
		deque.addLast(3);
		val = deque.getLast();
		assertEquals(val, 3);
	}
	
	@Test
	public void testRemoveFirst() {
		Deque<Integer> deque = new Deque<Integer>();
	    assertThrows(NoSuchElementException.class, () -> {
	    		deque.removeFirst();
	    });
		assertTrue(deque.isEmpty());
		
		deque.addFirst(1);
		deque.addLast(2);
		int val = deque.removeFirst();
		assertEquals(val, 1);
		assertEquals(deque.size(), 1);
	}
	
	@Test
	public void testRemoveLast() {
		Deque<Integer> deque = new Deque<Integer>();
		assertThrows(NoSuchElementException.class, () -> {
    			deque.removeLast();
		});		
		
		assertTrue(deque.isEmpty());
		
		deque.addFirst(1);
		deque.addLast(2);
		int val = deque.removeLast();
		assertEquals(val, 2);
		assertEquals(deque.size(), 1);
	}
	
	@Test
	public void testIterator() {
		Deque<Integer> deque = new Deque<Integer>();
		for(int i: deque) { assertTrue(false); }
		
		for(int i = 0; i < 10; ++i) {
			deque.addFirst(i);
		}
		
		int j = 9;
		for(int i: deque) {
			assertEquals(i, j);
			--j;
		}
	}
}
