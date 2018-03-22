package com.chelsea.spring5;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppTest {

    public static void main(String[] args) {
        List<List<Integer>> outer = new ArrayList<>();
        List<Integer> inner1 = new ArrayList<>();
        inner1.add(1);
        List<Integer> inner2 = new ArrayList<>();
        inner2.add(2);
        List<Integer> inner3 = new ArrayList<>();
        inner3.add(3);
        List<Integer> inner4 = new ArrayList<>();
        inner4.add(4);
        List<Integer> inner5 = new ArrayList<>();
        inner5.add(5);
        outer.add(inner1);
        outer.add(inner2);
        outer.add(inner3);
        outer.add(inner4);
        outer.add(inner5);
        List<Object> result =
                outer.stream().flatMap(inner -> inner.stream().map(i -> i + 1)).collect(Collectors.toList());
        System.out.println(result);
    }

}
