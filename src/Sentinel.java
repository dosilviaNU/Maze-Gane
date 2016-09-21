//Assignment 10
//Silvia, David
//dosilvia
//Donovan, Joe
//jdonovan

//Class to represent the start of a list.
public class Sentinel<T> extends ANode<T> {

    //Default constructor for a sentinel. 
    //Sets this.next and this.prev to this sentinel.
    Sentinel() {
        this.next = this;
        this.prev = this;

    }

    //Constructor for sentinel that takes two ANodes and
    //sets this.next and this.prev to them.
    Sentinel(ANode<T> next, ANode<T> prev) {
        super(next, prev);
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
        this.next = new Node<T>(given, this.next, this);
    }

    //EFFECT: Changes this.prev and this.prev's next value.
    //Places given data at the end of the list.
    void addAtTail(T given) {
        this.prev = new Node<T>(given, this, this.prev);
    }

    //Returns the data contained within this node.
    T getData() {
        return null;
    }

    //Returns the size of this list, excluding the sentinel
    int size() {
        return this.next.sizeHelp(0);
    }

    //Helper function for Size().
    int sizeHelp(int acc) {
        return acc;
    }

    //EFFECT: Removes an item from this list.
    //Removes this node from the list, and returns the data contained within this node.
    T removeFrom() {

        throw new RuntimeException("Cannot remove from an empty list.");
    }

    //Returns the first node that returns true for the given IPred.
    ANode<T> find(IPred<T> test) {
        return this.next.findHelp(test);
    }

    //Helper function for find().
    ANode<T> findHelp(IPred<T> test) {
        return this;

    }

    //EFFECT: Changes this.next's prev value.
    //EFFECT: Changes this.prev's next value.
    //Removes this node from the list.
    void removeNode() {
        throw new RuntimeException("Cannot remove the sentinel.");
    }

    boolean isSentinel() {
        return true;
    }

}
