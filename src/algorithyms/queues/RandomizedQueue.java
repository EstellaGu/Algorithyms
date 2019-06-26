package queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue <Item> implements Iterable<Item> {
    private Item[] arr;
    private int size;
    private int nextEmpty;


    private class RandomizedIterator<Item> implements Iterator<Item> {
        private Item[] randomArr;
        private int curr = 0;

        public RandomizedIterator() {
            randomArr = (Item[]) new Object[size];

            if (nextEmpty != size) {
                resizeArray(arr.length);
            }

            boolean[] isCollected = new boolean[size];

            for (int i = 0; i < size; i++) {
                int random;

                do {
                    random = StdRandom.uniform(0, size);
                } while (isCollected[random]);

                randomArr[i] = (Item) arr[random];
                isCollected[random] = true;
            }
        }

        @Override
        public boolean hasNext() {
            return curr < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item next = randomArr[curr];
            curr++;
            return next;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        arr = (Item[]) new Object[1];
        size = 0;
        nextEmpty = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resizeArray(int length) {
        Item[] newArr = (Item[]) new Object[length];
        int ptr = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                newArr[ptr] = arr[i];
                ptr++;
            }

            if (ptr == size) {
                break;
            }
        }

        arr = newArr;
        nextEmpty = size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        arr[nextEmpty] = item;

        size++;

        if (size == arr.length) {
            resizeArray(2 * size);
        } else if (nextEmpty == arr.length - 1) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == null) {
                    nextEmpty = i;
                    break;
                }
            }
        } else {
            nextEmpty++;
        }
    }

    // 随机产生一个数组下标
    private int randomNode() {
        int random;

        do {
            random = StdRandom.uniform(0, arr.length);
        } while (arr[random] == null);

        return random;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int random = randomNode();

        Item temp = arr[random];
        arr[random] = null;

        size--;

        if (size <= arr.length / 4) {
            resizeArray(arr.length / 2);
        }

        return temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int random = randomNode();

        return arr[random];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator<>();
    }


    public static void main(String[] args) {
//        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
//
//        queue.enqueue(1);
//        queue.enqueue(2);
//        queue.enqueue(3);
//        queue.enqueue(4);
//        queue.enqueue(5);
//        queue.enqueue(6);
//        queue.enqueue(7);
//
//        System.out.println(queue.dequeue());
//        System.out.println(queue.dequeue());
//        System.out.println(queue.sample());
//        System.out.println(queue.size());  // 5
//        System.out.println(queue.isEmpty());  // false
//
//        Iterator<Integer> it1 = queue.iterator();
//        Iterator<Integer> it2 = queue.iterator();
//
//        System.out.println("it1------------");
//        while (it1.hasNext()) {
//            System.out.println(it1.next());
//        }
//
//        System.out.println("it2------------");
//        while (it2.hasNext()) {
//            System.out.println(it2.next());
//        }
//
//        queue.enqueue(8);
//        queue.dequeue();
//        queue.dequeue();
//        queue.dequeue();
//        queue.dequeue();
//        queue.dequeue();
//        queue.dequeue();
//        System.out.println(queue.isEmpty());  // true
    }
}
