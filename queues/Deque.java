
import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
        Node previous;
    }
    
    private int size = 0;
    private Node start = null;
    private Node end = null;

    
    // construct an empty deque
    public Deque(){  
        start = new Node();
        start.previous = null;
        
        end = new Node();
        end.next = null;

        start.next = end;
        end.previous = start;
    }


    // is the deque empty?
    public boolean isEmpty(){
        return start.next == end;
    }

    // return the number of items on the deque
    public int size(){
        return size;
    }

    // add the item to the front
    public void addFirst(final Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        final Node nodeToAdd = new Node();
        nodeToAdd.item = item;
        nodeToAdd.previous = start;
        nodeToAdd.next = start.next;

        nodeToAdd.next.previous = nodeToAdd;
        start.next = nodeToAdd;
        ++size;
    }

    // add the item to the back
    public void addLast(final Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        final Node nodeToAdd = new Node();
        nodeToAdd.item = item;
        nodeToAdd.previous = end.previous;
        nodeToAdd.next = end;

        nodeToAdd.previous.next = nodeToAdd;
        end.previous = nodeToAdd;
        ++size;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Node nodeToRemove = start.next;
        start.next = nodeToRemove.next;
        nodeToRemove.next.previous = start;

        final Item toReturn = nodeToRemove.item;
        nodeToRemove = null;
        --size;

        return toReturn;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Node nodeToRemove = end.previous;
        end.previous = nodeToRemove.previous;
        nodeToRemove.previous.next = end;

        final Item toReturn = nodeToRemove.item;
        nodeToRemove = null;
        --size;

        return toReturn;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = start.next;

        public boolean hasNext() {
            return current != end;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            final Item currentItem = current.item;
            current = current.next;
            return currentItem;

        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(final String[] args) {
        final Deque<String> strings = new Deque<>();
        if (strings.size() != 0) {
            StdOut.printf("new deque should be size 0, but it is %d\n", strings.size());
        }
        if (!strings.isEmpty()) {
            StdOut.printf("New deque should be empty\n");
        }

        strings.addLast("One");
        if (strings.size() != 1) {
            StdOut.printf("deque should be size = 1, but it is %d\n", strings.size());
        }

        strings.removeLast();
        if (strings.size() != 0) {
            StdOut.printf("After removal deque should be size 0, but it is %d\n", strings.size());
        }
        if (!strings.isEmpty()) {
            StdOut.printf("after removing one element from one element deque, it should be empty\n");
        }

        strings.addFirst("One");
        strings.removeFirst();
        if (strings.size() != 0) {
            StdOut.printf("After removal deque should be size 0, but it is %d\n", strings.size());
        }
        if (!strings.isEmpty()) {
            StdOut.printf("after removing one element from one element deque, it should be empty\n");
        }

        strings.addFirst("One");
        strings.addLast("Two");
        if (strings.size() != 2) {
            StdOut.printf("After removal deque should be size 0, but it is %d\n", strings.size());
        }

        final String popped = strings.removeLast();
        if (popped != "Two") {
            StdOut.printf("Popped String should be 'Two', but it is %s\n", popped);
        }

        strings.addLast("Two");
        strings.addLast("Three");
        strings.addFirst("Zero");

        for (final String s : strings) {
            StdOut.printf("%s\n",s);
        }

        strings.removeFirst();
        strings.removeLast();

        for (final String s : strings) {
            StdOut.printf("%s\n",s);
        }

    }

}