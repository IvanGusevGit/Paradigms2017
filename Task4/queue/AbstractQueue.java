package queue;

import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue {
    int size;

    private void checkForNull(Object value) {
        if (value == null) {
            throw new AssertionError("Null element found.");
        }
    }

    private void tryForGet() {
        if (isEmpty()) {
            throw new AssertionError("No elements found.");
        }
    }

    AbstractQueue() {
        size = 0;
    }

    @Override
    public void enqueue(Object value) {
        checkForNull(value);
        pushBack(value);
        size++;
    }

    @Override
    public Object element() {
        tryForGet();
        return getFront();
    }

    @Override
    public Object dequeue() {
        Object returnValue = element();
        popFront();
        size--;
        return returnValue;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
        doCleaning();
    }

    @Override
    public void push(Object value) {
        checkForNull(value);
        pushFront(value);
        size++;
    }

    @Override
    public Object peek() {
        tryForGet();
        return getBack();
    }

    @Override
    public Object remove() {
        Object returnValue = peek();
        popBack();
        size--;
        return returnValue;
    }

    @Override
    public Queue filter(Predicate<Object> predicate) {
        Queue returnQueue = initQueue();
        int length = size();
        for (int i = 0; i < length; i++) {
            Object currentElement = dequeue();
            if (predicate.test(currentElement)) {
                returnQueue.enqueue(currentElement);
            }
            enqueue(currentElement);
        }
        return returnQueue;
    }

    @Override
    public Queue map(Function<Object, Object> function) {
        Queue returnQueue = initQueue();
        int length = size();
        for (int i = 0; i < length; i++) {
            Object currentElement = dequeue();
            returnQueue.enqueue(function.apply(currentElement));
            enqueue(currentElement);
        }
        return returnQueue;
    }

    protected abstract void pushFront(Object value);

    protected abstract Object getFront();

    protected abstract void popFront();

    protected abstract void pushBack(Object value);

    protected abstract Object getBack();

    protected abstract void popBack();

    protected abstract void doCleaning();

    protected abstract Queue initQueue();
}
