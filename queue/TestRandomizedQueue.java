import static org.junit.Assert.*;
import org.junit.Test;

public class TestRandomizedQueue {
	@Test
	public void testEmpty() {
		RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
		assertEquals(queue.size(), 0);
		assertTrue(queue.isEmpty());
	}
	
	@Test
	public void testEnqueue() {
		RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
		for(int i = 0; i < 20; ++i) {
			queue.enqueue(i);
			int val = queue.get(i);
			assertEquals(val, i);
		}
		assertEquals(queue.size(), 20);
	}
	
	@Test
	public void testDequeue() {
		RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
		for(int i = 0; i < 20; ++i) {
			queue.enqueue(i);
		}
		
		for(int i = 0; i < 20; ++i) {
			queue.dequeue();
		}
		
		assertTrue(queue.isEmpty());
	}
	
	@Test
	public void testIterator() {
		RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
		for(int i: queue) {
			assertTrue(false);
		}
		
		int n = 5;
		for (int i = 0; i < n; i++) {
		    queue.enqueue(i);
		}
		
		for (int a : queue) {
		    for (int b : queue) {
		        System.out.print(a + "-" + b + " ");
		    }
		    System.out.println();
		}
	}
}
