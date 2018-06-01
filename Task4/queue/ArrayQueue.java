package queue;
public class ArrayQueue extends AbstractQueue{

    private int head, tail;
    private Object[] elements;

    public ArrayQueue() {
        super();
        head = tail = 0;
        elements = new Object[2];
    }

    ArrayQueue(int capacity) {
        super();
        head = tail = 0;
        elements = new Object[2];
        ensureCapacity(capacity);
    }

    @Override
    protected Queue initQueue() {
        return new ArrayQueue();
    }

    private void ensureCapacity(int capacity) {
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

    @Override
    protected void pushFront(Object value) {
        ensureCapacity(size + 1);
        head -= 1;
        if (head < 0) {
            head = elements.length - 1;
        }
        elements[head] = value;
    }

    @Override
    protected Object getFront() {
        return elements[head];
    }

    @Override
    protected void popFront() {
        elements[head] = null;
        head++;
        if (head == elements.length) {
            head = 0;
        }
    }

    @Override
    protected void pushBack(Object value) {
        ensureCapacity(size + 1);
        elements[tail++] = value;
        if (tail == elements.length) {
            tail = 0;
        }
    }

    @Override
    protected Object getBack() {
        int position = tail - 1;
        if (position < 0) {
            position = elements.length - 1;
        }
        return  elements[position];
    }

    @Override
    protected void popBack() {
        tail -= 1;
        if (tail < 0) {
            tail = elements.length - 1;
        }
        elements[tail] = null;
    }

    @Override
    protected void doCleaning() {
        elements = new Object[2];
        head = tail = 0;
    }
}