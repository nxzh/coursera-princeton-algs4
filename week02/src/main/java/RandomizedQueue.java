import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int DEFAULT_INIT_CAPACITY = 4;
    private Item[] elements;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        elements = (Item[]) new Object[DEFAULT_INIT_CAPACITY];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    private void resize(int n) {
        Item[] temp = (Item[]) new Object[n];
        for (int i = 0; i < this.n; i++) {
            temp[i] = elements[i];
        }
        elements = temp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (n == elements.length) {
            resize(elements.length << 1);
        }
        elements[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (n == 0) {
            throw new NoSuchElementException();
        }
        int i = StdRandom.uniform(n);
        Item ret = elements[i];
        elements[i] = elements[--n];
        elements[n] = null;
        if (n == (elements.length >> 2) && (elements.length >> 1) >= DEFAULT_INIT_CAPACITY) {
            resize(elements.length >> 1);
        }
        return ret;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (n == 0) {
            throw new NoSuchElementException();
        }
        int i = StdRandom.uniform(n);
        return elements[i];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private int capacity() {
        return elements.length;
    }

    private class RandomIterator implements Iterator<Item> {
        private final Item[] items;
        private int index;

        public RandomIterator() {
            this.items = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) {
                this.items[i] = elements[i];
            }
            StdRandom.shuffle(items);
        }


        @Override
        public boolean hasNext() {
            return index != items.length;
        }

        @Override
        public Item next() {
            if (index == items.length) {
                throw new NoSuchElementException();
            }
            return items[index++];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
    }
}
