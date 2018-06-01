package queue;

//Model elements[front..back]
//Inv = {size >= 0 && elements[i] != null for i = front .. back}
public class ArrayQueueADT {
    //immutable = {elements[i] == elements'[i] for i = front .. back && front == front' && back == back' && size == size'}
    //immutable(l, r) = {elements[i] == elements'[i] for i = l .. r}
    private int head = 0, tail = 0, size = 0;
    private Object[] elements = new Object[2];

    //Pre: capacity >= 0 && queue != null
    //Post: immutable(front, back) && elements.length >= capacity && capacity == capacity' && size == size' && front + size == back
    private static void ensureCapacity(ArrayQueueADT queue, final int capacity) {
        if (capacity < queue.elements.length){
            return;
        }
        int newSize = queue.elements.length * 2;
        while (newSize < capacity) {
            newSize *= 2;
        }
        Object[] newArray = new Object[newSize];
        if (queue.tail <= queue.head) {
            System.arraycopy(queue.elements, queue.head, newArray,0, queue.elements.length - queue.head);
            System.arraycopy(queue.elements, 0, newArray, queue.elements.length - queue.head, queue.tail);
        } else {
            System.arraycopy(queue.elements, queue.head, newArray, 0, queue.tail - queue.head);
        }

        queue.elements = newArray;
        queue.head = 0;
        queue.tail = queue.size;
    }

    //Pre: value != null && queue != null
    //Post: elements[back] = value && back == back' + 1 && front == front' && size == size + 1 && immutable(front', back')
    public static void enqueue(ArrayQueueADT queue, final Object value) {
        if (value == null) throw new AssertionError("Null element found.");
        ensureCapacity(queue,queue.size + 1);
        queue.elements[queue.tail++] = value;
        if (queue.tail == queue.elements.length) {
            queue.tail = 0;
        }
        queue.size += 1;
    }

    //Pre: size > 0 && queue != null
    //Post: R = elements[front] && immutable
    public static Object element(ArrayQueueADT queue) {
        if (queue.size == 0) {
            throw new AssertionError("No elements found.");
        }
        return queue.elements[queue.head];
    }

    //Pre: size > 0 && queue != null
    //Post: R = elements[front'] && front = front + 1 && back == back' && size == size - 1 && immutable(front, back')
    public static Object dequeue(ArrayQueueADT queue){
        if (queue.size == 0) {
            throw new AssertionError("No elements found.");
        }
        Object returnValue = queue.elements[queue.head];
        queue.elements[queue.head] = null;
        queue.head++;
        if (queue.head == queue.elements.length) {
            queue.head = 0;
        }
        queue.size--;
        return returnValue;
    }

    //Pre: queue != null
    //Post: R = size && immutable
    public static int size(ArrayQueueADT queue){
        return queue.size;
    }

    //Pre: queue != null
    //Post: R = (size == 0) && immutable
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }

    //Pre: queue != null
    //Post: size == 0 && front == back + 1
    public static void clear(ArrayQueueADT queue) {
        queue.elements = new Object[2];
        queue.head = queue.tail = queue.size = 0;
    }

    //Pre: value != null && queue != null
    //Post: elements[front] = value && front == front' - 1 && size == size' + 1 && back == back' &&immutable(front', back')
    public static void push(ArrayQueueADT queue, Object value) {
        if (value == null) {
            throw new AssertionError("Null element found.");
        }
        ensureCapacity(queue,queue.size + 1);
        queue.head -= 1;
        if (queue.head < 0) {
            queue.head = queue.elements.length - 1;
        }
        queue.elements[queue.head] = value;
        queue.size += 1;
    }

    //Pre: size > 0 && queue != null
    //Post: R = elements[back] && immutable
    public static Object peek(ArrayQueueADT queue){
        if (queue.size == 0) {
            throw new AssertionError("No elements found");
        }
        int position = queue.tail - 1;
        if (position < 0) {
            position = queue.elements.length - 1;
        }
        return  queue.elements[position];
    }

    //Pre: size > 0 && queue != null
    //Post: R = elements[back'] && back = back' - 1 && front == front' && size == size' && immutable(front', back)
    public static Object remove(ArrayQueueADT queue){
        if (queue.size == 0) {
            throw  new AssertionError("No elements found");
        }
        queue.tail -= 1;
        if (queue.tail < 0) {
            queue.tail = queue.elements.length - 1;
        }
        Object returnValue = queue.elements[queue.tail];
        queue.elements[queue.tail] = null;
        queue.size -= 1;
        return returnValue;
    }
}
