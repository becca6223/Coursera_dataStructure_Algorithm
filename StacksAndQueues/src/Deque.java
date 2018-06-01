import java.util.Iterator;

public class Deque <Item> implements Iterable <Item> {
    //inner class
    private class Node {
        Item item;
        Node next;
    }

    //member variables
    private Node head;
    private Node secondLast;
    private Node last;
    private int n;

    public Deque() {
        //Constructor: create an empty Deque
        this.head = null;
        this.secondLast = null;
        this.n = 0; //will help size function
    }

    public boolean isEmpty() {
        return this.n == 0;
    }

    public int size() {
        return this.n;
    }

    public void addFirst(Item item) {
        Node oldHead = this.head;

        this.head = new Node();
        this.head.next = oldHead;
        this.head.item = item;

        this.n += 1;
        update2ndLast();
    }

    public void addLast(Item item) {

    }
    public Iterator<Item> iterator() {
        //What is iterator?
        return iterator();
    }

    //Private methods
    private void update2ndLast() {
        if(this.n == 2) {
            this.secondLast = this.head;
        }
        else if(this.n > 2) {
            this.secondLast = this.secondLast.next;
        }
        else {
            this.secondLast = null;
        }
    }

   /* public static void main(String[] args) throws Exception {

    }*/
}
