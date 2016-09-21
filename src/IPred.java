//Assignment 10
//Silvia, David
//dosilvia
//Donovan, Joe
//jdonovan

//Interface to represent boolean test.
public interface IPred<T> {
    boolean apply(T t);
}

//Class to compare data.
class CompareData<T> implements IPred<T> {
    T check;

    //constructor for CompareData takes in a T Object for comparison.
    CompareData(T check) {
        this.check = check;
    }

    //Returns true if given object matches this object.
    public boolean apply(T comp) {
        return comp.equals(check);
    }

}
