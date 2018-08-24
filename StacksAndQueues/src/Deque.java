import java.lang.IllegalArgumentException;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import java.lang.*;
import java.util.Iterator;

public class Deque <Item> implements Iterable<Item> {

    private Item[] array = (Item[]) new Object[0];
    private int num_elements = 0;
    private int array_size = 0;
    private int hpt = -1;
    private int tpt = -1;

    public Deque() {

    }

    public boolean isEmpty () {
        return this.num_elements == 0;
    }

    public int size () {
        return this.num_elements;
    }

    public void addFirst (Item item) {
        if (isFull()) {
            doubleSize();
        }
        if (isEmpty()) {
            this.hpt = 0;
            this.tpt = 0;
        }
        else {
            this.hpt = (this.hpt + 1) % array_size;
        }

        array[this.hpt] = item;
        this.num_elements++;
    }

    public void addLast (Item item) {
        if (isFull()) {
            doubleSize();
        }
        if (isEmpty()) {
            this.hpt = 0;
            this.tpt = 0;
        }
        else {
            this.tpt = (this.tpt + this.array_size - 1) % this.array_size;
        }

        array[this.tpt] = item;
        this.num_elements++;
    }

    public Iterator<Item> iterator () {
        return new DequeueIterator();
    }

    private class DequeueIterator implements Iterator<Item> {
        private int cur_index = 0;

        public boolean hasNext() {return true;}
        public Item next() {
            return array[0];
        }

    }

    //HELPER FUNCTIONS
    private boolean isFull () {
        return this.array_size == this.num_elements;
    }

    private void doubleSize() {
        int new_size = this.array_size == 0 ? 1 : 2 * this.array_size; //double array size
        Item[] copy = (Item[]) new Object[new_size];

        int head_length = this.hpt + 1; //number of elements to be copied from 0 to hpt
        System.arraycopy(this.array, 0, copy, 0, head_length);
        if (head_length != this.array_size) {
            //copy if the previous copy didn't copy the whole array
            int tail_length = this.array_size - head_length;
            System.arraycopy(this.array, this.tpt, copy, new_size - tail_length, tail_length);

        }

        //Final Update
        this.array_size = new_size;
        this.array = copy;

    }

    public static void main(String[] args) {
        Deque <Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addLast(2);
        System.out.print("hi");
    }
}
