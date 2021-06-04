package leetcode;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

public class ContinuousSubarraySum {
    public boolean checkSubarraySum(int[] nums, int k) {
        if (nums.length < 2) { // 最少要两个元素的子数组
            return false;
        }
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == 0 && nums[i + 1] == 0 ) {
                return true;
            }
        }
        
//        // 遍历全部子数组，用时 O(m*m)
//        // 左指针
//        for (int low = 0; low < nums.length - 1; low++) {
//            int subArraySum = nums[low];
//            // 右指针
//            for (int high = low + 1; high < nums.length; high++) {
//                subArraySum += nums[high];
//                if (subArraySum % k == 0) {
//                    return true;
//                }
//            }
//            // 没必要再遍历下去了
//            if (subArraySum < k) {
//                break;
//            }
//        }

        // 前缀和余数数组
        Map<Integer, Integer> remainders = new HashMap<Integer, Integer>();
        remainders.put(0, -1);

        int remainder = 0;
        for (int i = 0; i < nums.length; i++) {
            remainder = (remainder + nums[i]) % k;
            if (remainders.containsKey(remainder)) {
                if (i - remainders.get(remainder) >= 2) { // 长度>2
                    return true;
                }
            } else {
                remainders.put(remainder, i);
            }
        }
        
        return false;
    }
    
    public boolean checkSubarraySum2(int[] nums, int k) {
        if (nums.length < 2) { // 最少要两个元素的子数组
            return false;
        }
        // 连续两个为0的元素
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == 0 && nums[i + 1] == 0 ) {
                return true;
            }
        }

        // 前缀和数组。注意会比 nums.length 大一。好处多多，能够不用在处理边界 0 了
        int[] preSums = new int[nums.length + 1];
        preSums[0] = 0; // 对 java 来说，这一句可以不写，初始化的数组
        for (int i = 1; i <= nums.length; i++) {
            preSums[i] = preSums[i - 1] + nums[i - 1];
        }
        // 利用同余，
        Set<Integer> set = new HashSet<Integer>();
        // 因为至少要两个元素，要比较从 1 开始 的数。这里的下标从 2 开始，但其实比较的是 2 之前的所有数的和， 和 0 之前的所有书的和，这个边界处理有意思
        for (int i = 2; i <= nums.length; i++) {
            set.add(preSums[i - 2] % k);
            if (set.contains(preSums[i] % k)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        var target = new ContinuousSubarraySum();
        var nums = "[23,2,4,6,7]";
        var k = 6;
        assertEquals(true, target.checkSubarraySum(new Gson().fromJson(nums, int[].class), k));
    }

}
