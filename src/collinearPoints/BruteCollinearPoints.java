package collinearPoints;

import java.util.Iterator;
import edu.princeton.cs.algs4.ResizingArrayQueue;

public class BruteCollinearPoints {
    private ResizingArrayQueue<LineSegment> segs;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i++) {
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

        for (int p = 0; p <= points.length - 4; p++) {  // 每一轮循环选出与p共线的点（只是4点共线）
            for (int q = p + 1; q <= points.length - 3; q++) {
                for (int r = q + 1; r <= points.length - 2; r++) {
                    if (p != q && p != r && q != r && points[p].slopeTo(points[q]) == points[p].slopeTo(points[r])) {
                        // 如果p, q, r三点共线
                        for (int s = r + 1; s <= points.length - 1; s++) {

                            if (s != p && s != q && s != r && points[p].slopeTo(points[q]) == points[p].slopeTo(points[s])) {
                                // 四点共线

                                int smallest = points[p].compareTo(points[q]) < 0 ? p : q;
                                smallest = points[smallest].compareTo(points[s]) < 0 ? smallest : s;
                                smallest = points[smallest].compareTo(points[r]) < 0 ? smallest : r;

                                int biggest = points[p].compareTo(points[q]) > 0 ? p : q;
                                biggest = points[biggest].compareTo(points[s]) > 0 ? biggest: s;
                                biggest = points[biggest].compareTo(points[r]) > 0 ? biggest : r;

                                if (points[smallest] == points[p]) {
                                    segs.enqueue(new LineSegment(points[smallest], points[biggest]));
                                }
                            }
                        }
                    }
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
