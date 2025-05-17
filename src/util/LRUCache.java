package util;

import java.util.HashMap;

public class LRUCache<K, V> {
    private final int capacity;
    private final HashMap<K, Node> cache;
    private Node head;
    private Node tail;

    // Node class for doubly linked list
    private class Node {
        K key;
        V value;
        Node prev;
        Node next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        // Initialize dummy head and tail nodes
        this.head = new Node(null, null);
        this.tail = new Node(null, null);
        head.next = tail;
        tail.prev = head;
    }

    // Get value for key and mark as most recently used
    public V get(K key) {
        if (!cache.containsKey(key)) {
            return null;
        }
        Node node = cache.get(key);
        // Remove from current position
        removeNode(node);
        // Add to end (most recently used)
        addToEnd(node);
        return node.value;
    }

    // Put key-value pair in cache
    public void put(K key, V value) {
        if (cache.containsKey(key)) {
            // Update existing node
            Node node = cache.get(key);
            node.value = value;
            removeNode(node);
            addToEnd(node);
        } else {
            // Create new node
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addToEnd(newNode);

            // If cache is full, remove least recently used (head)
            if (cache.size() > capacity) {
                Node lruNode = head.next;
                removeNode(lruNode);
                cache.remove(lruNode.key);
            }
        }
    }

    // Remove a node from the list
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // Add node to the end of the list (most recently used)
    private void addToEnd(Node node) {
        node.prev = tail.prev;
        node.next = tail;
        tail.prev.next = node;
        tail.prev = node;
    }

    // Remove key-value pair from cache
    public void remove(K key) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            removeNode(node);
            cache.remove(key);
        }
    }

    // Get current size of cache
    public int size() {
        return cache.size();
    }

    // Check if key exists in cache
    public boolean containsKey(K key) {
        return cache.containsKey(key);
    }

    // Clear the cache
    public void clear() {
        cache.clear();
        head.next = tail;
        tail.prev = head;
    }
} 