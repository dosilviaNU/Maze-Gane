//Assignment 10
//Silvia, David
//dosilvia
//Donovan, Joe
//jdonovan

//Class to represent a Stack<T>
public class Stack<T> {
    Deque<T> contents;

    //Constructor for a Stack<T>
    Stack() {
        this.contents = new Deque<T>();
    }

    //Adds given item to the stack.
    void push(T item) {
        this.contents.addAtHead(item);
    }

    //Returns true if this stack is empty.
    boolean isEmpty() {
        return this.contents.size() == 0;
    }

    //Removes and returns first item in the stack.
    T pop() {
        return this.contents.removeFromHead();
    }

    int size() {
        return this.contents.size();
    }
}
