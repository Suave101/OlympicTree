import java.util.ArrayList;

/*

  Author: Alexander Doyle
  Email: adoyle2025@my.fit.edu
  Course: Data Structures and Algorithms
  Section: Section E2/E3
  Description of this file: A class that stores the mergesort alg

*/
public class MergeSort {

    /*
     * Sorts an ArrayList of Strings alphabetically using Mergesort
     */
    public static void mergeSort(ArrayList<String> list) {
        // Base Case: list with 0 or 1 element are already sorted so no sorting is required
        if (list.size() <= 1) {
            return;
        }

        // Divide the list into two parts
        int mid = list.size() / 2;

        // Left side of the array
        ArrayList<String> left = new ArrayList<>(list.subList(0, mid));

        // Right side of the list
        ArrayList<String> right = new ArrayList<>(list.subList(mid, list.size()));

        // Recursively sort the two parts
        mergeSort(left);
        mergeSort(right);

        // --- Merge the two parts into the original list ---
        // Index for the left list
        int l = 0;
        // Index for the right list
        int r = 0;
        // Index for the merged list
        int m = 0;

        // Compare the elements and merge them in alphabetical order
        while ((l < left.size()) && (r < right.size())) {
            if (left.get(l).compareTo(right.get(r)) <= 0) {
                list.set(m, left.get(l));
                l++;
                m++;
            } else {
                list.set(m, right.get(r));
                m++;
                r++;
            }
        }

        // Add any more elements left in the left list
        while (l < left.size()) {
            list.set(m, left.get(l));
            m++;
            l++;
        }

        // Add any more elements left in the right list
        while (r < right.size()) {
            list.set(m, right.get(r));
            m++;
            r++;
        }
    }
}
