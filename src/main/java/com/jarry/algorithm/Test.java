package com.jarry.algorithm;

public class Test {
    public static void main(String[] args) {
        int[] a = {1,3,4,2,2};
        int m = m(a);
        System.out.println(m);
    }

    public static int m(int[] nums){
        int slow = 0, fast = 0;
        do {
            slow = nums[slow];// 0 - 5，
            fast = nums[nums[fast]]; //0 - 4，
            System.out.println(slow);
            System.out.println(fast);
            System.out.println();
        } while (slow != fast);

        slow = 0;
        while (slow != fast) {
            slow = nums[slow];//5
            fast = nums[fast];//5
        }
        return slow = nums[slow];
    }
}
