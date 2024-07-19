package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.worklists.WorkList;
import datastructures.worklists.ListFIFOQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * 1. You must implement a generic chaining hashtable. You may not
 * restrict the size of the input domain (i.e., it must accept
 * any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should rehash as appropriate (use load factor as
 * shown in class!).
 * 5. HashTable should be able to resize its capacity to prime numbers for more
 * than 200,000 elements. After more than 200,000 elements, it should
 * continue to resize using some other mechanism.
 * 6. We suggest you hard code some prime numbers. You can use this
 * list: http://primes.utm.edu/lists/small/100000.txt
 * NOTE: Do NOT copy the whole list!
 * 7. When implementing your iterator, you should NOT copy every item to another
 * dictionary/list and return that dictionary/list's iterator.
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private final WorkList<Integer> PRIMES = new ListFIFOQueue<>();
    private int tableSize; //prime number that increases by at lease double until > 200000 then size *= 2
    private double loadFactor; //(double)size / hashTable.length;
    private Supplier<Dictionary<K, V>> newChain; //supplies new dictionary objects
    private Dictionary<K, V>[] hashTable; //an array of dictionary objects

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        super();
        this.newChain = newChain;
        this.tableSize = 2;
        hashTable = new Dictionary[5];
        this.size = 0;
        this.loadFactor = 0.0;
        addToList(PRIMES, new Integer[] {11,23,47,97,197,397,797,1597,3203,6421,12853,25717,51437,102877,205754});
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) { throw new IllegalArgumentException(); }
        V returnValue = this.find(key); //if key in table, gets value, else is null
        if (returnValue == null) { //if key not in table
            size++; //increment size
            loadFactor = (double)size / hashTable.length;
        }
        if (loadFactor > 1) { //need to rehash
            Dictionary<K, V>[] newHashTable; //temporary hashTable
            if (PRIMES.hasWork()) { //if next capacity is going to stay prime
                newHashTable = new Dictionary[PRIMES.next()];
            }
            else { //next capacity is double previous capacity
                newHashTable = new Dictionary[tableSize * 2];
            }
            for (Dictionary<K, V> items : hashTable) { //rehash and copy items into new array
                if (items != null) { //if there are hashed values at this index of hash table
                    for (Item<K, V> item : items) { //for each element in chain
                        int hashValue = Math.abs(item.key.hashCode()) % newHashTable.length; //create hashed value
                        if (newHashTable[hashValue] == null) { // no dictionary stored at that index
                            newHashTable[hashValue] = newChain.get(); //create new chain
                        }
                        newHashTable[hashValue].insert(item.key, item.value); //add value at index
                    }
                }
            }
            hashTable = newHashTable; //set hash table to rehashed table
            loadFactor = (double)size / hashTable.length;
        }
        int hashValue = Math.abs(key.hashCode()) % hashTable.length; //hash value of key
        if (hashTable[hashValue] == null) {
            hashTable[hashValue] = newChain.get();
        }
        //loadFactor = (double)size / hashTable.length;
        hashTable[hashValue].insert(key, value);
        return returnValue;
    }

    @Override
    public V find(K key) {
        if (key == null) { throw new IllegalArgumentException(); }
        int hashValue = Math.abs(key.hashCode()) % hashTable.length;
        if (hashTable[hashValue] == null) {
            return null;
        }
        return hashTable[hashValue].find(key);
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new ChainingHashTableIterator();
    }

    /**
     * Temporary fix so that you can debug on IntelliJ properly despite a broken iterator
     * Remove to see proper String representation (inherited from Dictionary)
     */
    //@Override
    public String toString() {
        return "ChainingHashTable String representation goes here.";
    }
    private void addToList(WorkList<Integer> list, Integer[] primes) {
        for (int number : primes) {
            list.add(number);
        }
    }
    private class ChainingHashTableIterator extends SimpleIterator<Item<K, V>> {
        private Iterator<Item<K, V>> it;
        private int index;

        public ChainingHashTableIterator() {
            this.index = 0;
            if (hashTable[index] == null) {
                hashTable[index] = newChain.get();
            }
            it = hashTable[index].iterator();
        }

        public boolean hasNext() {
            if (it.hasNext()) {return true;}
            index++;
            while(index < hashTable.length && hashTable[index] == null) {
                index++;
            }
            if (index >= hashTable.length) {return false;}
            it = hashTable[index].iterator();
            return true;
        }

        public Item<K, V> next() {
            if (!hasNext()) { throw new NoSuchElementException(); }
            return it.next();
        }
    }
}
