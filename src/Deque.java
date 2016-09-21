
//Assignment 10
//Silvia, David
//dosilvia
//Donovan, Joe
//jdonovan

//Class to represent a doubly linked list.
public class Deque<T> {
    Sentinel<T> header;

    //Default constructor, sets header to an empty sentinel.
    Deque() {
        this.header = new Sentinel<T>();
    }

    //Constructor that takes a Sentinel and sets this.header to it.
    Deque(Sentinel<T> header) {
        this.header = header;
    }

    //EFFECT: Modifies the start of this list.
    //Places given object at the front of this list.
    void addAtHead(T given) {
        this.header.addAtHead(given);
    }

    //EFFECT: Modifies the end of this list.
    //Places given object at the ned of this list.
    void addAtTail(T given) {
        this.header.addAtTail(given);
    }

    //Returns the size of this list, excluding the header.
    int size() {
        return this.header.size();
    }

    //EFFECT: Modifies the start of this list.
    //Removes the first item from this list, and returns the data contained in that node.
    T removeFromHead() {
        return this.header.next.removeFrom();
    }

    //EFFECT: Modifies the end of this list.
    //Removes the last item from this list, and retrns the data contained in that node.
    T removeFromTail() {
        return this.header.prev.removeFrom();
    }

    //Returns the first node that returns true for the given IPred.
    //Returns this.header otherwise.
    ANode<T> find(IPred<T> test) {
        return this.header.find(test);
    }

    //EFFECT: Removes a node from this list.
    //Removes the given node from this list.
    //Runtime exception if given is sentinel.
    void removeNode(ANode<T> given) {

        this.header.find(new CompareData<T>(given.getData())).removeNode();

    }

}
