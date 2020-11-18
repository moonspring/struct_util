package com.lzq.struct.util.collection;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtilTest {


    public static void main(String[] args) {

        List<Integer> numList = new ArrayList<Integer>();
        numList.add(12);
        numList.add(33);
        numList.add(11);
        numList.add(55);
        numList.add(66);

        int maxNum = Collections.max(numList);
        int minNum = Collections.min(numList);

        System.out.println(maxNum);
        System.out.println(minNum);
    }

}
