
import collinearPoints.FastCollinearPoints;
import collinearPoints.LineSegment;
import collinearPoints.Point;

public class Test {
    public static void main(String[] args) {
        Point[] points = {
                new Point(19986, 10303),
                new Point(19986, 28343),
                new Point(30425, 22945),
                new Point(6887, 5121),
                new Point(8952, 2805),
                new Point(18833, 6091),
                new Point(20438, 5374),
                new Point(29175, 18850),
                null,
                new Point(11728, 9688)
        };

        FastCollinearPoints fcp = new FastCollinearPoints(points);
        int num = fcp.numberOfSegments();
        System.out.println(num);
        LineSegment[] fsegs = fcp.segments();
        for (int i = 0; i < num; i++) {
            System.out.println(fsegs[i].toString());
        }

//        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
//        int num1 = bcp.numberOfSegments();
//        System.out.println(num1);
//        LineSegment[] bsegs = bcp.segments();
//        for (int i = 0; i < num1; i++) {
//            System.out.println(bsegs[i].toString());
//        }

//    System.out.println(points[8] == null);
    }
}
