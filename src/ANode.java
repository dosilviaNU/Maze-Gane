//Assignment 10
//Silvia, David
//dosilvia
//Donovan, Joe
//jdonovan

//Abstract class node. Superclass for Node and Sentinel.
abstract public class ANode<T> {
    ANode<T> next;
    ANode<T> prev;

    //Constructor for ANode.
    ANode(ANode<T> next, ANode<T> prev) {

        this.next = next;
        this.prev = prev;
    }

    //Empty constructor to allow for empty Sentinel constructor.
    ANode() {
        //Empty constructor to allow for empty Sentinel constructor.
    }

    boolean isSentinel() {
        return false;
    }

    boolean isNode() {
        return false;
    }

    //EFFECT: Changes this nodes next value.
    //Replaces this.next with given next value.
    abstract void updateNext(ANode<T> next);

    //EFFECT: Changes this nodes prev value.
    //Replaces this.prev with given prev value.
    abstract void updatePrev(ANode<T> prev);

    //EFFECT: Changes this.next and this.next's prev value.
    //Places given data at the front of the list.
    abstract void addAtHead(T given);

    //EFFECT: Changes this.prev and this.prev's next value.
    //Places given data at the end of the list.
    abstract void addAtTail(T given);

    //Returns the data contained within this node.
    abstract T getData();

    //Returns the size of this list, excluding the sentinel.
    abstract int size();

    //Helper function for Size().
    abstract int sizeHelp(int acc);

    //EFFECT: Removes an item from this list.
    //Removes this node from the list, and returns the data contained within this node.
    abstract T removeFrom();

    //Returns the first node that returns true for the given IPred.
    abstract ANode<T> find(IPred<T> test);

    //Helper function for find().
    abstract ANode<T> findHelp(IPred<T> test);

    //EFFECT: Changes this.next's prev value.
    //EFFECT: Changes this.prev's next value.
    //Removes this node from the list.
    abstract void removeNode();

}
