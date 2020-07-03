package com.jarry.algorithm;

/**
 * 选择排序
 * O(N^2)
 * 稳定
 */
public class Sort01_Selection {
    public static void main(String[] args) {
        int[] arr = {4,1,5,7,3,9,2};
        for(int i =0 ; i < arr.length -1; i++ ){
            //记录最小数所在的位置
            int minIndex = i;
            for(int j = i + 1; j < arr.length; j++){
                //如果此刻最小数大于当前数，则记录当前数所在位置为最小数所在位置
                if(arr[minIndex] > arr[j]){
                    minIndex = j;
                }
            }
            //进行交换
            swap(arr, minIndex, i);
        }

        for(int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }

    public static void swap(int[] arr, int minIndex, int currentIndex){
        int tmp = arr[minIndex];
        arr[minIndex] = arr[currentIndex];
        arr[currentIndex] = tmp;
    }
}
