import java.util.Comparator;

//Assignment 10
//Silvia, David
//dosilvia
//Donovan, Joe
//jdonovan

//Class to represent an Edge Comparator.
class CompareEdge implements Comparator<Edge> {

    //Compares two edges by weight, if - first weight is less than second weight.
    //If 0 they are equal, if positive first weight is greater than second weight.
    public int compare(Edge first, Edge second) {
        return first.weight - second.weight;
    }

}
