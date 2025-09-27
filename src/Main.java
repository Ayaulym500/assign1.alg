import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        int[] arr1 = {5,3,8,4,2,7,1,6};
        MergeSort.sort(arr1);
        System.out.println("MergeSort: " + Arrays.toString(arr1));
        System.out.println("Metrics: " + MergeSort.metrics);

        int[] arr2 = {9,7,5,3,1,2,4,6,8};
        QuickSort.sort(arr2);
        System.out.println("QuickSort: " + Arrays.toString(arr2));
        System.out.println("Metrics: " + QuickSort.metrics);

        int[] arr3 = {10,2,8,4,6,1,3,9,7,5};
        int k = 4;
        int sel = DeterministicSelect.select(arr3, k);
        System.out.println("Deterministic Select k=" + k + ": " + sel);
        System.out.println("Metrics: " + DeterministicSelect.metrics);

        ClosestPair.Point[] points = {
                new ClosestPair.Point(2,3),
                new ClosestPair.Point(12,30),
                new ClosestPair.Point(40,50),
                new ClosestPair.Point(5,1),
                new ClosestPair.Point(12,10),
                new ClosestPair.Point(3,4)
        };
        double minDist = ClosestPair.find(points);
        System.out.println("Closest Pair Distance:");
    }
}