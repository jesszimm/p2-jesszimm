package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {
    private ListNode front;
    private ListNode back;
    private int length;
    public ListFIFOQueue() {
        clear(); //create new ListFIFOQueue
    }

    @Override
    public void add(E work) {
        if (length == 0) { //if list is empty
            front = new ListNode(work); //new node is front
            back = front;
        }
        else { //if list already started
            ListNode temp = new ListNode(work);
            back.next = temp;
            back = back.next;
        }
        length++;
    }
    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        return front.data; //return the data from the front node
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        E value = front.data; //get the data from the front node
        front = front.next; //remove the front node
        length--; //update count
        return value; //return removed value
    }

    @Override
    public int size() {
        return length; //return size of linked list
    }

    @Override
    public void clear() {
        front = new ListNode(null); //empty linked list
        back = front;
        length = 0; //set size to 0
    }

    class ListNode {
        public final E data;
        public ListNode next;
        public ListNode(E data) { // constructs a node with the given data no and link
            this(data, null);
        }
        public ListNode(E data, ListNode next) { // constructs a node with the given data and link
            this.data = data;
            this.next = next;
        }
    }

}
