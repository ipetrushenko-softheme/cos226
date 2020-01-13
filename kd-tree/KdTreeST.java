import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class KdTreeST<Value> {
    private Node root;

    private class Node {
        Point2D key;
        Value val;
        Node left;
        RectHV rectangle;    // the axis-aligned rectangle corresponding to this node
        Node right;
        int size;

        public Node(Point2D k, Value v, Node l, Node r, int s, RectHV rect) {
            key = k;
            val = v;
            left = l;
            right = r;
            size = s;
            rectangle = rect;
        }
    }

    public KdTreeST() {
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void put(Point2D p, Value val) {
        if (p == null) {
            throw new IllegalArgumentException("calls put() with null key");
        }

        RectHV r = new RectHV(
                Double.MIN_NORMAL, // xmin
                Double.MIN_NORMAL, // ymin
                Double.MAX_VALUE,  // xmax
                Double.MAX_VALUE); // ymax


        root = put(root, p, val, true, r);
    }

    private Node put(Node x, Point2D p, Value v, boolean vertical, RectHV r) {
        if (x == null) {
            return new Node(p, v, null, null, 1, r);
        }

        if (x.key.equals(p)) {
            return x;
        }

        RectHV rect;

        if (vertical) {
            if (p.x() < x.key.x()) {
                rect = new RectHV(r.xmin(), r.ymin(), x.key.x(), r.ymax()); // +
                x.left = put(x.left, p, v, !vertical, rect);
            } else {
                rect = new RectHV(x.key.x(), r.ymin(), r.xmax(), r.ymax()); // +
                x.right = put(x.right, p, v, !vertical, rect);
            }
        } else {
            if (p.y() < x.key.y()) {
                rect = new RectHV(r.xmin(), r.ymin(), r.xmax(), x.key.y());
                x.left = put(x.left, p, v, !vertical, rect);
            } else {
                rect = new RectHV(r.xmin(), x.key.y(), r.xmax(), r.ymax());
                x.right = put(x.right, p, v, !vertical, rect);
            }
        }

        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Value get(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("calls get() with null key");
        }

        return get(root, p, true);
    }

    private Value get(Node x, Point2D p, boolean vertical) {
        if (x == null) {
            return null;
        }

        if (p.equals(x.key)) {
            return x.val;
        }

        if (vertical) {
            if (p.x() < x.key.x()) {
                return get(x.left, p, !vertical);
            } else {
                return get(x.right, p, !vertical);
            }
        } else {
            if (p.y() < x.key.y()) {
                return get(x.left, p, !vertical);
            } else {
                return get(x.right, p, !vertical);
            }
        }
    }

    public boolean contains(Point2D p) {
        return get(p) != null;
    }

    public Iterable<Point2D> points() {
        Queue<Node> queue = new LinkedList<Node>();
        Queue<Point2D> keys = new LinkedList<Point2D>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node x = queue.remove();
            if (x == null) continue;
            keys.add(x.key);
            queue.add(x.left);
            queue.add(x.right);
        }

        return keys;
    }

    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> stack = new Stack<Point2D>();
        return range(root, rect, stack);
    }

    private Iterable<Point2D> range(Node x, RectHV rect, Stack<Point2D> stack) {
        if (!x.rectangle.intersects(rect)) {
            return stack;
        }

        if (rect.contains(x.key)) {
            stack.add(x.key);
        }

        if (x.left != null) {
            range(x.left, rect, stack);
        }
        if (x.right != null) {
            range(x.right, rect, stack);
        }

        return stack;
    }

    public Point2D nearest(Point2D point) {
        if (isEmpty()) {
            return null;
        }
        return nearest(root, point, root.key);
    }

    private Point2D nearest(Node x, Point2D point, Point2D min) {
        if (x == null) {
            return min;
        }

        double pointDist = min.distanceSquaredTo(point);
        if (pointDist < x.rectangle.distanceSquaredTo(point)) {
            return min;
        }

        // If the current min point is closer to query than the current point
        if (x.key.distanceSquaredTo(point) < pointDist) {
            min = x.key;
        }

        // Check in which order should we iterate
        if (x.right != null && x.right.rectangle.contains(point)) {
            min = nearest(x.right, point, min);
            min = nearest(x.left, point, min);
        } else {
            min = nearest(x.left, point, min);
            min = nearest(x.right, point, min);
        }

        return min;
    }
}
