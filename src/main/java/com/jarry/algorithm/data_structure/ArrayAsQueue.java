package com.jarry.algorithm.data_structure;

import java.util.Queue;

/**
 * 用数组实现队列
 * 先进先出
 */
public class ArrayAsQueue {
    //定义固定大小的数组
    static int[] arr = new int[2];
    static int size = 0;
    static int popIndex = 0;
    static int pushIndex = 0;

    public static void main(String[] args) {
        add(1);
        add(2);
        System.out.println(remove());
        add(3);
        System.out.println(remove());
        for(int a: arr){
            //System.out.println(a);
        }
    }

    public static int remove(){
        if(size == 0){
            throw new RuntimeException("没有元素了");
        }
        int i = arr[popIndex];
        modifyIndex(++popIndex, false);
        size--;
        return i;
    }

    public static void add(int num){
        if(size == arr.length){
            throw new RuntimeException("没有位置了");
        }
        size++;
        arr[pushIndex] = num;
        modifyIndex(++pushIndex, true);
    }

    private static void modifyIndex(int i, boolean flag) {
        int index = i >= arr.length ? 0: i;
        if(flag){
            pushIndex = index;
        }else {
            popIndex = index;
        }
    }


}
