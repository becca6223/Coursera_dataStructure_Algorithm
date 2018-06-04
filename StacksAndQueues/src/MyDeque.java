import java.lang.IllegalArgumentException;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import java.lang.*;

public class MyDeque <Item> {
    //create a resizable dequeue that is implemented in array

    //Member variables
    private int hpt; //head pointer next available location
    private int tpt; //tail pointer next available location
    private int arraySize; //size of the array
    private int num_elements; //number of elements
    private Item[] array; //create an array with type of "Item"

    public MyDeque() {
        //Construct MyDeque to a size of 4 array
        this.array = (Item[]) new Object[4];
        this.hpt = 0;
        this.tpt = 0;
        this.num_elements = 0;
        this.arraySize = 4;
    }

    //Methods
    public boolean isEmpty() {
        return this.num_elements == 0;
    }

    public int size() {
        return this.num_elements;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        //add logic for checking if the array is full -> resize
        if (isFull()) {
            //call resize function
        }

        this.array[hpt] = item;
        this.num_elements++;

        //update head pointer
        if (hpt == 0) {
            hpt = this.arraySize-1;
        } else {
            hpt--;
        }

        //update tail pointer
        if (this.num_elements == 1) {
            tpt = (tpt + 1) % this.arraySize;
        }
    }

    public void addLast(Item item) {
        if(item == null) {
            throw new IllegalArgumentException();
        }
        //add logic for checking if the array is full -> resize

        this.array[tpt] = item;
        this.num_elements++;

        //update tail pointer
        tpt = (tpt + 1) % this.arraySize;

        //update head pointer
        if (this.num_elements == 1) {
            if (hpt == 0) {
                hpt = this.arraySize-1;
            } else {
                hpt--;
            }
        }
    }

    public Item removeFirst() {
        if (isEmpty()) {
            //throw exception
            throw new NoSuchElementException();
        }
        else {
            hpt = (hpt + 1) % this.arraySize; //the current element at the head
            this.num_elements--;

            //update tail pointer
            if (this.num_elements == 0) {
                tpt--;
                if(tpt == -1) {
                    tpt = this.arraySize-1;
                }
            }
            return this.array[hpt];
        }
    }

    public Item removeLast() {
        if (isEmpty()) {
            //throw exception
            throw new NoSuchElementException();
        }
        else {
            tpt--; //the current element at the tail
            if(tpt == -1) {
                tpt = this.arraySize-1;
            }
            this.num_elements--;

            //update head pointer
            if (this.num_elements == 0) {
                hpt = (hpt + 1) % this.arraySize;
            }
            return this.array[tpt];
        }
    }

    //helper functions
    private boolean isFull() {
        return this.num_elements == this.arraySize;
    }

    private void resize() {
        this.arraySize = 2 * this.arraySize; //double the array size
        Item[] copy = (Item[]) new Object[this.arraySize]; //create a twice bigger array
        if(tpt == hpt && tpt == 0) {
            System.arraycopy(this.array, 0, copy, 0, this.num_elements);
        }
        else {
            System.arraycopy(this.array, 0, copy, 0, tpt); //copy elements up to tpt to "copy"
            System.arraycopy(this.array, hpt);
            //pay attention to the position of the pointers
        }

    }



    //main function
    public static void main(String[] args) {
        //Test Fixed Capacity Dequeue
        String control = "last";
        MyDeque <Integer> dequeue = new MyDeque<Integer>();
        dequeue.addFirst(2);
        dequeue.addLast(45);
        dequeue.addFirst(10);
        dequeue.addLast(200);

        if(control == "first") {
            for (int i = 0; i < 4; i++) {
                System.out.println(dequeue.removeFirst());
            }
        } else {
            for (int i = 0; i < 4; i++) {
                System.out.println(dequeue.removeLast());
            }
        }

        //Test resizeable Dequeue

    }

}