package p2.sorts;

import cse332.exceptions.NotYetImplementedException;

import java.util.Comparator;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        int left = 0;
        int right = array.length - 1;
        quickSort(array, left, right, comparator);
    }

    private static <E> void  quickSort(E[] array, int left, int right, Comparator<E> comparator) {
        if (left < right) {
            E pivotValue = array[right];
            int current = (left - 1);
            for (int i = left; i <= right - 1; i++) {
                if (comparator.compare(array[i], pivotValue) < 0) {
                    current++;
                    swap(array, current, i);
                }
            }
            swap(array, current + 1, right);
            int pivotIndex = current + 1;
            quickSort(array, left, pivotIndex - 1, comparator);
            quickSort(array, pivotIndex + 1, right, comparator);
        }
    }

    private static <E> void swap(E[] array, int x, int y) {
        E temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

}
