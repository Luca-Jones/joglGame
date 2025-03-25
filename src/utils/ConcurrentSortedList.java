package utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConcurrentSortedList<T extends Comparable<? super T>> implements List<T> {

    private CopyOnWriteArrayList<T> elements;

    public ConcurrentSortedList() {
        this.elements = new CopyOnWriteArrayList<>();
    }

    @Override
    synchronized public int size() {
        return elements.size();
    }

    @Override
    synchronized public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    synchronized public boolean contains(Object o) {
        return elements.contains(o);
    }

    @Override
    synchronized public Iterator<T> iterator() {
        return elements.iterator();
    }

    @Override
    synchronized public Object[] toArray() {
        return elements.toArray();
    }

    @SuppressWarnings("hiding")
    @Override
    synchronized public <T> T[] toArray(T[] a) {
        return elements.toArray(a);
    }

    @Override
    synchronized public boolean add(T e) {
        boolean result = elements.add(e);
        elements.sort(T::compareTo);
        return result;
    }

    @Override
    synchronized public boolean remove(Object o) {
        return elements.remove(o);
    }

    @Override
    synchronized public boolean containsAll(Collection<?> c) {
        return elements.containsAll(c);
    }

    @Override
    synchronized public boolean addAll(Collection<? extends T> c) {
        c.forEach(e -> add(e));
        return true;
    }

    @Override
    synchronized public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("Unimplemented method 'addAll'");
    }

    @Override
    synchronized public boolean removeAll(Collection<?> c) {
        return elements.removeAll(c);
    }

    @Override
    synchronized public boolean retainAll(Collection<?> c) {
        return elements.retainAll(c);
    }

    @Override
    synchronized public void clear() {
        elements.clear();
    }

    @Override
    synchronized public T get(int index) {
        return elements.get(index);
    }

    @Override
    synchronized public T set(int index, T element) {
        return elements.set(index, element);
    }

    @Override
    synchronized public void add(int index, T element) {
        elements.add(index, element);
    }

    @Override
    synchronized public T remove(int index) {
        return elements.remove(index);
    }

    @Override
    synchronized public int indexOf(Object o) {
        return elements.indexOf(o);
    }

    @Override
    synchronized public int lastIndexOf(Object o) {
        return elements.lastIndexOf(o);
    }

    @Override
    synchronized public ListIterator<T> listIterator() {
        return elements.listIterator();
    }

    @Override
    synchronized public ListIterator<T> listIterator(int index) {
        return elements.listIterator(index);
    }

    @Override
    synchronized public List<T> subList(int fromIndex, int toIndex) {
        return elements.subList(fromIndex, toIndex);
    }
    
}
