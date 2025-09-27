import java.util.Arrays;

public class MergeSort {

    public static Metrics metrics = new Metrics();

    public static void sort(int[] arr) {
        metrics.reset();
        int[] buffer = new int[arr.length];
        metrics.allocations += buffer.length;
        sort(arr, buffer, 0, arr.length - 1);
    }

    private static void sort(int[] arr, int[] buffer, int left, int right) {
        metrics.enterRecursion();
        if (right - left <= 16) {
            insertionSort(arr, left, right);
            metrics.exitRecursion();
            return;
        }
        int mid = left + (right - left) / 2;
        sort(arr, buffer, left, mid);
        sort(arr, buffer, mid + 1, right);
        merge(arr, buffer, left, mid, right);
        metrics.exitRecursion();
    }

    private static void merge(int[] arr, int[] buffer, int left, int mid, int right) {
        int i = left, j = mid + 1, k = left;
        while (i <= mid && j <= right) {
            metrics.comparisons++;
            if (arr[i] <= arr[j]) buffer[k++] = arr[i++];
            else buffer[k++] = arr[j++];
            metrics.swaps++;
        }
        while (i <= mid) { buffer[k++] = arr[i++]; metrics.swaps++; }
        while (j <= right) { buffer[k++] = arr[j++]; metrics.swaps++; }
        for (i = left; i <= right; i++) arr[i] = buffer[i];
    }

    private static void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left) {
                metrics.comparisons++;
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    metrics.swaps++;
                    j--;
                } else break;
            }
            arr[j + 1] = key;
            metrics.swaps++;
        }
    }

    public static void main(String[] args) {
        int[] arr = {5,3,8,4,2,7,1,6};
        System.out.println(  Arrays.toString(arr));
        sort(arr);
        System.out.println("Sorted:   " + Arrays.toString(arr));
        System.out.println("Metrics:  " + metrics);
    }
}
