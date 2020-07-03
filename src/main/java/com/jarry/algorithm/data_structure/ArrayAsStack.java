package com.jarry.algorithm.data_structure;

/**
 * 用数组实现栈
 */
public class ArrayAsStack {
    //定义固定大小的数组
    static int[] arr = new int[2];
    static int size = 0;
    public static void main(String[] args) {
        push(1);
        push(2);
        int pop = pop();
        push(3);
        push(4);
        push(5);

        for(int a: arr){
            System.out.println(a);
        }
    }

    public static int pop(){
        if(size == 0){
            throw new RuntimeException("没有元素可抛出");
        }
        return arr[--size];
    }

    public static void push(int num){
        if(size == arr.length){
            throw new RuntimeException("没有位置可放入元素");
        }
        arr[size] = num;
        size++;
    }


}
