package autocomplete;
import java.util.Arrays;
import java.util.Comparator;
import org.junit.Test;

public class TestBinarySearch {
	
	private static class StrComp implements Comparator<Term>{
		@Override
		public int compare(Term o1, Term o2) {
			// TODO Auto-generated method stub
			return o1.getName().compareTo(o2.getName());
		}
	}
	
	@Test
	public void findFirst() {
		Term a = new Term("avatar", 1);
		Term b = new Term("avatar", 2);
		Term c =  new Term("avataz", 1);
		Term d =  new Term("avataz", 1);
		Term e =  new Term("avataz", 1);
		Term f =  new Term("avatazd", 1);

		Term[] terms = {a, b, c, d, e};
		Comparator<Term> s = new StrComp();
		Arrays.sort(terms);
		int item = BinarySearchDeluxe.firstIndexOf(terms, f, s);
		System.out.println("first: " + item);
	}
	
	@Test
	public void findLast() {
		Term a = new Term("avatar", 1);
		Term b = new Term("avatar", 2);
		Term c =  new Term("avataz", 1);
		Term d =  new Term("avataz", 1);
		Term e =  new Term("avataz", 1);
		Term f =  new Term("avatazd", 1);

		Term[] terms = {a, b, c, d, e, f};
		Comparator<Term> s = new StrComp();
		Arrays.sort(terms);
		int item = BinarySearchDeluxe.lastIndexOf(terms, a, s);
		System.out.println("last: " + item);
	}
}
