import java.util.Arrays;

public class DeterministicSelect {

    public static Metrics metrics = new Metrics();

    public static int select(int[] arr, int k) {
        metrics.reset();
        return select(arr, 0, arr.length - 1, k);
    }

    private static int select(int[] arr, int left, int right, int k) {
        metrics.enterRecursion();
        if (right - left + 1 <= 5) {
            insertionSort(arr, left, right);
            metrics.exitRecursion();
            return arr[left + k];
        }
        int numMedians = (int)Math.ceil((right - left + 1) / 5.0);
        int[] medians = new int[numMedians];
        metrics.allocations += numMedians;
        for (int i = 0; i < numMedians; i++) {
            int subLeft = left + i * 5;
            int subRight = Math.min(subLeft + 4, right);
            insertionSort(arr, subLeft, subRight);
            medians[i] = arr[subLeft + (subRight - subLeft) / 2];
        }
        int pivot = select(medians, medians.length / 2);
        int pivotIndex = partitionAroundPivot(arr, left, right, pivot);
        int rank = pivotIndex - left;
        int result;
        if (k == rank) result = arr[pivotIndex];
        else if (k < rank) result = select(arr, left, pivotIndex - 1, k);
        else result = select(arr, pivotIndex + 1, right, k - rank - 1);
        metrics.exitRecursion();
        return result;
    }

    private static int partitionAroundPivot(int[] arr, int left, int right, int pivot) {
        int pivotIndex = left;
        for (int i = left; i <= right; i++) {
            metrics.comparisons++;
            if (arr[i] == pivot) { pivotIndex = i; break; }
        }
        swap(arr, pivotIndex, right);
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            metrics.comparisons++;
            if (arr[i] < pivot) swap(arr, i, storeIndex++);
        }
        swap(arr, storeIndex, right);
        return storeIndex;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
        metrics.swaps++;
    }

    private static void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left) {
                metrics.comparisons++;
                if (arr[j] > key) { arr[j + 1] = arr[j]; metrics.swaps++; j--; }
                else break;
            }
            arr[j + 1] = key;
            metrics.swaps++;
        }
    }

    public static void main(String[] args) {
        int[] arr = {10,2,8,4,6,1,3,9,7,5};
        int k = 4;
        int sel = select(arr, k);
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("k=" + k + " Select: " + sel);
        System.out.println("Metrics: " + metrics);
    }
}