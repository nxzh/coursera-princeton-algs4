import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private static class DoubleLinkedNode<Item> {
        DoubleLinkedNode<Item> prev;
        DoubleLinkedNode<Item> next;
        Item data;
    }

    private DoubleLinkedNode<Item> head = null;
    private DoubleLinkedNode<Item> tail = null;
    private int size = 0;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        DoubleLinkedNode<Item> oldHead = head;
        head = new DoubleLinkedNode<>();
        head.data = item;
        head.next = oldHead;
        if (oldHead != null) {
            oldHead.prev = head;
        } else {
            tail = head;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        DoubleLinkedNode<Item> oldTail = tail;
        tail = new DoubleLinkedNode<>();
        tail.data = item;
        tail.prev = oldTail;
        if (oldTail != null) {
            oldTail.next = tail;
        } else {
            head = tail;
        }
        size++;
    }

    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        DoubleLinkedNode<Item> ret = null;
        if (size == 1) {
            ret = head;
            head = tail = null;
        } else {
            ret = head;
            head = head.next;
            head.prev = null;
        }
        --size;
        ret.prev = ret.next = null;
        return ret.data;
    }

    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        DoubleLinkedNode<Item> ret = null;
        if (size == 1) {
            ret = head;
            head = tail = null;
        } else {
            ret = tail;
            tail = tail.prev;
            tail.next = null;
        }
        --size;
        ret.prev = ret.next = null;
        return ret.data;
    }

    @Override

    public Iterator<Item> iterator() {
        return new FifoIterator<>(head, size);
    }

    private class FifoIterator<Item> implements Iterator<Item> {
        private DoubleLinkedNode<Item> head;
        private int size;

        public FifoIterator(DoubleLinkedNode<Item> head, int size) {
            this.head = head;
            this.size = size;
        }

        @Override
        public boolean hasNext() {
            return size != 0;
        }

        @Override
        public Item next() {
            if (size == 0) {
                throw new NoSuchElementException();
            }
            DoubleLinkedNode<Item> toRet = head;
            head = head.next;
            size--;
            return toRet.data;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(3);
        deque.addLast(2);
        deque.addFirst(1);
        deque.addLast(0);


        for (Integer i : deque) {
            System.out.println(i);
        }
    }

}
