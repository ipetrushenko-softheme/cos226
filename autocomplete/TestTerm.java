package autocomplete;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Comparator;
import org.junit.Test;

public class TestTerm {
	
	@Test
	public void testLexicographicComparator() {
		Term first = new Term("avatar", 1);
		Term second = new Term("avatge", 2);
		Term third =  new Term("avataz", 1);
		
		assertTrue(first.compareTo(second) < 0);
		assertTrue(first.compareTo(third) < 0);
		
		Term[] terms = {first, second, third};
		Arrays.sort(terms);
		for(int i = 0; i < terms.length - 1; ++i) {
			String curr = terms[i].getName();
			String next = terms[i+1].getName();
			int cmp = curr.compareTo(next);
			assertTrue(cmp < 0);
		}
	}
	
	@Test
	public void testWeightComparator() {
		Term first = new Term("avatar", 1111);
		Term second = new Term("avengers", 2);
		Term third = new Term("avengers", 12);

		Comparator<Term> weightOrder = Term.byReverseWeightOrder();
		assertTrue(weightOrder.compare(first, second) < 0);
		
		Term[] terms = {first, second, third};
		Arrays.sort(terms, weightOrder);;
		for(int i = 0; i < terms.length - 1; ++i) {
			long curr = terms[i].getWeight();
			long next = terms[i+1].getWeight();
			assertTrue(curr > next);
		}
	}
	
	@Test
	public void testPrefixOrderComparator() {
		Term first = new Term("avatar", 1);
		Term second = new Term("avengers", 2);
		
		Comparator<Term> cmp = Term.byPrefixOrder(1);
		assertTrue(cmp.compare(first, second) == 0);
	}
}
