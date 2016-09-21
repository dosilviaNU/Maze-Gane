//Assignment 8
//Silvia, David
//dosilvia
//Donovan, Joe
//jdonovan

//Class to represent a data containing node.
public class Node<T> extends ANode<T> {
    T data;

    //Default constructor, sets this.next and this.prev to null.
    Node(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    //Constructor that takes to ANodes and sets thi.next and this.prev to them.
    Node(T data, ANode<T> next, ANode<T> prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
        if (next == null || prev == null) {
            throw new IllegalArgumentException("Given Nodes are Null");
        }
        else {
            next.updatePrev(this);
            prev.updateNext(this);
        }
    }

    //EFFECT:Modifies this nodes next value.
    //Sets this nodes next value to given node.
    void updateNext(ANode<T> next) {
        if (next == null) {
            throw new IllegalArgumentException("Given Nodes are Null");
        }
        this.next = next;

    }

    //EFFECT:Modifies this nodes prev value.
    //Sets this nodes prev value to given node.
    void updatePrev(ANode<T> prev) {
        if (prev == null) {
            throw new IllegalArgumentException("Given Nodes are Null");
        }
        this.prev = prev;

    }

    //EFFECT: Changes this.next and this.next's prev value.
    //Places given data at the front of the list.
    void addAtHead(T given) {
        //Will not be called on a Node.

    }

    //EFFECT: Changes this.prev and this.prev's next value.
    //Places given data at the end of the list.
    void addAtTail(T given) {
        //Will not be called on a Node.
    }

    //Returns the data contained within this node.
    T getData() {
        return this.data;
    }

    //Returns the size of this list, excluding the sentinel
    int size() {
        return 1;
    }

    //Helper function for Size().
    int sizeHelp(int acc) {
        return this.next.sizeHelp(acc + 1);
    }

    //EFFECT: Removes an item from this list.
    //Removes this node from the list, and returns the data contained within this node.
    T removeFrom() {
        this.prev.updateNext(this.next);
        this.next.updatePrev(this.prev);
        return this.data;

    }

    //Returns the first node that returns true for the given IPred.
    ANode<T> find(IPred<T> test) {
        return this.next.findHelp(test);
    }

    //Helper function for find().
    ANode<T> findHelp(IPred<T> test) {
        if (test.apply(this.data)) {
            return this;
        }
        else {
            return this.next.findHelp(test);
        }

    }

    //EFFECT: Changes this.next's prev value.
    //EFFECT: Changes this.prev's next value.
    //Removes this node from the list.
    void removeNode() {
        this.prev.updateNext(this.next);
        this.next.updatePrev(this.prev);

    }

    boolean isNode() {
        return true;
    }

}
