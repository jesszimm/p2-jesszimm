package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    /**
     * Behaviour is undefined when k > array.length
     */
    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        MinFourHeap<E> heap = new MinFourHeap<>(comparator);
        if (k > array.length) {
            k = array.length;
        }
        for (int i = 0; i < k; i++) {
            heap.add(array[i]);
        }
        for (int i = k; i < array.length; i++) {
            if (comparator.compare(array[i],heap.peek()) > 0) {
                heap.next();
                heap.add(array[i]);
            }
        }
        for (int i = 0; i < array.length; i++) {
            if (!heap.hasWork()) {
                array[i] = null;
            }
            else {
                array[i] = heap.next();
            }
        }
    }
}
