package datastructures.dictionaries;
import cse332.datastructures.containers.Item;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;
import datastructures.worklists.ListFIFOQueue;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<ChainingHashTable<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new ChainingHashTable<>(MoveToFrontList::new);
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            ListFIFOQueue<Entry<A, HashTrieNode>> elements = new ListFIFOQueue<>();
            for (Item<A, HashTrieNode> element : pointers) {
                elements.add(new AbstractMap.SimpleEntry<A, HashTrieNode>(element.key, element.value));
            }
            return elements.iterator();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        size = 0;
        root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            root = new HashTrieNode();
        }
        HashTrieNode temp = (HashTrieNode) root; //create temp HashTrieNode to iterate
        for (A character : key) {
            if (temp.pointers.find(character) == null) { //if pointers doesn't have next character
                temp.pointers.insert(character, new HashTrieNode()); // add character
            }
            temp = temp.pointers.find(character); //move to next node
        }
        V returnValue = temp.value; //save prev value
        temp.value = value; //set new value
        if (returnValue == null) {
            size++; //increment size
        }
        return returnValue; //return previous value
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (!findPrefix(key)) {
            return null;
        }
        if (key.size() == 0) {
            return root.value;
        }
        HashTrieNode temp = (HashTrieNode) root; //create temp HashTrieMap to iterate
        for (A character : key) { //while key has next
            if (temp.pointers.find(character) != null) { //if pointers has next character, keep going
                temp = temp.pointers.find(character); //move to next node
            }
            else {
                return null;
            }
        }
        return temp.value; //return value at end of key
    }

    @Override
    public boolean findPrefix(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (this.size == 0) {
            return false;
        }
        HashTrieNode temp = (HashTrieNode) root; //create temp HashTrieMap to iterate
        for (A character : key) { //while key has next
            if (temp.pointers.find(character) != null) { //if pointers has next character, keep going
                temp = temp.pointers.find(character); //move to next node
            } else { // didn't have next character
                return false; //trie does not contain key, return false
            }
        }
        return true; //trie contains key, return true
    }

    @Override
    public void delete(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
