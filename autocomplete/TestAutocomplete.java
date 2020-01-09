package autocomplete;
import static org.junit.Assert.*;
import org.junit.Test;

public class TestAutocomplete {
	
	@Test
	public void testAllMatches() {
		Term a = new Term("avatar", 1);
		Term b = new Term("avatar", 2);
		Term c =  new Term("avataz", 1);
		Term d =  new Term("avataz", 1);
		Term e =  new Term("avataz", 1);
		Term f =  new Term("avatazd", 1);
		
		Term[] terms = {a, b, c, d, e, f};
		Autocomplete autocomplete = new Autocomplete(terms);
		Term[] res = autocomplete.allMatches("avataz");
		for(int i = 0; i < res.length; ++i) {
			System.out.println(res[i].getName());
		}
	}
	
	@Test
	public void testNumberOfMatches() {
		Term a = new Term("avatar", 1);
		Term b = new Term("avatar", 2);
		Term c =  new Term("avataz", 1);
		Term d =  new Term("avataz", 1);
		Term e =  new Term("avataz", 1);
		Term f =  new Term("avatazd", 1);
		
		Term[] terms = {a, b, c, d, e, f};
		Autocomplete autocomplete = new Autocomplete(terms);
		int n = autocomplete.numberOfMatches("avataz");
		assertEquals(n, 4);
	}
}
