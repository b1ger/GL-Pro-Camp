package com.company;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Random;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        int[] arr1 = {1, 4, 7, 8, 25};
        int[] arr2 = {3, 4, 9, 10, 21};

        for (int i = 0; i < arr1.length; i++) {
            add(arr1[i], arr2[i]);
        }
    }

    public void add(int a, int b) {
        System.out.println(a + b);
    }
}
