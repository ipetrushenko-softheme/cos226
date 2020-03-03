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

public class MoveToFront {
    private static final int R = 256;

    public static void encode() {
        char[] chars = new char[R];
        for (int i = 0; i < R; i++) {
            chars[i] = (char) i;
        }

        while (!BinaryStdIn.isEmpty()) {
            char curr_ch = BinaryStdIn.readChar();
            int pos = (int) curr_ch;
            while (chars[pos] != curr_ch) {
                pos = (int) chars[pos];
            }
            BinaryStdOut.write(pos, 8);

            while (pos > 0) {
                swap(chars, pos - 1, pos);
                pos--;
            }
        }
        BinaryStdOut.close();
    }


    // apply move-to-front decoding, reading from stdin and writing to stdout
    public static void decode() {
        char[] chars = new char[R];
        for (int i = 0; i < R; i++) {
            chars[i] = (char) i;
        }
        
        while (!BinaryStdIn.isEmpty()) {
            int pos = BinaryStdIn.readChar();
            BinaryStdOut.write(chars[pos], 8);
            while (pos > 0) {
                swap(chars, pos - 1, pos);
                pos--;
            }
        }

        BinaryStdOut.close();
    }

    private static void swap(char[] ch, int i, int j) {
        char tmp = ch[i];
        ch[i] = ch[j];
        ch[j] = tmp;
    }

    public static void main(String[] args) {
        if (args.length != 1)
            throw new IllegalArgumentException("Expected + or -\n");
        else if (args[0].equals("+"))
            decode();
        else if (args[0].equals("-"))
            encode();
        else {
            String msg = "Unknown argument: " + args[0] + "\n";
            throw new IllegalArgumentException(msg);
        }
    }
}
