import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements IDeque<Item>, Iterable<Item> {
	private Node front;
	private Node tail;
	private int size;
	
	private class Node {
		Item value;
		Node next;
		Node prev;
		
		public Node(Item v, Node n, Node p) {
			value = v;
			next = n;
			prev = p;
		}
	}
	
	public Deque() {
		front = new Node(null, null, null);
		tail = new Node(null, null, null);
		
		front.next = tail;
		tail.prev = front;
		
		size = 0;
	}
	
	public Deque(Item v) {
		front = new Node(null, null, null);
		tail = new Node(null, null, null);		
		Node node = new Node(v, tail, front);

		front.next = node;
		tail.prev = node;
		size = 1;
	}
	
	@Override
	public void addFirst(Item v) {		
		Node node = new Node(v, front.next, front);
		
		Node last = front.next;
		last.prev = node;
		front.next = node;
		size += 1;
	}
	
	@Override
	public void addLast(Item v) {
		Node node = new Node(v, tail, tail.prev);
		
		tail.prev.next = node;
		tail.prev = node;
		size += 1;
	}
	
	@Override
	public Item removeFirst() {
		if (this.isEmpty()) { 
			throw new NoSuchElementException(); 
		}
		
		Node first = front.next;
		Node afterFirst = first.next;
		
		front.next = afterFirst;
		afterFirst.prev = front;
		
		first.next = null;
		first.prev = null;
		
		size -= 1;
		
		return first.value;
	}
	
	@Override
	public Item removeLast() {
		if (this.isEmpty()) { 
			throw new NoSuchElementException(); 
		}
		
		Node last = tail.prev;
		Node beforeLast = last.prev;
		beforeLast.next = tail;
		tail.prev = beforeLast;
		
		last.next = null;
		last.prev = null;
		
		size -= 1;
		
		return last.value;
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}
	
	private class DequeIterator implements Iterator<Item>{
		private Node curr = front.next; 
		
		@Override
		public boolean hasNext() {
			return curr != tail;
		}

		@Override
		public Item next() {
			Item returnItem = curr.value;
			curr = curr.next;
			return returnItem;
		}
	}
	
	public Item getFirst() {
		return front.next.value;
	}
	
	public Item getLast() {
		return tail.prev.value;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return this.size() == 0;
	}
}

