package collinearPoints;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import edu.princeton.cs.algs4.ResizingArrayQueue;

public class FastCollinearPoints {
    private ResizingArrayQueue<LineSegment> segs;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i++) {
            // System.out.println(i);
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            for (int j = 0; j < points.length; j++) {
                if (points[j] == null) {
                    throw new IllegalArgumentException();
                }

                if (i != j && points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException();
                }
            }

        }

        segs = new ResizingArrayQueue<>();

        Point[] tmp = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            tmp[i] = points[i];
        }

        for (int i = 0; i < tmp.length; i++) {
            Comparator<Point> cmp = tmp[i].slopeOrder();
            Arrays.sort(points, cmp);

            Point curr = tmp[i];

            // 滑动窗口找到最长共线子串
            int left = 0;
            int right = 0;
            int smallest = 0;
            int biggest = 0;
            double colSlope = curr.slopeTo(points[left]);
            while (true) {
                if (right < points.length && curr.slopeTo(points[right]) == colSlope) {
                    smallest = points[smallest].compareTo(points[right]) < 0 ? smallest : right;
                    biggest = points[biggest].compareTo(points[right]) > 0 ? biggest : right;
                } else {
                    if (right - left >= 3 && curr == points[smallest]) {
                        // left -> right - 1共线，但是如果当前节点不是smallest，就不加入，这样可以避免重复
                        segs.enqueue(new LineSegment(points[smallest], points[biggest]));
                    }

                    if (right < points.length) {
                        left = right;
                        smallest = 0;
                        biggest = 0;
                        colSlope = curr.slopeTo(points[left]);
                        right--;
                    }
                }

                right++;

                if (right > points.length) {
                    break;
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segs.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] tmp = new LineSegment[segs.size()];
        Iterator<LineSegment> it = segs.iterator();
        int i = 0;
        while (it.hasNext()) {
            tmp[i] = it.next();
            i++;
        }
        return tmp;
    }
}
