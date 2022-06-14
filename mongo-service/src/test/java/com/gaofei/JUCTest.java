package com.gaofei;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author : gaofee
 * @date : 15:21 2022/6/13
 * @码云地址 : https://feege.gitee.io
 */
public class JUCTest {
    public static void main(String[] args) {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        copyOnWriteArrayList.add("aa");
        Object o = copyOnWriteArrayList.get(0);
        System.out.println(o);

    }
}
