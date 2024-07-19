# uMessage Backend and Data Structures

## Overview

This project involves implementing various data structures and algorithms to support the backend of a chat application called **uMessage**. The focus is on creating a robust infrastructure for word suggestion, spelling correction, and autocompletion.

## Project Goals

1. **Implement Data Structures**: Build several dictionary implementations and sorting algorithms.
2. **Develop Chat Application Features**: Support features such as word suggestion and autocompletion.
3. **Enhance Java Skills**: Gain deeper understanding by avoiding built-in Java data structures.

## Key Components

### Dictionary Implementations

- **AVLTree**: Self-balancing binary search tree.
- **ChainingHashTable**: Hash table with separate chaining.
- **MoveToFrontList**: Linked list with efficient access patterns.

### Sorting Algorithms

- **HeapSort**: Sort using a MinFourHeap.
- **QuickSort**: Efficient, recursive sort.
- **TopKSort**: Extract top K elements with O(n log(k)) efficiency.

### WorkList and Trie

- **MinFourHeap**: A four-ary heap for efficient priority queue operations.
- **CircularArrayFIFOQueue**: An array-based queue with equality and comparison operations.
- **HashTrieMap**: Trie data structure for autocompletion and spelling correction.

## Application: uMessage

**uMessage** is a chat application that uses the implemented data structures to offer:

- **Word Suggestion**: Suggests words based on input.
- **Spelling Correction**: Corrects typos dynamically.
- **Autocompletion**: Completes words based on initial input.

## Development Environment

- **Java**: Core language used for the project.
- **JUnit**: Employed for testing each component.
- **IntelliJ IDEA**: Recommended IDE setup.
