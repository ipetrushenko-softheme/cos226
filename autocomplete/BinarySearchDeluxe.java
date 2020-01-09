package autocomplete;
import java.util.Comparator;

public class BinarySearchDeluxe {

    // Returns the index of the first key in a[] that equals the search key, 
	// or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
    		int bad = -1;
    		int good = a.length;
    		
    		while(good - bad > 1) {
    			int m = (good + bad) / 2;
    			if(comparator.compare(a[m], key) >= 0) {
    				good = m;
    			}
    			else {
    				bad = m;
    			}
    		}
    		
    		if (good < a.length && comparator.compare(a[good], key) == 0) {
    			return good;
    		}
    		
    		return -1;
    }
    
    // Returns the index of the last key in a[] that equals the search key,
    // or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
		int bad = -1;
		int good = a.length;
		
		while(good - bad > 1) {
			int m = (good + bad) / 2;
			if(comparator.compare(a[m], key) > 0) {
				good = m;
			}
			else {
				bad = m;
			}
		}
		
		if (bad > -1 && comparator.compare(a[bad], key) == 0) {
			return bad;
		}
		
		return -1;
    }
}
