package com.alviss.football.league;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;

public class FixedSizeDeque<T> implements Iterable<T> {
    private final Deque<T> deque;
    private final int maxSize;

    public FixedSizeDeque(int maxSize) {
        if (maxSize < 1) {
            throw new IllegalArgumentException("Max size must be at least 1");
        }
        this.maxSize = maxSize;
        deque = new LinkedList<>();
    }

    public void add(T item) {
        if (deque.size() == maxSize) {
            deque.removeFirst();
        }
        deque.addLast(item);
    }

    public T get(int index) {
        if (index < 0 || index >= deque.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return ((LinkedList<T>) deque).get(index);
    }

    public int size() {
        return deque.size();
    }

    @Override
    public Iterator<T> iterator() {
        return deque.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        for (T t : deque) {
            action.accept(t);
        }
    }
}
