package datastructures.worklists;


import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
    private int capacity; //total size of array
    private int indexOfFront; //where front is in the array
    private int indexOfBack; //where back is in array
    private E[] array;
    private int size; //amount of capacity being used
    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        this.capacity = capacity;
        size = 0;
        indexOfFront = 0;
        indexOfBack = 0;
        array = (E[])new Comparable[capacity];
    }

    @Override
    public void add(E work) { //adds given work to back of array
        if (isFull()) {
            throw new IllegalStateException();
        }
        if (indexOfBack == capacity - 1) { //if at capacity, loop around
            indexOfBack = 0;
            array[indexOfBack] = work;
        }
        else if (size == 0) {
            array[indexOfBack] = work;
        }
        else {
            array[indexOfBack + 1] = work; //add value to back
            indexOfBack++; //update index of back
        }
        size++; //update size
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        return array[indexOfFront]; //return value at front
    }

    @Override
    public E peek(int i) {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        i = (indexOfFront + i) % capacity;
        if (array[i] == null) {
            throw new IndexOutOfBoundsException();
        }
        return array[i];
    }

    @Override
    public E next() {
        E value = peek(); //save value at front
        array[indexOfFront] = null; //remove value from front
        if (indexOfFront == capacity - 1) { //if front is at capacity, loop around
            indexOfFront = 0; //update index of front
        }
        else {
            indexOfFront++; //update index of front
        }
        size--; //update size
        return value; //return value
    }

    @Override
    public void update(int i, E value) {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        i = (indexOfFront + i) % capacity;
        if (array[i] == null) {
            throw new IndexOutOfBoundsException();
        }
        array[i] = value; //update value at given index
    }

    @Override
    public int size() {
        return size; //return size
    }

    @Override
    public void clear() {
        for (int i = 0; i < capacity; i++) { //manually erase array
            array[i] = null;
        }
        size = 0; //reset size
        indexOfBack = 0; //reset index of back
        indexOfFront = 0; //reset index of front
    }
    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        if (this.equals(other)) {
            return 0;
        }
        for (int i = 0; i < Math.min(this.size, other.size()); i++) {
            if (!this.peek(i).equals(other.peek(i))) {
                return this.peek(i).compareTo(other.peek(i));
            }
        }
        if (this.size > other.size()) {
            return 1;
        }
        else {
            return -1;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            // Uncomment the line below for p2 when you implement equals
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;
            if (this.size != other.size()) {
                return false;
            }
            for (int i = 0; i < this.size; i++) {
                if (!this.peek(i).equals(other.peek(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (E item : this) {
            result = 31 * result + item.hashCode();
        }
        return result;
    }
}
