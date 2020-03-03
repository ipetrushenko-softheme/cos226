/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Partner Name:    Ada Lovelace
 *  Partner NetID:   alovelace
 *  Partner Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;


public class BurrowsWheeler {
    private static class FoundedChar {
        private char ch;
        private boolean is_founded;

        public FoundedChar(char ch) {
            this.ch = ch;
            is_founded = false;
        }

        public boolean isFounded() {
            return is_founded;
        }

        public void markAsFound() {
            is_founded = true;
        }
    }


    public static void transform() {
        String text = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(text);
        int first = findFirst(csa);
        BinaryStdOut.write(first);

        int n = csa.length();
        for (int i = 0; i < n; ++i) {
            char last = getLastChar(csa, text, i);
            BinaryStdOut.write(last);
        }

        BinaryStdOut.flush();
    }

    private static char getLastChar(CircularSuffixArray csa, String s, int i) {
        return s.charAt((csa.index(i) + csa.length() - 1) % csa.length());
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String T = BinaryStdIn.readString();

        char[] arr = T.toCharArray();
        Arrays.sort(arr);
        String sortedT = new String(arr);
        int[] next = buildNext(sortedT, T);

        for (int i = 0; i < T.length(); ++i) {
            BinaryStdOut.write(sortedT.charAt(first));
            first = next[first];
        }

        BinaryStdOut.flush();
        BinaryStdIn.close();
    }

    private static int findFirst(CircularSuffixArray csa) {
        int n = csa.length();
        for (int i = 0; i < n; ++i) {
            if (csa.index(i) == 0) {
                return i;
            }
        }

        String msg = "Suffix array was constructed incorrectly";
        throw new IllegalArgumentException(msg);
    }

    private static int[] buildNext(String sortedT, String T) {
        int n = T.length();
        FoundedChar[] tarr = new FoundedChar[n];
        for (int i = 0; i < n; ++i) {
            tarr[i] = new FoundedChar(T.charAt(i));
        }

        int[] next = new int[n];
        for (int i = 0; i < n; ++i) {
            char curr_sorted = sortedT.charAt(i);
            int idx = findPosition(curr_sorted, tarr);
            next[i] = idx;
        }

        return next;
    }

    private static int[] buildNextLinear(String t) {
        int n = t.length();
        int[] count = new int[256 + 1];
        for (int i = 0; i < n; i++) {
            count[t.charAt(i) + 1]++;
        }
        for (int r = 0; r < 256; r++) {
            count[r + 1] += count[r];
        }

        int[] next = new int[n];
        for (int i = 0; i < n; i++) {
            next[count[t.charAt(i)]++] = i;
        }

        return next;
    }

    private static int findPosition(char key, FoundedChar[] tarr) {
        for (int i = 0; i < tarr.length; ++i) {
            if (tarr[i].ch == key && !tarr[i].is_founded) {
                tarr[i].markAsFound();
                return i;
            }
        }

        throw new IllegalArgumentException();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
        else throw new IllegalArgumentException("Expected either '-' or '+'");
    }

}
