package queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 双头队列
 * 所有操作都要在constant worst time内实现
 * */
public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item val;
        Node next;
        Node prev;

        public Node(Item item) {
            val = item;
            next = null;
            prev = null;
        }
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        private Node curr = first;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public Item next() {
            if (curr == null) {
                throw new NoSuchElementException();
            }

            Item val = (Item) curr.val;
            curr = curr.next;

            return val;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator<Item>();
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
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

        Node newNode = new Node(item);

        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            Node oldFirst = first;
            first = newNode;
            first.next = oldFirst;
            oldFirst.prev = first;
        }

        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node newNode = new Node(item);

        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            Node oldLast = last;
            last = newNode;
            oldLast.next = last;
            last.prev = oldLast;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item val = first.val;
        first = first.next;
        if (first != null){
            first.prev = null;
        }

        size--;

        if (isEmpty()) {
            last = null;
        }

        return val;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item val = last.val;
        last = last.prev;
        if (last != null) {
            last.next = null;
        }

        size--;

        if (isEmpty()) {
            first = null;
        }

        return val;
    }


    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
//
//        deque.addFirst(1);
//        deque.addFirst(2);
//        deque.addFirst(3);
//        deque.addFirst(4);
//        deque.addFirst(5);
//        deque.addLast(6);
//        deque.addLast(7);
//        deque.addLast(8);
//        deque.addLast(9);
//
//        // first: 5, 4, 3, 2, 1, 6, 7, 8, 9 :last
//
//        System.out.println(deque.size());  // 9
//        System.out.println(deque.removeFirst());  // 5
//        System.out.println(deque.removeLast());  // 9
//        System.out.println(deque.size());  //7
//
//        Iterator<Integer> it = deque.iterator();
//
//        while (it.hasNext()) {   //  4, 3, 2, 1, 6, 7, 8
//            System.out.println(it.next());
//        }

        deque.addFirst(1);
        deque.removeFirst();
    }
}
