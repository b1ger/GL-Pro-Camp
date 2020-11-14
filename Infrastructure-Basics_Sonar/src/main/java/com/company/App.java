package com.company;

import java.math.BigDecimal;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        List<BigDecimal> list = new DynamicArray<>();

        list.add(BigDecimal.valueOf(3));
        list.add(BigDecimal.valueOf(5));
        list.add(BigDecimal.valueOf(7));

        System.out.println(list);
    }
}
