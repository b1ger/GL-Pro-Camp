package com.company;

public interface List<T> extends Iterable<T> {

    T get(int index);

    void set(int index, T element);

    void add(T element);

    int size();

    void remove(int index);

    java.util.List some();

}
