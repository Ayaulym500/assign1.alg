Divide-and-Conquer & Advanced Algorithms Project
1. Architecture Notes

MergeSort uses a reusable buffer for merging, minimizing memory allocations.
QuickSort recurses only on the smaller partition and iterates over the larger partition, keeping stack depth bounded to approximately O(log n).
Deterministic Select (Median-of-Medians) recurses only in the required partition and uses in-place partitioning to reduce allocations.
Closest Pair (2D) maintains sorted arrays of points by X and Y only once per recursion level, reducing memory overhead.

All algorithms use a Metrics system to track comparisons, swaps, allocations, and recursion depth.

2. Recurrence Analysis

MergeSort
Method: classic divide-and-conquer with linear merging.
Recurrence: T(n) = 2T(n/2) + Θ(n)
Solution (Master Theorem Case 2): Θ(n log n)
Recursion depth: log₂ n, buffer reuse reduces allocation overhead.

QuickSort (robust)
Method: randomized pivot; recurse on smaller partition, iterate larger partition.
Recurrence (average case): T(n) = T(n/2) + Θ(n)
Solution: Θ(n log n); randomized pivot prevents worst-case O(n²).
Recursion depth bounded: ≤ 2*floor(log₂ n) + O(1).

Deterministic Select (Median-of-Medians)
Method: divide into groups of 5, choose median-of-medians as pivot, recurse only into required partition.
Recurrence: T(n) = T(n/5) + T(7n/10) + Θ(n)
Solution (Akra–Bazzi intuition): Θ(n)
Guarantees linear time for all inputs.

Closest Pair (2D)
Method: sort points by X, recursively split, check strip of width d in Y-order (7–8 neighbor scan).
Recurrence: T(n) = 2T(n/2) + Θ(n)
Solution (Master Theorem Case 2): Θ(n log n)
Using sorted arrays by Y reduces number of comparisons in strip check.

3. Plots

Time vs n: shows linear-logarithmic growth for MergeSort, QuickSort, and Closest Pair; linear growth for Deterministic Select.

Depth vs n:
MergeSort: log₂ n
QuickSort: ≤ 2*floor(log₂ n) + O(1)
Select: ≤ log n
Closest Pair: log₂ n

Discussion:
MergeSort’s buffer improves cache performance and reduces constant factors.
Garbage collection has minimal effect since most algorithms use in-place operations.
Randomized pivot in QuickSort prevents stack overflow and worst-case behavior.


 Summary: Alignment vs Measurements
All algorithms produce correct results consistent with theory.MergeSort and QuickSort confirm Θ(n log n) behavior on random and adversarial arrays.Deterministic Select consistently achieves linear time even on worst-case inputs.Closest Pair matches brute-force results for small n (≤ 2000) and scales efficiently for larger n.
Recursion depth and allocation metrics align with theoretical expectations, demonstrating controlled stack usage and memory efficiency.
