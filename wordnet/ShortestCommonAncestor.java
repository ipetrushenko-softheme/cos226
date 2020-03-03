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


import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.Set;

public class ShortestCommonAncestor {
    private Digraph G;
    private int root;

    public ShortestCommonAncestor(Digraph G) {
        if (!isRooted()) {
            throw new IllegalArgumentException();
        }
        this.G = G;
    }

    public boolean isRooted() {
        return acyclic() && isOneRoot();
    }

    // white = 0, green = 1, black = 2
    public boolean acyclic() {
        int[] color = new int[G.V()];
        boolean cycle = false;
        for (int u = 0; u < G.V(); ++u) {
            if (color[u] == 0) {
                cycle = findCycle(u, color, cycle);
                if (cycle) {
                    return false;
                }
            }
        }
        return !cycle;
    }

    private boolean findCycle(int u, int[] color, boolean cycle) {
        color[u] = 1;
        for (int v : G.adj(u)) {
            if (cycle) {
                break;
            }
            if (color[v] == 0) {
                cycle = findCycle(v, color, cycle);
            }
            if (color[v] == 1) {
                cycle = true;
            }
        }
        color[u] = 2;
        return cycle;
    }

    private boolean isOneRoot() {
        int root = 0;
        for (int i = 0; i < G.V(); ++i) {
            if (G.outdegree(i) == 0 && G.indegree(i) != 0) {
                this.root = i;
                root++;
            }
        }

        return root == 1;
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths from_v = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths from_w = new BreadthFirstDirectedPaths(G, w);

        Set<Integer> set = new HashSet<Integer>();
        // shortest path to root
        for (int u : from_v.pathTo(this.root)) {
            set.add(u);
        }
        Stack<Integer> new_stack = new Stack<Integer>();
        int ancestor = 0;
        for (int u : from_w.pathTo(this.root)) {
            new_stack.push(u);
        }

        while (!new_stack.isEmpty()) {
            int u = new_stack.pop();
            if (!set.contains(u)) {
                return ancestor;
            }
            ancestor = u;
        }

        return ancestor;
    }

    public int length(int u, int w) {
        int ancestor = ancestor(u, w);
        BreadthFirstDirectedPaths from_v = new BreadthFirstDirectedPaths(G, u);
        BreadthFirstDirectedPaths from_w = new BreadthFirstDirectedPaths(G, w);
        return from_v.distTo(ancestor) + from_w.distTo(ancestor);
    }

    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        int minLen = Integer.MAX_VALUE;

        for (int u : subsetA) {
            for (int w : subsetB) {
                int currLen = length(u, w);
                if (minLen > currLen) {
                    minLen = currLen;
                }
            }
        }
        return minLen;
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        int minLen = Integer.MAX_VALUE;
        for (int a : subsetA) {
            for (int b : subsetB) {
                int currLen = length(a, b);
                if (minLen > currLen) {
                    minLen = currLen;
                }
            }
        }

        return minLen;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
