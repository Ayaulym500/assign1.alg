import java.util.Arrays;

public class ClosestPair {

    public static Metrics metrics = new Metrics();

    public static class Point {
        public double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }

    public static double find(Point[] points) {
        metrics.reset();
        Point[] px = points.clone();
        Arrays.sort(px, (a,b) -> Double.compare(a.x, b.x));
        Point[] py = points.clone();
        Arrays.sort(py, (a,b) -> Double.compare(a.y, b.y));
        return closestRec(px, py, points.length);
    }

    private static double closestRec(Point[] px, Point[] py, int n) {
        metrics.enterRecursion();
        if (n <= 3) {
            double minDist = Double.MAX_VALUE;
            for (int i = 0; i < n; i++)
                for (int j = i + 1; j < n; j++)
                    minDist = Math.min(minDist, dist(px[i], px[j]));
            metrics.exitRecursion();
            return minDist;
        }
        int mid = n / 2;
        Point midPoint = px[mid];

        Point[] pyl = Arrays.stream(py).filter(p -> p.x <= midPoint.x).toArray(Point[]::new);
        Point[] pyr = Arrays.stream(py).filter(p -> p.x > midPoint.x).toArray(Point[]::new);

        double dl = closestRec(Arrays.copyOfRange(px, 0, mid), pyl, mid);
        double dr = closestRec(Arrays.copyOfRange(px, mid, n), pyr, n - mid);
        double d = Math.min(dl, dr);

        Point[] strip = Arrays.stream(py).filter(p -> Math.abs(p.x - midPoint.x) < d).toArray(Point[]::new);
        metrics.allocations += strip.length;
        double minStrip = d;
        for (int i = 0; i < strip.length; i++) {
            for (int j = i + 1; j < strip.length && j <= i + 7; j++) {
                minStrip = Math.min(minStrip, dist(strip[i], strip[j]));
                metrics.comparisons++;
            }
        }
        metrics.exitRecursion();
        return minStrip;
    }

    private static double dist(Point a, Point b) {
        double dx = a.x - b.x, dy = a.y - b.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    public static void main(String[] args) {
        Point[] points = {
                new Point(2,3),
                new Point(12,30),
                new Point(40,50),
                new Point(5,1),
                new Point(12,10),
                new Point(3,4)
        };
        double minDist = find(points);
        System.out.println("Closest Pair Distance: " + minDist);
        System.out.println("Metrics: " + metrics);
    }
}