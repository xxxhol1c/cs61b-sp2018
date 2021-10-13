
public class ArrayDeque<T> {

    private int size;
    private int nextFirst;
    private int nextLast;
    private T[] items;
    private int capacity;
    private static int minCapacity = 16;
    private static double minUsage = 0.25;


    /* create the empty array and make the index point
     *  For the convenience of addFirst, I set the initial index at the last*/
    public ArrayDeque() {
        capacity = 8;
        items = (T[]) new Object[capacity];
        nextFirst = capacity - 1;
        nextLast = 0;
        size = 0;
    }

    /* Check whether the array is empty */
    public boolean isEmpty() {
        return size == 0;
    }

    /* return the size */
    public int size() {
        return size;
    }

    /** To implement the resize method, you need copy the current array to a new array
     * and need to cut the original array into two pieces */
    private void resize(int newCapacity) {
        T[] newItems  = (T[]) new Object[newCapacity];
        int currentFrontIndex = onePlus(nextFirst);
        int currentEndIndex = oneMinus(nextLast);
        // separate the array into two parts
        int lengthFront = capacity - currentFrontIndex;
        int lengthEnd = currentEndIndex + 1;
        // copy the two part
        if (currentEndIndex <= currentFrontIndex) {
            int newFrontIndex = newCapacity - lengthFront;
            System.arraycopy(items, currentFrontIndex, newItems, newFrontIndex, lengthFront);
            System.arraycopy(items, 0, newItems, 0, lengthEnd);
            nextFirst = newFrontIndex - 1;

        } else {  // special case: care the nextLast needed to be changed
            System.arraycopy(items, 0, newItems, 0, lengthEnd);
            nextFirst = newCapacity - 1;
            nextLast = lengthEnd;
        }
        capacity = newCapacity;
        items = newItems;
    }

    /* move the index forward one place and notice the circular condition */
    private int oneMinus(int index) {
        if (index == 0) {
            return capacity - 1;
        }
        return index - 1;
    }

    /* move the index back one place and notice the circular condition */
    private int onePlus(int index) {
        if (index == capacity - 1) {
            return 0;
        }
        return index + 1;
    }

    /* cut down the size after remove */
    private void cut() {
        double usage = (double) size / capacity;
        if (capacity >= minCapacity && usage < minUsage) {
            int newCapacity = capacity / 2;
            resize(newCapacity);
        }
    }

    /* add the item at the first position and modify the other attributes */
    public void addFirst(T item) {
        items[nextFirst] = item;
        nextFirst = oneMinus(nextFirst);
        size++;
        /* maybe need resize [Done] */
        if (size == capacity) {
            int newCapacity = capacity * 2;
            resize(newCapacity);
        }
    }

    /* add the item at the last position and modify the other attributes */
    public void addLast(T item) {
        items[nextLast] = item;
        nextLast = onePlus(nextLast);
        size += 1;
        /* maybe need resize [Done] */
        if (size == capacity) {
            int newCapacity = capacity * 2;
            resize(newCapacity);
        }
    }

    /* print the array deque
     * start from the index after the nextFirst and notice the circulation */
    public void printDeque() {
        int currIndex = onePlus(nextFirst);
        while (currIndex != nextLast) {
            System.out.print(items[currIndex] + " ");
            currIndex = onePlus(currIndex);
        }
        System.out.println();
    }

    /* remove and return the item at first or last
     * modify the other attributes */
    public T removeFirst() {
        int removedIndex = onePlus(nextFirst);
        T result = items[removedIndex];
        nextFirst = removedIndex;
        items[removedIndex] = null;
        size -= 1;
        /* maybe need resize DONE */
        cut();
        return result;
    }

    public T removeLast() {
        int removedIndex = oneMinus(nextLast);
        T result = items[removedIndex];
        nextLast = removedIndex;
        items[removedIndex] = null;
        size -= 1;
        /* maybe need resize DONE */
        cut();
        return result;
    }

    /** get the item of given index
     * Caution : it should take constant time, I originally used iteration */
    public T get(int index) {
        int firstIndex = onePlus(nextFirst) + index;
        if (firstIndex >= capacity) {
            firstIndex = firstIndex - capacity;
        }
        return items[firstIndex];
    }
}
