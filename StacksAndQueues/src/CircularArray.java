import java.util.Random;

public class CircularArray {
    //Implement a circular array (circular queue)
    /*Method:
    enqueue
    isEmpty
    isFull
    dequeue
     */

    //
    private int array[];
    private int size;
    private int write;
    private int read;
    private int numElements;

    public CircularArray(int size) {
        /* ARGS
        size: default initialized array size
         */
        this.array = new int[size];
        this.size = size;
        this.numElements = 0;
        this.write = 0;
        this.read = 0;
    }

    public boolean isEmpty() {
        return numElements == 0;
    }

    public boolean isFull() {
        return numElements == size;
    }

    public boolean enqueue (int element) {
        if (isFull()) {
            System.out.println("\nThe array is full!");
            return false;
        }
        else {
            this.array[this.write] = element;
            this.write = (this.write + 1) % this.size; //update write pointer
            this.numElements++;
            return true;
        }
    }

    public int deque () {

        if (isEmpty()) {
            System.out.println("\nThe array is empty!");
            return 0;
        }
        else {
            int value = this.array[this.read];
            this.read = (this.read + 1) % this.size; //update read pointer
            this.numElements--;
            return value;
        }
    }

    public static void main (String[] args)
    {
        int size = 10;
        Random rand = new Random();
        int range = 1000;

        CircularArray cir_array = new CircularArray(size-2);

        System.out.println("Writing operation");
        for(int i = 0; i < size; i++) {
            int value = rand.nextInt(range);
            if (cir_array.enqueue(value)) {
                System.out.print(value + " ");
            }
        }

        System.out.println("\nReading operation");
        for(int i = 0; i < size; i++) {
            System.out.print(cir_array.deque() + " ");
        }
    }


}
