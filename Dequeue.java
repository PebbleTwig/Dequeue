import java.util.*;
import java.util.stream.*;

public class Dequeue<E> extends AbstractQueue<E> implements Queue<E> {
    //Initialize the instance variables
    private int front;

    private int rear;

    private E elem[];

    private final int INITIAL_CAPACITY = 10;

    //Constructor foe Dequeue
    public Dequeue() {
        front = 0;
        rear = 0;
        elem = (E[]) new Object[INITIAL_CAPACITY];
    }

    //This method adds to the front of the Dequeue, and if there is not enough space, it creates a bigger array
    public boolean addFront(E temp) {
        if (elem.length == 0) {
            elem[0] = temp;
        } else {
            if (canAdd() == true) {
                front--;
                front = circular(front);
                elem[front] = temp;
                return true;
            } else {
                reallocate();
                addFront(temp);
            }
        }
        return false;
    }

    //This method adds to the rear of the Dequeue, and if there is not enough space, it creates a bigger array
    public boolean addRear(E temp) {
        if (elem.length == 0) {
            addFront(temp);
        } else {
            if (canAdd() == true) {
                rear++;
                rear = circular(rear);
                elem[rear] = temp;
                return true;
            } else {
                reallocate();
                addRear(temp);
            }
        }
        return false;
    }

    //This method helps make the Dequeue circular by preventing the index from going out of bounds
    public int circular(int index) {
        if (index < 0) {
            index = elem.length - front - 2;
        } else if (index >= elem.length) {
            index = rear + 1;
        }
        return index;
    }

    //This removes the element at the front of the Dequeue and returns it
    public E removeFront() {
        if (elem[front] == null) {
            throw new NoSuchElementException();
        } else {
            E temp = elem[front];
            elem[front] = null;
            front++;
            return temp;
        }
    }

    //This removes the element at the rear of the Dequeue and returns it
    public E removeRear() {
        if (elem[rear] == null) {
            throw new NoSuchElementException();
        } else {
            E temp = elem[rear];
            elem[rear] = null;
            rear--;
            return temp;
        }
    }

    //This returns the element at the front of the Dequeue, or throws an error if the Dequeue is empty
    public E peekFront() {
        if (front == rear) {
            throw new NoSuchElementException();
        } else {
            return elem[front];
        }
    }

    //This returns the element at the rear of the Dequeue, or throws an error if the Dequeue is empty
    public E peekRear() {
        if (front == rear) {
            throw new NoSuchElementException();
        } else {
            return elem[rear];
        }
    }

    public boolean empty() {
        boolean empty = true;
        for (int i = 0; i < size(); i++) {
            if (elem[i] != null) {
                empty = false;
            }
        }
        return empty;
    }

    //This checks to see if you can add to the Dequeue
    public boolean canAdd() {
        int temp = 0;
        for (int i = 0; i < elem.length; i++) {
            if (elem[i] == null) {
                temp++;
            }
        }
        if (temp > 1) {
            return true;
        } else {
            return false;
        }
    }

    //Same as addRear
    public boolean offer(E item) {
        return addRear(item);
    }

    //Same as peekFront
    public E peek() {
        return peekFront();
    }

    //Same as removeFront
    public E poll() {
        return removeFront();
    }

    //I could not get this method to work right no matter what I tried
    private void reallocate() {
        //elem = java.util.Arrays.copyOf(elem, elem.length * 2);
        E[] temp = (E[]) new Object[elem.length * 2];
        int i = 0;
        while (elem[i] != null) {
            temp[i] = elem[i];
            i++;
        }
        int j = elem.length - 1;
        int k = temp.length - 1;
        while (elem[i] != null) {
            temp[k] = elem[j];
            j--;
            k--;
        }
        front = k;
        elem = temp;
    }

    //This returns the number of elements in the Dequeue
    public int size() {
        int temp = 0;
        for (int i = 0; i < elem.length; i++) {
            if (elem[i] != null) {
                temp++;
            }
        }
        return temp;
    }

    public ListIterator<E> iterator() {
        return new myIterator();
    }

    //Iterator class
    private class myIterator implements ListIterator<E> {
        //Instance variables for the Iterator
        private E forward = elem[front];
        private E backward = elem[rear];
        private int f = front;
        private int r = rear;
        private int current = f - 1;

        //Checks to see if there is another element in the Dequeue
        public boolean hasNext() {
            if (current + 1 >= elem.length) {
                current = 0;
            }
            return elem[current + 1] != null;
        }

        //Returns the next element in the Dequeue
        public E next() {
            current++;
            return elem[current];
        }

        //Checks to see if there is an element behind current
        public boolean hasPrevious() {
            if (current - 1 <= 0) {
                current = elem.length - 1;
            }
            return elem[current - 1] != null;
        }

        //Returns the previous element
        public E previous() {
            current--;
            return elem[current];
        }


        /* this operation is not supported */
        public void add(E obj) {
            throw new UnsupportedOperationException();
        }

        /* this operation is not supported */
        public void set(E obj) {
            throw new UnsupportedOperationException();
        }

        /* this operation is not supported */
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        /* this operation is not supported */
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        /* this operation is not supported */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}