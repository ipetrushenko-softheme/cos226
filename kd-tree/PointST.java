import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Stack;
import java.util.TreeMap;

public class PointST<Value> {
    private TreeMap<Point2D, Value> tree;

    // construct an empty symbol table of points
    public PointST() {
        tree = new TreeMap<>();
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return tree.size() == 0;
    }

    // number of points
    public int size() {
        return tree.size();
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        tree.put(p, val);
    }

    // value associated with point p
    public Value get(Point2D p) {
        return tree.get(p);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        return tree.containsKey(p);
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        return tree.keySet();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> stack = new Stack<>();
        for (Point2D p : points()) {
            if (rect.contains(p)) {
                stack.add(p);
            }
        }

        return stack;
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        Point2D nearest = null;
        double minDist = Double.MAX_VALUE;
        for (Point2D currPoint : points()) {
            double currDist = p.distanceSquaredTo(currPoint);
            if (minDist > currDist) {
                minDist = currDist;
                nearest = currPoint;
            }
        }
        return nearest;
    }
}
