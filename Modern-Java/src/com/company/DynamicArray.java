package com.company;

import java.util.Iterator;

public class DynamicArray<T> implements List<T> {

    protected Object[] array = new Object[0];
    protected int lastIndex = -1;

    @SuppressWarnings("unchecked")
    @Override
    public T get(int index) {
        ensureIndexInBounds(index);
        return (T)array[index];
    }

    private void ensureIndexInBounds(int index) {
        if (index < 0 || index > lastIndex) {
            throw new IndexOutOfBoundsException("wrong index: " + index);
        }
    }

    @Override
    public void set(int index, T element) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index cannot be " + index);
        }
        ensureFit(index);
        array[index] = element;
        lastIndex = index > lastIndex ? index : lastIndex;
    }

    @Override
    public void add(T element) {
        set(lastIndex + 1, element);
    }

    private void ensureFit(int index) {
        if (index >= array.length) {
            Integer[] newArray = new Integer[index + 1];
            System.arraycopy(array, 0, newArray, 0, (array.length));
            array = newArray;
        }
    }

    @Override
    public int size() {
        return lastIndex++;
    }

    public void remove(int index) {
        ensureIndexInBounds(index);
        array[index] = null;
        for (int i = index; i <= lastIndex - 1; i++) {
            array[i] = array[i + 1];
        }
        array[lastIndex] = null;
        lastIndex--;
    }

    public void trimToSize() {
        Object[] newArray = new Object[size()];
        for (int i = 0; i <= lastIndex; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int pos = -1;

            @Override
            public boolean hasNext() {
                return pos + 1 <= lastIndex;
            }

            @Override
            public void remove() {
                DynamicArray.this.remove(pos);
            }

            @SuppressWarnings("unchecked")
            @Override
            public T next() {
                return (T)array[++pos];
            }
        };
    }

}
