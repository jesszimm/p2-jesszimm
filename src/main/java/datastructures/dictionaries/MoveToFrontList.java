package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.worklists.WorkList;
import datastructures.worklists.ArrayStack;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find or insert is called on an existing key, move it
 * to the front of the list. This means you remove the node from its
 * current position and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 * elements to the front.  The iterator should return elements in
 * the order they are stored in the list, starting with the first
 * element in the list. When implementing your iterator, you should
 * NOT copy every item to another dictionary/list and return that
 * dictionary/list's iterator.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    private ListNode root;
    public MoveToFrontList() {
        root = new ListNode();
        size = 0;
    }
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {throw new IllegalArgumentException();}
        V returnValue = find(key); //if key is in list, move to front
        if (returnValue == null) { //if key not in list
            root = new ListNode(key, value, root); //make new node at front of list
            size++; //update size
        }
        else { root.value = value; }//if key is in list, set new value
        return returnValue; //return previous value or null if no previous value
    }
    @Override
    public V find(K key) {
        if (key == null) {throw new IllegalArgumentException();}
        if (root == null || root.key == null) {return null;} //if list is empty, return null
        V returnValue = null; //return value is null unless key is in list already
        ListNode prev = root; //keep track of previous node
        //ListNode curr = root.next; //keep track of current node
        if (root.key.equals(key)) { return root.value; }//if key is at root, return value at root
        while (prev.next != null && prev.next.key != null) { //iterate to key
            if (prev.next.key.equals(key)) {
                returnValue = prev.next.value; //save previous curr value to return
                ListNode temp = prev.next;
                prev.next = prev.next.next; //remove curr node from list
                temp.next = root; //put curr at front
                root = temp; //update root with curr at front
            }
            prev = prev.next;
        }
        return returnValue; //return prev key value or null if no prev value at key
    }

    //return elements in the order they are stored in the list
    @Override
    public Iterator<Item<K, V>> iterator() { return new MoveToFrontListIterator(); }
    private class MoveToFrontListIterator extends SimpleIterator<Item<K, V>> {
        private ListNode current;

        public MoveToFrontListIterator() {
            this.current = MoveToFrontList.this.root;
        }
        @Override
        public boolean hasNext() {
            return this.current != null && current.next != null;
        }

        @Override
        public Item<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item<K, V> value = new Item<K, V>(this.current.key, this.current.value);
            this.current = current.next;
            return value;
        }
    }
    public class ListNode {
        private final K key;
        public V value;
        public ListNode next;
        public ListNode() {
            this.key = null;
            this.value = null;
            this.next = null;
        }
        public ListNode(K key, V value, ListNode next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
