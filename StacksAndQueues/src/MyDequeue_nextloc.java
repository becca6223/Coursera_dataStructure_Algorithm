import jdk.internal.cmm.SystemResourcePressureImpl;

import java.lang.IllegalArgumentException;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import java.lang.*;

public class MyDequeue_nextloc <Item> {
    //create a resizable dequeue that is implemented in array

    //Member variables
    private int hpt; //head pointer next available location
    private int tpt; //tail pointer next available location
    private int arraySize; //size of the array
    private int num_elements; //number of elements
    private Item[] array; //create an array with type of "Item"

    public MyDequeue_nextloc() {
        //Construct MyDeque_nextloc to a size of 4 array
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
        //add logic for checking if the array is full -> resize_grow
        if (isFull()) {
            //call resize_grow function
            resize_grow();
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
        if (isFull()) {
            //add logic for checking if the array is full -> resize_grow
            resize_grow();
        }

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

    private void resize_grow() {
        this.arraySize = 2 * this.arraySize; //double the array size
        Item[] copy = (Item[]) new Object[this.arraySize]; //create a twice bigger array
        int head = (this.hpt + 1) % this.num_elements;
        System.arraycopy(this.array, head, copy, 0, this.num_elements - head);
        System.arraycopy(this.array, 0, copy, this.num_elements - head, __tailPos(this.tpt) + 1);

        //after copy the orig array to bigger array, update hpt, tpt
        this.hpt = this.arraySize - 1;
        this.tpt = this.num_elements;

        this.array = copy;
    }

    private int __tailPos(int cur_tpt) {
        //the actual tail position, not next available location for tail
        if(cur_tpt - 1 < 0) {
            cur_tpt = this.num_elements - 1;
        }
        else {
            cur_tpt--;
        }
        return cur_tpt;
    }

    //main function
    public static void main(String[] args) {
        //Test Fixed Capacity Dequeue
        String control = "pass";
        MyDequeue_nextloc <Integer> dequeue = new MyDequeue_nextloc<Integer>();
        dequeue.addFirst(2);
        dequeue.addLast(45);
        dequeue.addFirst(10);
        dequeue.addLast(200);

        if(control == "first") {
            for (int i = 0; i < 4; i++) {
                System.out.println(dequeue.removeFirst());
            }
        } else if (control == "last") {
            for (int i = 0; i < 4; i++) {
                System.out.println(dequeue.removeLast());
            }
        }

        //example of copying an array
        /*int [] hi = new int[4];
        hi[0] = 1;
        hi[1] = 2;
        hi[2] = 3;
        hi[3] = 4;

        int[] copy = new int[8];
        System.arraycopy(hi,3, copy, 0, 1);*/


        //Test resize_grow Dequeue
        dequeue.addFirst(300);
        System.out.println("DONE");

    }

}