package com.lzq.struct.util.diff.mydiff;

import com.github.difflib.algorithm.DifferentiationFailedException;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author adam
 */
public class DiffMain {

    public static void main(String[] args) throws DifferentiationFailedException {

        String srcText = "aa00000baa123456";
//        String srcText = "ABCABBA";
        String targetText = "aaa123456";

        List<String> src = srcText.chars().mapToObj(item->String.valueOf((char)item)).collect(Collectors.toList());
        List<String> target = targetText.chars().mapToObj(item->String.valueOf((char)item)).collect(Collectors.toList());


        MyersDiff<String> myersDiff = new MyersDiff<>();
        PathNode pathNode = myersDiff.buildPath(src,target);

        System.out.println(pathNode);

        List<String> result = myersDiff.buildRevision(pathNode,src,target);
        for (String line:result) {
            System.out.println(line);
        }
    }


}
