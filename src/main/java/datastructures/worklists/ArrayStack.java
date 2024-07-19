package datastructures.worklists;

import cse332.interfaces.trie.TrieMap;
import cse332.interfaces.worklists.LIFOWorkList;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.HashTrieMap;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private int capacity;
    private int size;
    private E[] array;

    public ArrayStack() {
        capacity = 10;
        size = 0;
        array = (E[])new Object[capacity];
    }

    @Override
    public void add(E work) {
        if (size == capacity) { //if at capacity, double capacity
            capacity *= 2;
            E[] doubleArray = (E[])new Object[capacity];
            for (int i = 0; i < size; i++) { //manual array copy
                doubleArray[i] = array[i];
            }
            array = doubleArray;
        }
        array[size] = work;//add on top
        size++; //update size
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        return array[size - 1]; //return value on top
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        E value = peek(); //save value on top
        array[size - 1] = null; //remove top
        size--; //update size
        return value; //return removed value
    }

    @Override
    public int size() {
        return size; //return size
    }

    @Override
    public void clear() {
        while (size != 0) {
            next();
        }
    }
}
