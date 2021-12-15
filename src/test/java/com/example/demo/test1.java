package com.example.demo;

import java.util.Arrays;

/**
 * @Auther: hzy
 * @Date: 2021/10/22 12:34
 * @Description:
 */
public class test1 {
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int nums1_length = nums1.length;
        int nums2_length = nums2.length;
        int len = nums1_length + nums2_length;
        int index = -1;
        int[] nums = new int[len];
        for (int i = 0; i < nums.length; i++) {
            if (i < nums1_length)
                nums[i] = nums1[i];
            else
                nums[i] = nums2[i - nums1_length];
        }
        Arrays.sort(nums);
        if (len % 2 != 0) {
            index = len / 2;
            return nums[index];
        }
        index = len / 2;
        return (nums[index - 1] + nums[index]) / 2;
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 3};
        int[] nums2 = {2};
        double medianSortedArrays = findMedianSortedArrays(nums1, nums2);
        System.out.println("medianSortedArrays = " + medianSortedArrays);
    }
}
