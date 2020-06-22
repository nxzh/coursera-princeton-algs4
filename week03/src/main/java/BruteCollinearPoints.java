import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;
import java.util.Iterator;

public class BruteCollinearPoints {

    private final Point[] points;
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        checkNull(points);
        this.points = Arrays.copyOf(points, points.length);
        Arrays.sort(this.points, (p1, p2) -> p1.compareTo(p2));
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
        return new LineSegment[0];
    }

    private void calcSegments() {
        Stack<LineSegment> segmentStack = new Stack<>();
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        Point p0 = points[i];
                        Point p1 = points[j];
                        Point p2 = points[k];
                        Point p3 = points[l];
                        if (p0.slopeTo(p1) == p0.slopeTo(p2) && p0.slopeTo(p2) == p0.slopeTo(p3)) {
                            segmentStack.push(new LineSegment(p0, p3));
                        }
                    }
                }
            }
        }
        assignSegments(segmentStack);
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
