package autocomplete;
import java.util.Comparator;

public class Term implements Comparable<Term> {
	private String query;
	private long weight;
	
	public Term(String q, long w) {
		query = q;
		weight = w;
	}
	
	public String getName() {
		return this.query;
	}
	
	public long getWeight() {
		return this.weight;
	}
	
	@Override
	public int compareTo(Term o) {
		return this.query.compareTo(o.query);
	}
	
    public static Comparator<Term> byReverseWeightOrder() {
    		return new ReverseWeighComparator();
    }
    
    public static Comparator<Term> byPrefixOrder(int r) {
    		return new PrefixOrderComparator(r);
    }
	
	private static class ReverseWeighComparator implements Comparator<Term> {
		@Override
		public int compare(Term o1, Term o2) {
			return (int)(o2.weight - o1.weight);
		}	
	}
	
	private static class PrefixOrderComparator implements Comparator<Term> {
		private int prefixLen;
		
		public PrefixOrderComparator(int r) {
			prefixLen = r;
		}
		
		@Override
		public int compare(Term o1, Term o2) {
			String first = o1.query;
			String second = o2.query;
			int minLen = Math.min(first.length(), second.length());
			if (minLen < prefixLen) { return -1; }
			
			for(int i = 0; i < prefixLen; ++i) {
				char a = first.charAt(i);
				char b = second.charAt(i);
				if (a != b) {
					return a - b;
				}
			}
			
			return 0;
		}	
	}
}
