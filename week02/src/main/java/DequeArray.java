import java.util.Iterator;
import java.util.NoSuchElementException;

public class DequeArray<Item> implements Iterable<Item> {

    private static final int DEFAULT_INIT_CAPACITY = 4;
    private Item[] elements;
    private int head;
    private int size;

    // construct an empty deque
    public DequeArray() {
        elements = (Item[]) new Object[DEFAULT_INIT_CAPACITY];
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        enlarge();
        head = (head - 1) & (elements.length - 1);
        elements[head] = item;
        size++;
    }

    private void enlarge() {
        if (size == elements.length) {
            resize(elements.length << 1);
        }
    }

    private void resize(int n) {
        Item[] temp = (Item[]) new Object[n];
        Iterator<Item> iter = iterator();
        int i = 0;
        while (iter.hasNext()) {
            temp[i++] = iter.next();
        }
        elements = temp;
        head = 0;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        enlarge();
        int next = (head + size) & (elements.length - 1);
        elements[next] = item;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Item ret = elements[head];
        elements[head] = null;
        head = (head + 1) & (elements.length - 1);
        size--;
        shrink();
        return ret;
    }

    private void shrink() {
        if ((size == elements.length >> 2) && ((elements.length >> 1) >= DEFAULT_INIT_CAPACITY)) {
            resize(elements.length >> 1);
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        int tail = (head + size - 1) & (elements.length - 1);
        Item ret = elements[tail];
        elements[tail] = null;
        size--;
        shrink();
        return ret;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DefaultIterator(head, size);
    }

    private int capacity() {
        return elements.length;
    }

    private class DefaultIterator implements Iterator<Item> {
        private int head;
        private int n;

        public DefaultIterator(int head, int n) {
            this.head = head;
            this.n = n;
        }

        @Override
        public boolean hasNext() {
            return n != 0;
        }

        @Override
        public Item next() {
            if (n == 0) {
                throw new NoSuchElementException();
            }
            Item ret = elements[head];
            head = (head + 1) & (elements.length - 1);
            --n;
            return ret;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        DequeArray<Integer> deque = new DequeArray<>();
        deque.addFirst(3);
        deque.addLast(2);
        deque.addFirst(1);
        deque.addLast(0);


        for (Integer i : deque) {
            System.out.println(i);
        }
    }

}
