package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;
import cse332.exceptions.NotYetImplementedException;

/**
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * <p>
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 * instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode.  This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance).  Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must be correct
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {
    //private AVLNode root;
    public AVLTree() {
        super();
    }
    public class AVLNode extends BSTNode{
        public int height;
        public AVLNode(K key, V value) {
            super(key, value);
            this.height = 1;
        }
    }
    //return the height of the node parameter
    private int height(AVLNode node) {
        if (node == null) { return 0; }
        return node.height;
    }
    //return the difference between the left tree height and right tree height
    private int balance(AVLNode node) {
        if (node == null) { return 0; }
        return height((AVLNode)(node.children[0])) - height((AVLNode)(node.children[1]));
    }

    //return the previous value at key or null if the key wasn't previously there
    public V insert(K key, V value) {
        if (key == null || value == null) { throw new IllegalArgumentException(); }
        V previous = find(key);
        root = insert(key, value, (AVLNode)root); //insert value into tree
        return previous;
    }
    //recursive insert method traverses to key node and then back up to problem node
    private AVLNode insert(K key, V value, AVLNode node) {
        //insert node into the tree
        if (node == null) { //if node is not already in the tree
            size++; //update size
            return new AVLNode(key, value); //add new node as leaf
        }
        if (key.compareTo(node.key) < 0) { //if key is less than node.key, traverse left
            node.children[0] = insert(key, value, (AVLNode)node.children[0]);
        } else if (key.compareTo(node.key) > 0) { //if key is greater than node.key, traverse right
            node.children[1] = insert(key, value, (AVLNode)node.children[1]);
        } else { //key = node.key means node is already in the tree
            node.value = value; //update value
            return node; //return node because we don't need to re-balance
        }
        //update height after insertion
        node.height = 1 + Math.max(height((AVLNode)node.children[0]), height((AVLNode)node.children[1]));
        //re-balance if needed
        if (balance(node) > 1 && key.compareTo(node.children[0].key) < 0) { // case 1: left-left imbalance
            return rightRotation(node);
        }
        if (balance(node) < -1 && key.compareTo(node.children[1].key) > 0) { // case 2: right-right imbalance
            return leftRotation(node);
        }
        if (balance(node) > 1 && key.compareTo(node.children[0].key) > 0) { // case 3: left-right imbalance
            node.children[0] = leftRotation((AVLNode)node.children[0]);
            return rightRotation(node);
        }
        if (balance(node) < -1 && key.compareTo(node.children[1].key) < 0) { // case 4: right-left imbalance
            node.children[1] = rightRotation((AVLNode)node.children[1]);
            return leftRotation(node);
        }
        return node; //no balancing necessary
    }
    public AVLNode leftRotation(AVLNode root) {
        AVLNode node = (AVLNode)root.children[1]; //node = root.right
        root.children[1] = node.children[0]; //root.right = node.left
        node.children[0] = root; //node.left = root
        //update height of involved nodes
        root.height = calcHeight(root);
        node.height = calcHeight(node);
        return node;
    }
    public AVLNode rightRotation(AVLNode root) {
        AVLNode node = (AVLNode)root.children[0]; //node = root.left
        root.children[0] = node.children[1]; //root.left = node.right
        node.children[1] = root; //node.right = root
        //update height of involved nodes
        root.height = calcHeight(root);
        node.height = calcHeight(node);
        return node;
    }

    private int calcHeight(AVLNode node) {
        return Math.max(height((AVLNode)node.children[0]), height((AVLNode)node.children[1])) + 1;
    }

}