package com.jarry.algorithm;

/**
 * 二分查找法
 * 题目：找到数组中(数组有序)大于等于某个数的最左位置
 */
public class Sort03_BinarySearch {
    public static void main(String[] args) {
        int[] arr = {2,2,2,2,2,2,2,2,1};
        System.out.println(leftmostPosition(arr, 1));
    }


    public static int leftmostPosition(int[] arr, int num){
        if(arr == null || arr.length == 0){
            return 0;
        }

        int L = 0;
        int R = arr.length -1;
        int mid = 0;
        while(L < R){
            mid = L + (R -L) >> 1; // 等价于 (L + R)/2

            if(L >= mid){
                if(arr[L] == num) {
                    return L;
                }
                if(arr[R] == num){
                    return R;
                }
                break;
            }

            if(arr[mid] >= num) {
                R = mid;
            }else {
                L = mid;
            }
        }
        return -1 ;

    }
}
