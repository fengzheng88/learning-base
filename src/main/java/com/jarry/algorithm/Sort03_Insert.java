package com.jarry.algorithm;

/**
 * 插入排序
 * O(N^2)
 * 不稳
 */
public class Sort03_Insert {
    public static void main(String[] args) {
        int[] arr = {4, 1, 5, 7, 3, 9, 2};
        for(int i = 0; i< arr.length; i++){
            for(int j = i; j > 0 && arr[j - 1] > arr[j]; j--){
                swap(arr, j, j - 1);
            }
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    public static void swap(int[] arr, int minIndex, int currentIndex) {
        int tmp = arr[minIndex];
        arr[minIndex] = arr[currentIndex];
        arr[currentIndex] = tmp;
    }
}
