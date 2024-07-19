package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeapComparable<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;
    private int capacity;

    public MinFourHeapComparable() {
        super();
        size = 0;
        capacity = 5; // start capacity at 2 levels and increase by one level each time it gets full
        data = (E[])new Comparable[capacity];
    }

    @Override
    public boolean hasWork() {
        return size > 0;
    }

    @Override
    public void add(E work) {
        if (work == null) { throw new IllegalArgumentException(); }
        if (size == capacity) { // if at capacity
            int newCap = 4;
            int height = height();
            for (int i = 1; i < (height); i++) {
                newCap *= 4;
            }
            capacity +=  newCap;
            E[] temp = (E[])new Comparable[capacity]; //increase capacity by 1 level
            for (int i = 0; i < size; i++) { //manually fill in new array
                temp[i] = data[i];
            }
            data = temp;
        }
        data[size] = work; //add new value in last index
        if (size != 0) {
            int child = size; //set child to new node
            int parent = (int) Math.floor((child - 1.0) / 4.0); //set parent to parent of child
            while (parent >= 0 && data[child].compareTo(data[parent]) < 0) { //if parent > child, percolate up
                E temp = data[parent];
                data[parent] = data[child];
                data[child] = temp;
                child = parent;
                parent = (int) Math.floor((child - 1.0) / 4.0);
            }
        }
        size++;
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        return data[0];
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        E value = peek(); //save deleted value to return
        data[0] = data[size-1]; //replace root with last value
        data[size-1] = null; //remove last value
        size--;
        int parent = 0;
        int smallestChild = findSmallestChild(parent); //1. find the smallest child of parent
        if (smallestChild == -1) {
            return value; //parent has no children
        }
        while (parent >= 0 && data[parent].compareTo(data[smallestChild]) > 0) { //2. swap parent with the smallest child if child < parent
            E temp = data[parent];
            data[parent] = data[smallestChild];
            data[smallestChild] = temp;
            parent = smallestChild;
            smallestChild = findSmallestChild(parent);
            if (smallestChild == -1) { //if at last level
                break; //break out of loop
            }
        }
        return value; //return deleted value
    }

    private int findSmallestChild(int parent) {
        int smallestChild = parent * 4 + 1;
        if (smallestChild >= size) { //if parent has no children
            return -1;
        }
        for (int i = 1; i < 5; i++) { //children = parent * 4 + (1, 2, 3, 4)
            if (4 * parent + i < size) {
                if (data[4 * parent + i].compareTo(data[smallestChild]) < 0) {
                    smallestChild = 4 * parent + i;
                }
            }
        }
        return smallestChild;
    }

    @Override
    public int size() {
        return size;
    }
    private int height() {
        if (size ==0 || size == 1) {
            return size;
        }
        return (int)Math.ceil(Math.log(4*size-size+1)/Math.log(4));
    }

    @Override
    public void clear() {
        while (size > 0) {
            next();
        }
        size = 0;
        capacity = 5;
    }
}
