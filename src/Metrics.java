public class Metrics {
    public long comparisons = 0;
    public long swaps = 0;
    public long allocations = 0;
    public int recursionDepth = 0;
    public int maxDepth = 0;

    public void enterRecursion() {
        recursionDepth++;
        if (recursionDepth > maxDepth) maxDepth = recursionDepth;
    }

    public void exitRecursion() {
        recursionDepth--;
    }

    public void reset() {
        comparisons = swaps = allocations = recursionDepth = maxDepth = 0;
    }

    @Override
    public String toString() {
        return "Comparisons=" + comparisons +
                ", Swaps=" + swaps +
                ", Allocations=" + allocations +
                ", MaxRecursionDepth=" + maxDepth;
    }
}
