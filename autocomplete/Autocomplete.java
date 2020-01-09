package autocomplete;

import java.util.Arrays;
import java.util.Comparator;

public class Autocomplete {
	private Term[] terms;
	
    public Autocomplete(Term[] terms) {
    		Arrays.sort(terms);
    		this.terms = terms;
    }

    // Returns all terms that start with the given prefix, in descending order of weight.
    public Term[] allMatches(String prefix) {
    		Term key = new Term(prefix, 0);
    		Comparator<Term> cmp = Term.byPrefixOrder(prefix.length());
		int firstIndex = BinarySearchDeluxe.firstIndexOf(terms, key, cmp);
		if (firstIndex == -1) {
			return new Term[0];
		}
		int lastIndex = BinarySearchDeluxe.lastIndexOf(terms, key, cmp);
		int n = lastIndex - firstIndex;
		Term[] a = new Term[n+1];
		for(int i = firstIndex, j = 0; i <= lastIndex; ++i, ++j) {
			a[j] = terms[i];
		}
		
		Comparator<Term> weightOrder = Term.byReverseWeightOrder();
		Arrays.sort(a, weightOrder);;
		return a;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
		Term key = new Term(prefix, 0);
		Comparator<Term> cmp = Term.byPrefixOrder(prefix.length());
		int firstIndex = BinarySearchDeluxe.firstIndexOf(terms, key, cmp);
		if (firstIndex == -1) { return 0; }
		int lastIndex = BinarySearchDeluxe.lastIndexOf(terms, key, cmp);
		return lastIndex - firstIndex + 1;
    }

    // unit testing (required)
    public static void main(String[] args) {
    	
    }

}