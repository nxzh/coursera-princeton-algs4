import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;
import java.util.Iterator;

public class FastCollinearPoints {

    private final Point[] points;
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        checkNull(points);
        checkLength(points);
        this.points = Arrays.copyOf(points, points.length);
        Arrays.sort(points, (p1, p2) -> p1.compareTo(p2));
        checkDuplicate(this.points);
        if (points.length >= 4) {
            calcSegments();
        }
    }

    public int numberOfSegments() {
        if (segments != null) {
            return segments.length;
        }
        return 0;
    }

    public LineSegment[] segments() {
        if (segments != null) {
            return Arrays.copyOf(segments, segments.length);
        }
        return null;
    }

    private void calcSegments() {
        Stack<LineSegment> segmentStack = new Stack<>();
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        for (int i = 0; i < points.length; i++) {
            Arrays.sort(pointsCopy, points[i].slopeOrder());
            addLineSegment(segmentStack, pointsCopy, points[i]);
        }
        assignSegments(segmentStack);
    }

    private void addLineSegment(Stack<LineSegment> stacks, Point[] pointsCopy, Point pi) {
        int j = 0, k = 0;
        boolean isAdded = false;
        while (k < pointsCopy.length) {
            if (Double.compare(pi.slopeTo(pointsCopy[j]), pi.slopeTo(pointsCopy[k])) != 0) {
                if (!isAdded && k - j >= 3) {
                    Point end = pointsCopy[k - 1].compareTo(pointsCopy[j]) > 0 ? pointsCopy[k - 1] : pointsCopy[j];
                    stacks.push(new LineSegment(pi, end));
                }
                j = k;
                isAdded = false;
            } else {
                if (pi.compareTo(pointsCopy[k]) > 0) {
                    isAdded = true;
                }
                k++;
            }
        }
        if (!isAdded && k - j >= 3) {
            Point end = pointsCopy[k - 1].compareTo(pointsCopy[j]) > 0 ? pointsCopy[k - 1] : pointsCopy[j];
            stacks.push(new LineSegment(pi, end));
        }
    }

    private void assignSegments(Stack<LineSegment> stacks) {
        segments = new LineSegment[stacks.size()];
        Iterator<LineSegment> iter = stacks.iterator();
        int n = 0;
        while (iter.hasNext()) {
            segments[n++] = iter.next();
        }
    }

    private void checkDuplicate(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void checkNull(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void checkLength(Point[] points) {
        if (points.length < 4) {
            throw new IllegalArgumentException();
        }
    }
}
