import java.util.Arrays;
import java.util.Random;

public class QuickSort {

    public static Metrics metrics = new Metrics();
    private static Random rnd = new Random();

    public static void sort(int[] arr) {
        metrics.reset();
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int left, int right) {
        metrics.enterRecursion();
        while (left < right) {
            int pivotIndex = left + rnd.nextInt(right - left + 1);
            int pivotNewIndex = partition(arr, left, right, pivotIndex);
            if (pivotNewIndex - left < right - pivotNewIndex) {
                quickSort(arr, left, pivotNewIndex - 1);
                left = pivotNewIndex + 1;
            } else {
                quickSort(arr, pivotNewIndex + 1, right);
                right = pivotNewIndex - 1;
            }
        }
        metrics.exitRecursion();
    }

    private static int partition(int[] arr, int left, int right, int pivotIndex) {
        int pivotValue = arr[pivotIndex];
        swap(arr, pivotIndex, right);
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            metrics.comparisons++;
            if (arr[i] < pivotValue) swap(arr, i, storeIndex++);
        }
        swap(arr, storeIndex, right);
        return storeIndex;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
        metrics.swaps++;
    }

    public static void main(String[] args) {
        int[] arr = {9,7,5,3,1,2,4,6,8};
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println("Sorted:   " + Arrays.toString(arr));
        System.out.println("Metrics:  " + metrics);
    }
}