import java.util.Iterator;
import java.util.Random;

public class RandomizedQueue<Item> implements IRandomizedQueue<Item>, Iterable<Item> {
	private Random rand = new Random();
	private static int RFACTOR = 2;
	private Item[] items;
	private int size;
	
	public RandomizedQueue() {
		items = (Item[]) new Object[10];
		size = 0;
	}
	
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	@Override
	public int size() {
		return size;
	}
	
    private void resize(int capacity) {
    		Item[] temp = (Item[]) new Object[capacity];
    		System.arraycopy(items, 0, temp, 0, size);
    		items = temp;
    }
    
	@Override
    public void enqueue(Item item) {
    		if (size == items.length) {
    			resize(size * RFACTOR);
    		}
    		items[size] = item;
    		size += 1;
    }
    
    private void swap(int a, int b) {
    		Item temp = items[a];
    		items[a] = items[b];
    		items[b] = temp;
    }
    
	@Override
    public Item dequeue() {
    		if (size < items.length / 4) {
    			resize(items.length / 2);
    		}
    		
    		int randIndex = rand.nextInt(size);
    		Item randElem = items[randIndex];
    		swap(randIndex, size-1);
    		items[size-1] = null;
    		size -= 1;
    		return randElem;
    }
	
	@Override
    public Item sample() {
		int randIndex = rand.nextInt(size);
		return items[randIndex];
    }

    @Override
	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}
    
    private class RandomizedQueueIterator implements Iterator<Item> {
    		private Item[] shuffle;
    		private int pos = 0;
    		
    		public RandomizedQueueIterator() {
    			shuffle = (Item[]) new Object[items.length];
    			System.arraycopy(items, 0, shuffle, 0, items.length);
    			
    			Random rnd = new Random();
    		    for (int i = size - 1; i > 0; i--) {
    		    		int index = rnd.nextInt(i + 1);
    		      
    		    		Item tmp = shuffle[index];
    		    		shuffle[index] = shuffle[i];
    		    		shuffle[i] = tmp;
    		    }
    		}
    		
    		@Override
    		public boolean hasNext() {
    			return pos < size;
    		}

    		@Override
    		public Item next() {
    			Item returnItem = shuffle[pos];
    			pos++;
    			return returnItem;
    		}
    }
	
    public Item get(int i) {
		return items[i];
    }
}




