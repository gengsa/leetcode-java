package leetcode;

import java.util.Arrays;

public class SubarraySumEqualsK {

    public int subarraySum(int[] nums, int k) {
        
        
        int count = 0;
        for (int left = 0; left < nums.length; left++) {
            for (int right = 0; right < nums.length; right++) {
                int sum = 0;
                for (int i = left; i <= right; i++) {
                    sum += nums[i];
                    if (sum == k) {
                        count++;
                    }
                }
            }
        }
        return count;
        
//        int result = 0;
//        
//        // 前缀和数组
//        int[] sums = new int[nums.length];
//        sums[0] = nums[0];
//        for (int i = 1; i < nums.length; i++) {
//            sums[i] = sums[i - 1] + nums[i - 1]; 
//        }
//        int[] preSums = new int[nums.length + 1];
//        for (int i = 1; i <= nums.length; i++) {
//            preSums[i] = preSums[i - 1] + nums[i - 1]; 
//        }
//        
//        System.out.println(Arrays.toString(sums));
//        System.out.println(Arrays.toString(preSums));
//        
//
//        for (int i = 0; i < nums.length - 1; i++) {
//            for (int j = i + 1; j <= nums.length; j++) { // 至少两个数字
//                System.out.println("本次对比 " + i + " " + j);
//                if (preSums[j] - preSums[i] < k) {
//                    continue;
//                } else if (preSums[j] - preSums[i] > k) {
//                    break;
//                } else {
//                    result++;
//                    System.out.println("可以");
//                    break;
//                }
//            }
//        }
//        System.out.println(result);
//        return result;

    }
    
    public static void main(String[] args) {
        var target = new SubarraySumEqualsK();
        target.subarraySum(new int [] {1, 1, 1}, 2);

    }

}
