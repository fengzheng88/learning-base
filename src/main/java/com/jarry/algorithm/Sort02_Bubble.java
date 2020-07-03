package com.jarry.algorithm;

/**
 * 冒泡排序
 * O(N^2)
 * 稳定
 */
public class Sort02_Bubble {
    public static void main(String[] args) {
        int[] arr = {4, 1, 5, 7, 3, 9, 2};
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length -1; j++) {
                //第N个与第N+1个比，大就交换
                if(arr[j] > arr[j + 1]){
                    //进行交换
                    swap(arr, j, j + 1);
                }
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
