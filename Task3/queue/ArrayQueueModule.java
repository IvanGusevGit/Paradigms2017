package queue;

//Model elements[front..back]
//Inv = {size >= 0 && elements[i] != null for i = front .. back}
public class ArrayQueueModule {
    //immutable = {elements[i] == elements'[i] for i = front .. back && front == front' && back == back' && size == size'}
    //immutable(l, r) = {elements[i] == elements'[i] for i = l .. r}
    private static int head = 0, tail = 0, size = 0;
    private static Object[] elements = new Object[2];

    //Pre: capacity >= 0
    //Post: immutable(front, back) && elements.length >= capacity && capacity == capacity' && size == size' && front + size == back
    private static void ensureCapacity(int capacity) {
        if (capacity < elements.length){
            return;
        }
        int newSize = elements.length * 2;
        while (newSize < capacity) {
            newSize *= 2;
        }
        Object[] newArray = new Object[newSize];
        if (tail <= head) {
            System.arraycopy(elements, head, newArray,0, elements.length - head);
            System.arraycopy(elements, 0, newArray, elements.length - head, tail);
        } else {
            System.arraycopy(elements, head, newArray, 0, tail - head);
        }

        elements = newArray;
        head = 0;
        tail = size;
    }

    //Pre: value != null
    //Post: elements[back] = value && back == back' + 1 && front == front' && size == size + 1 && immutable(front', back')
    public static void enqueue(final Object value) {
        if (value == null) throw new AssertionError("Null element found.");
        ensureCapacity(size + 1);
        elements[tail++] = value;
        if (tail == elements.length) {
            tail = 0;
        }
        size += 1;
    }

    //Pre: size > 0
    //Post: R = elements[front] && immutable
    public static Object element() {
        if (size == 0) {
            throw new AssertionError("No elements found.");
        }
        return elements[head];
    }

    //Pre: size > 0
    //Post: R = elements[front'] && front = front + 1 && back == back' && size == size - 1 && immutable(front, back')
    public static Object dequeue(){
        if (size == 0) {
            throw new AssertionError("No elements found.");
        }
        Object returnValue = elements[head];
        elements[head] = null;
        head++;
        if (head == elements.length) {
            head = 0;
        }
        size--;
        return returnValue;
    }

    //Pre: True
    //Post: R = size && immutable
    public static int size(){
        return size;
    }

    //Pre: True
    //Post: R = (size == 0) && immutable
    public static boolean isEmpty() {
        return size == 0;
    }

    //Pre: True
    //Post: size == 0 && front == back + 1
    public static void clear() {
        elements = new Object[2];
        head = tail = size = 0;
    }

    //Pre: value != null
    //Post: elements[front] = value && front == front' - 1 && size == size' + 1 && back == back' &&immutable(front', back')
    public static void push(Object value) {
        if (value == null) {
            throw new AssertionError("Null element found.");
        }
        ensureCapacity(size + 1);
        head -= 1;
        if (head < 0) {
            head = elements.length - 1;
        }
        elements[head] = value;
        size += 1;
    }

    //Pre: size > 0
    //Post: R = elements[back] && immutable
    public static Object peek(){
        if (size == 0) {
            throw new AssertionError("No elements found");
        }
        int position = tail - 1;
        if (position < 0) {
            position = elements.length - 1;
        }
        return  elements[position];
    }

    //Pre: size > 0
    //Post: R = elements[back'] && back = back' - 1 && front == front' && size == size' && immutable(front', back)
    public static Object remove(){
        if (size() == 0) {
            throw  new AssertionError(" No elements found");
        }
        tail -= 1;
        if (tail < 0) {
            tail = elements.length - 1;
        }
        Object returnValue = elements[tail];
        elements[tail] = null;
        size -= 1;
        return returnValue;
    }
}
