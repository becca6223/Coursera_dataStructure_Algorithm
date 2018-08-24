import java.lang.IllegalArgumentException;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import java.lang.*;
import java.util.Iterator;

public class Deque <Item> implements Iterable<Item> {

    private Item[] array = (Item[]) new Object[0];
    private int num_elements = 0;
    private int array_size = 0;
    private int hpt = 0;
    private int tpt = 0;

    public Deque() {

    }

    public boolean isEmpty () {
        return this.num_elements == 0;
    }

    public int size () {
        return this.num_elements;
    }

    public void addFirst (Item item) {
        if (item == null) { throw new IllegalArgumentException(); }
        if (isFull()) { doubleSize(); }
        if (isEmpty()) {
            this.hpt = 0;
            this.tpt = 0;
        }
        else {
            this.hpt = (this.hpt + 1) % this.array_size;
        }

        this.array[this.hpt] = item;
        this.num_elements++;
    }

    public void addLast (Item item) {
        if (item == null) { throw new IllegalArgumentException(); }
        if (isFull()) { doubleSize(); }
        if (isEmpty()) {
            this.hpt = 0;
            this.tpt = 0;
        }
        else {
            this.tpt = (this.tpt + this.array_size - 1) % this.array_size;
        }

        this.array[this.tpt] = item;
        this.num_elements++;
    }

    public Item removeFirst() {
        if (isEmpty()) { throw new NoSuchElementException(); }

        int remove = this.hpt;
        this.hpt = (this.hpt + this.array_size - 1) % this.array_size;
        this.num_elements--;

        if (isQuarter()) { reduceHalf(); }

        return this.array[remove];
    }

    public Item removeLast() {
        if (isEmpty()) { throw new NoSuchElementException(); }

        int remove = this.tpt;
        this.tpt = (this.tpt + 1) % this.array_size;
        this.num_elements--;

        if (isQuarter()) { reduceHalf(); }

        return this.array[remove];
    }

    public Iterator<Item> iterator () {
        return new DequeueIterator();
    }

    private class DequeueIterator implements Iterator<Item> {
        private int total = Deque.this.num_elements;
        private int cur_hpt = Deque.this.hpt;
        private int cur_size = Deque.this.array_size;

        public boolean hasNext() { return total != 0; }
        public Item next() {
            if (!hasNext()) { throw new NoSuchElementException(); }
            int old_hpt = cur_hpt;
            cur_hpt = (cur_hpt + cur_size - 1) % cur_size;
            total--;
            return Deque.this.array[old_hpt];
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    //HELPER FUNCTIONS
    private boolean isFull() {
        return this.array_size == this.num_elements;
    }

    private void doubleSize() {
        int new_size = this.array_size == 0 ? 1 : 2 * this.array_size; //double array size
        Item[] copy = (Item[]) new Object[new_size];

        int tail_length = this.array_size - this.tpt;
        System.arraycopy(this.array, this.tpt, copy, 0, tail_length);
        if (tail_length != this.array_size) {
            //copy the head part if the array didn't get copied completely
            System.arraycopy(this.array, 0, copy, tail_length, this.array_size - tail_length);
        }


        //Update
        this.array_size = new_size;
        this.array = copy;
        this.hpt = this.num_elements - 1;
        this.tpt = 0;

    }

    private boolean isQuarter() {
        return this.array_size / 4 >= this.num_elements;
    }

    private void reduceHalf() {
        int new_size = this.array_size / 2;
        Item[] copy = (Item []) new Object[new_size];

        if (this.tpt <= this.hpt) {
            System.arraycopy(this.array, this.tpt, copy, 0, this.num_elements);
        }
        else {
            int tail_length = this.array_size - this.tpt;
            System.arraycopy(this.array, this.tpt, copy, 0, tail_length);
            System.arraycopy(this.array, 0, copy, tail_length, this.num_elements - tail_length);
        }

        //Update
        this.array_size = new_size;
        this.array = copy;
        this.tpt = 0;
        this.hpt = this.num_elements- 1;
    }

    public static void main(String[] args) {
        Deque <Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(5);
        deque.addFirst(10);
        deque.addLast(60);

        deque.removeLast();
        deque.removeLast();
        deque.removeLast();

        Iterator<Integer> itr = deque.iterator();
        while(itr.hasNext()) {
            System.out.println(itr.next());
        }

    }
}
