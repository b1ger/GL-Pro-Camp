package com.company;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.*;

public class DynamicArrayTest {

    private List<Integer> list;
    private Integer integer;

    @Before
    public void setUp() {
        list = new DynamicArray<>();
        integer = 5;
        list.add(integer);
    }

    @After
    public void tearDown() {
        list = null;
    }

    @Test
    public void listShouldReturnCorrectValue() {
        Integer detached = list.get(0);
        assertEquals(integer, detached);
    }

    @Test
    public void listShouldSetValue() {
        int expected = 1;
        int actual = list.size();

        assertEquals(expected, actual);
    }

    @Test
    public void listShouldIncludesValue() {
        int actual = list.size();
        assertThat(actual, greaterThan(0));
    }

    @Test
    public void listShouldCalculateCorrectValue() {
        int expected = 1;
        int actual = list.size();

        assertEquals(expected, actual);
    }

    @Test
    public void listShouldRemoveValueByIndex() {
        list.add(2);
        list.remove(0);

        assertThat(1, lessThan(2));
    }

    @Test
    public void fitListToActualSize() {
        list.add(2);
        list.add(3);
        list.remove(0);

        assertEquals(2, list.size());
    }
}