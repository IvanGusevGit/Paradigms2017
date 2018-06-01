package queue;

public class LinkedQueue extends AbstractQueue{
    private Node head;

    private class Node {
        private Object value;
        Node next, previous;
        Node() {}
        Node(Object value, Node next, Node previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }

    @Override
    protected Queue initQueue() {
        return new LinkedQueue();
    }

     public LinkedQueue() {
        super();
        head = new Node();
        head.next = head;
        head.previous = head;
        head.value = 0;
    }

    @Override
    protected void pushFront(Object value) {
        Node newNode = new Node(value, head.next, head);
        head.next.previous = newNode;
        head.next = newNode;
    }

    @Override
    protected Object getFront() {
        return head.next.value;
    }

    @Override
    protected void popFront() {
        Node currentNode = head.next;
        currentNode.next.previous = currentNode.previous;
        currentNode.previous.next = currentNode.next;
    }

    @Override
    protected void pushBack(Object value) {
        Node newNode = new Node(value, head, head.previous);
        head.previous.next = newNode;
        head.previous = newNode;
    }

    @Override
    protected Object getBack() {
        return head.previous.value;
    }

    @Override
    protected void popBack() {
        Node currentNode = head.previous;
        currentNode.previous.next = currentNode.next;
        currentNode.next.previous = currentNode.previous;
    }

    @Override
    protected void doCleaning() {
        head.next = head;
        head.previous = head;
    }
}
