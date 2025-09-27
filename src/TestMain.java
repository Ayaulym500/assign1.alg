import java.util.Arrays;
import java.util.Random;

public class TestMain {

    private static Random rnd = new Random();

    public static void main(String[] args) {
        testSorting();
        testDeterministicSelect();
        testClosestPair();
    }

    private static void testSorting() {
        System.out.println("=== Sorting Tests ===");

        int[] sizes = {20, 50, 100};
        for (int n : sizes) {
            int[] arrRandom = generateRandomArray(n, 0, 1000);
            int[] arrReverse = generateReverseArray(n);
            int[] arrSame = generateSameArray(n, 42);

            testMergeSort(arrRandom, "Random");
            testMergeSort(arrReverse, "Reverse");
            testMergeSort(arrSame, "AllSame");

            testQuickSort(arrRandom, "Random");
            testQuickSort(arrReverse, "Reverse");
            testQuickSort(arrSame, "AllSame");
        }
        System.out.println();
    }

    private static void testMergeSort(int[] arr, String type) {
        int[] copy = arr.clone();
        MergeSort.sort(arr);
        boolean ok = isSorted(arr);
        System.out.printf("MergeSort %s array: %s, Metrics: %s\n",
                type, ok ? "PASSED" : "FAILED", MergeSort.metrics);
    }

    private static void testQuickSort(int[] arr, String type) {
        int[] copy = arr.clone();
        QuickSort.sort(arr);

        boolean ok = isSorted(arr);
        int maxExpectedDepth = 2 * (int)Math.floor(Math.log(arr.length)/Math.log(2)) + 5;
        boolean depthOk = QuickSort.metrics.maxDepth <= maxExpectedDepth;

        System.out.printf("QuickSort %s array: %s, Depth OK: %s, Metrics: %s\n",
                type, ok ? "PASSED" : "FAILED", depthOk ? "YES" : "NO", QuickSort.metrics);
    }

    private static void testDeterministicSelect() {
        System.out.println("DeterministicSelect Tests");
        int trials = 100;
        boolean allPassed = true;

        for (int t = 0; t < trials; t++) {
            int n = 20 + rnd.nextInt(80);
            int[] arr = generateRandomArray(n, 0, 1000);
            int[] sorted = arr.clone();
            Arrays.sort(sorted);
            int k = rnd.nextInt(n);

            int sel = DeterministicSelect.select(arr.clone(), k);
            if (sel != sorted[k]) allPassed = false;
        }

        System.out.println("DeterministicSelect 100 random trials: " + (allPassed ? "PASSED" : "FAILED"));
        System.out.println("Metrics (last trial): " + DeterministicSelect.metrics + "\n");
    }


    private static void testClosestPair() {
        System.out.println(" ClosestPair Tests");

        int[] sizes = {5, 10, 100, 2000}; // small n for brute-force check
        for (int n : sizes) {
            ClosestPair.Point[] points = new ClosestPair.Point[n];
            for (int i = 0; i < n; i++)
                points[i] = new ClosestPair.Point(rnd.nextDouble()*1000, rnd.nextDouble()*1000);

            double dFast = ClosestPair.find(points);
            boolean ok = true;
            if (n <= 2000) {
                double dBrute = bruteForceClosest(points);
                ok = Math.abs(dFast - dBrute) < 1e-6;
            }

            System.out.printf("ClosestPair n=%d: %s, Distance=%.6f, Metrics=%s\n",
                    n, ok ? "PASSED" : "FAILED", dFast, ClosestPair.metrics);
        }
        System.out.println();
    }

    private static int[] generateRandomArray(int n, int min, int max) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rnd.nextInt(max - min + 1) + min;
        return arr;
    }

    private static int[] generateReverseArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = n - i;
        return arr;
    }

    private static int[] generateSameArray(int n, int value) {
        int[] arr = new int[n];
        Arrays.fill(arr, value);
        return arr;
    }

    private static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++)
            if (arr[i-1] > arr[i]) return false;
        return true;
    }

    private static double bruteForceClosest(ClosestPair.Point[] points) {
        double minDist = Double.MAX_VALUE;
        for (int i = 0; i < points.length; i++)
            for (int j = i+1; j < points.length; j++)
                minDist = Math.min(minDist, Math.hypot(points[i].x - points[j].x, points[i].y - points[j].y));
        return minDist;
    }
}