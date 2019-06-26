package nextProgram;

import java.util.*;

class IF<T> implements Iterator{
    LinkedList<Iterator<T>> itList;
    int pointer;
    int length;

    public IF(Iterator<T>[] iterlist){
        itList = new LinkedList<>();
        itList.addAll(Arrays.asList(iterlist));
        pointer = 0;
        length = iterlist.length;
    }

    private void moveToNext() {
        while (!itList.get(pointer).hasNext()) {
            itList.remove(pointer);

            if (itList.size() == 0) {
                break;
            }

            if (pointer >= itList.size()) {
                pointer = 0;
            }
        }
    }

    public T next(){
        if (hasNext()) {
            moveToNext();

            T next = itList.get(pointer).next();
            pointer++;
            if (pointer >= itList.size()) {
                pointer = 0;
            }

            return next;
        } else {
            throw new NoSuchElementException();
        }
    }

    public boolean hasNext(){
        moveToNext();

        if (itList.size() == 0) {
            return false;
        }

        if (pointer != itList.size() - 1) {
            return true;
        }

        return itList.get(pointer).hasNext();
    }

    public static void main(String[] args) {
        ArrayList<Integer> arr1 = new ArrayList<>();
        Collections.addAll(arr1, 1, 2, 3, 6, 7, 8);
        ArrayList<Integer> arr2 = new ArrayList<>();
        Collections.addAll(arr2, 4, 5, 3);
        ArrayList<Integer> arr3 = new ArrayList<>();
        Collections.addAll(arr3, 6, 7, 8, 5, 6);


        Iterator<Integer> a = arr1.iterator();
        Iterator<Integer> b = arr2.iterator();
        Iterator<Integer> c = arr3.iterator();
        Iterator<Integer>[] iterlist = new Iterator[3];
        iterlist[0] = a;
        iterlist[1] = b;
        iterlist[2] = c;


        IF itfl = new IF(iterlist);
        while(itfl.hasNext()){
            System.out.println(itfl.next());
        }
    }
}
