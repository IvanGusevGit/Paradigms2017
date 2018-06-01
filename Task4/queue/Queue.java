package queue;

import java.util.function.Function;
import java.util.function.Predicate;

//Model elements(front <== back), next(pointer), previous(pointer), size
//Inv = {size >= 0 && elements(i) != null for i = front .. back)
public interface Queue {
    //immutable = {elements(i) == elements'[i] for i = front .. back && front == front' && back == back' && size == size'}
    //immutable(l, r) = {elements(i) == elements(i) for i = front .. back}

    //Pre: value != null
    //Post: elements(back) == value && back = next(back') && front == front' && size == size' + 1 && immutable(front, back')
    void enqueue(final Object value);

    //Pre: size > 0
    //Post: R = elements(front) && immutable
    Object element();

    //Pre: size > 0
    //Post: R = elements(front') && front == next(front') && back == back' && size == size' - 1 && immutable(front, back)
    Object dequeue();

    //Pre: True
    //Post: R = size && immutable
    int size();

    //Pre: True
    //Post: R = (size == 0) && immutable
    boolean isEmpty();

    //Pre: True
    //Post: size == 0 && front == next(back)
    void clear();

    //Pre: value != null
    //Post: elements(front) = value && front == previous(front') && back == back' && size == size + 1 && immutable(front', back)
    void push(Object value);

    //Pre: size > 0
    //Post: R = elements(back) && immutable
    Object peek();

    //Pre: size > 0
    //Post: R = elements(back') && front == front' && back == previous(back') && size == size' - 1 && immutable(front, back)
    Object remove();

    Queue filter(Predicate<Object> predicate);

    Queue map(Function<Object, Object> function);
}
