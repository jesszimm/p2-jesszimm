package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class HeapSort {
    //put every item in a min heap and call next until empty
    public static <E extends Comparable<E>> void sort(E[] array) {
        sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        //put every item in a min heap and call next until empty
        MinFourHeap<E> heap = new MinFourHeap<>(comparator);
        for (int i = 0; i < array.length; i++) {
            heap.add(array[i]);
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = heap.next();
        }
    }
}
