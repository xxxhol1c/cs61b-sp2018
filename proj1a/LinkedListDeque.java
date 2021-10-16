/**This project is to implement the basic framework and method of deque,
 * some methods and implement theory has been introduced in the lecture
 * This project uses circular sentinel to implement the actual function
 * Deque is an irregular acronym of double-ended queue.
 * Double-ended queues are sequence containers with dynamic sizes
 * that can be expanded or contracted on both ends (either its front or its back).*/

public class LinkedListDeque<T> {

    private class Node {
        T item;
        Node prev;
        Node next;

        Node(T i, Node p, Node n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private int size;
    private Node sentinel;

    /* create an empty deque */
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    /* return the size */
    public int size() {
        return size;
    }

    /* check whether is empty */
    public boolean isEmpty() {
        return size == 0;
    }

    /* print each item except sentinel with space */
    public void printDeque() {
        Node curr = sentinel;
        while (curr.next != sentinel) {
            System.out.print(curr.next.item + " ");
            curr = curr.next;
        }
        System.out.println();
    }

    /* add the item at the first position */
    public void addFirst(T item) {
        Node target = new Node(item, sentinel, sentinel.next);
        sentinel.next = target;
        sentinel.next.next.prev = target;
        size++;
    }

    /* add the item at the last position */
    public void addLast(T item) {
        Node target = new Node(item, sentinel.prev, sentinel);
        sentinel.prev = target;
        sentinel.prev.prev.next = target;
        size++;
    }

    /* remove and return the item at the first position */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T node = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;
        return node;
    }

    /* remove and return the item at the last position */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T node = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return node;
    }

    /* get the item at the given index */
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        Node curr = sentinel.next;
        while (index > 0) {
            curr = curr.next;
            index--;
        }
        return curr.item;
    }

    /* uses recursive method to get the item */
    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }

    /* Need a helper function to resolve the node */
    private T getRecursiveHelper(Node curr, int i) {
        if (i == 0) {
            return curr.item;
        }
        return getRecursiveHelper(curr.next, i - 1);
    }

    /* some extra tests to check the method whether work correctly */
    private static void main(String[] args) {

        /* Test add method and remove method work correctly */
        LinkedListDeque<String> lld1 = new LinkedListDeque<>();
        lld1.addFirst("Hello");
        lld1.addLast("CS61B");
        lld1.addLast("SP-2018");
        System.out.println(lld1.removeFirst());
        System.out.println(lld1.removeLast());
        lld1.printDeque();

        /* Test get method */
        LinkedListDeque<Integer> lld2 = new LinkedListDeque<Integer>();
        lld2.addFirst(2);
        lld2.addLast(0);
        lld2.addLast(1);
        lld2.addLast(8);
        System.out.println(lld2.get(0));
        System.out.println(lld2.get(1));
        System.out.println(lld2.get(2));
        System.out.println(lld2.get(3));

        /* Check whether the list change */
        lld2.printDeque();

        /* Test getRecursive method */
        LinkedListDeque<Integer> lld3 = new LinkedListDeque<Integer>();
        lld3.addFirst(2);
        lld3.addLast(0);
        lld3.addLast(2);
        lld3.addLast(1);
        lld3.removeLast();
        System.out.println(lld3.getRecursive(0));
        System.out.println(lld3.getRecursive(1));
        System.out.println(lld3.getRecursive(2));
        // should be null
        System.out.println(lld3.getRecursive(3));

        /* Check whether the list change */
        lld3.printDeque();

    }

}
