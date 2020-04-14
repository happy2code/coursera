import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] storage;
    public int capacity;
    private int end;
    private final int INITIAL_CAPACITY = 10;
    // construct an empty randomized queue
    public RandomizedQueue()
    {
        StdRandom.setSeed(9284519);
        storage = (Item[]) new Object[INITIAL_CAPACITY];
        capacity = 10;
        end = -1;
    }
    
    private void resize(int newCapacity){    
        if(newCapacity < INITIAL_CAPACITY)            
        {
            return;
        }
        Item[] newStorage = (Item[]) new Object[newCapacity];
        Item[] oldStorage = storage;
        int index = 0;
        for(; index <= end; index++) {
            newStorage[index++] = oldStorage[index];
        }
        capacity = newCapacity;
        end = index - 1;
        
        storage = newStorage;
        oldStorage = null;
    }

    // is the randomized queue empty?
    public boolean isEmpty()
    {
        return size() <= 0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return end + 1;
    }

    // add the item
    public void enqueue(Item item){
        if(size() == capacity){
            resize(capacity*2);
        }
        storage[++end] = item;
    }

    // remove and return a random item
    public Item dequeue(){
        int indexToRemoveFrom = GetRandomIndex(end);
        Item toReturn = storage[indexToRemoveFrom];
        storage[indexToRemoveFrom] = storage[end--];
        if(size() > 0 && size()  <= capacity/4 )
        {
            resize(capacity/2);
        }
        return toReturn;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        int indexToGetFrom = GetRandomIndex(end);
        Item toReturn = storage[indexToGetFrom];
        return toReturn;
    }

    public int GetRandomIndex(int maxIndex){
        int ret = maxIndex == 0 ? 0 : StdRandom.uniform(0, maxIndex);
        return ret;
    }

    private class RandomQueueIterator implements Iterator<Item> {
        int[] indices;
        int lastIndex;
        public RandomQueueIterator()
        {
            indices = new int[size()];
            lastIndex = indices.length - 1;
            for(int i = 0; i <= lastIndex; i++){
                indices[i] = i;
            }
        }
        public boolean hasNext() {
            return  lastIndex != -1;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            StdRandom.shuffle(indices,0,lastIndex + 1);
            Item toReturn = storage[indices[lastIndex]];
            --lastIndex;

            return toReturn;
        }

    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {
        return new RandomQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueue<Integer> randQ = new RandomizedQueue<Integer>();
        StdOut.printf("Empty queue: Size=%d, Capacity =%d, IsEmpty = %B\n",randQ.size(), randQ.capacity, randQ.isEmpty());
        for(int i = 0; i < 10; i ++){
            randQ.enqueue(i);
        }

        StdOut.printf("After adding 10 elements: Size=%d, Capacity =%d, IsEmpty = %B\n",randQ.size(), randQ.capacity, randQ.isEmpty());
        for(int i = 0; i < 10; i ++){
            StdOut.println(randQ.dequeue());
        }

        for(int i = 0; i < 10; i ++){
            randQ.enqueue(i);
        }

        StdOut.printf("Print with iterator:\n");
        Iterator<Integer> it = randQ.iterator();
        while(it.hasNext()){
            StdOut.println(it.next());
        }

    }

}