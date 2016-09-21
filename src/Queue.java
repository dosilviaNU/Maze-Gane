//Assignment 10
//Silvia, David
//dosilvia
//Donovan, Joe
//jdonovan

//Class to represent a Queue<T>
public class Queue<T> {

    Deque<T> contents;

    //Constructor for a Queue<T>
    Queue() {
        this.contents = new Deque<T>();
    }

    //Adds given item to the end of the Queue.
    void enqueue(T item) {
        this.contents.addAtTail(item);
    }

    //Returns true if this Queue> is empty.
    boolean isEmpty() {
        return this.contents.size() == 0;
    }

    //Removes and returns the first item in the queue.
    T dequeue() {
        return this.contents.removeFromHead();
    }

}
