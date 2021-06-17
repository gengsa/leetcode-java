package leetcode;

import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.kerberos.KerberosKey;

public class TargetSum {

    public int findTargetSumWays(int[] nums, int target) {
//        return recursiveDFSOld(nums, target, 0, 0);
//        return recursiveDFS(nums, target, 0, 0);
//        recursiveDFS2Old(nums, target, 0, 0); return count;
//        recursiveDFS2(nums, target, 0, 0); return count;
        return dp(nums, target); 
    }
    
    
    public int dp(int[] nums, int target) {
        int n = nums.length;
        int maxSum = 0;
        for (int i : nums) {
            maxSum += Math.abs(i);
        }
        if (target > maxSum) {
            return 0;
        }
        // 令 f[i][j] 代表前 i 个数，和为 j 的组合数
        // 第二维，向右偏移 maxSum，能够存储负数和
        int[][] f = new int[n + 1][2 * maxSum + 1];
        f[0][0 + maxSum] = 1;
        
        for (int i = 1; i <= n; i++) {
            int preNum = nums[i - 1];
            for (int j = -maxSum; j <= maxSum; j++) {
                if ((j - preNum) + maxSum >= 0) { // 这两个判断的保护我还没想明白
                    f[i][j + maxSum] += f[i - 1][(j - preNum) + maxSum];
                }
                if ((j + preNum) + maxSum <= 2 * maxSum) {
                    f[i][j + maxSum] += f[i - 1][(j + preNum) + maxSum];
                }
            }
        }
        return f[n][target + maxSum];
    }
    
    int count = 0;
    Map<String, Integer> cache = new HashMap<>();
    
    public void recursiveDFS2(int[] nums, int target, int index, int sum) {
        String key = index + "_" + sum;
        if (cache.containsKey(key)) {
            count += cache.get(key);
            return;
        }
        if (index == nums.length) {
            if (sum == target) {
                count++;
            }
            return;
        }
        int oldCount = count;
        recursiveDFS2(nums, target, index + 1, sum - nums[index]);
        recursiveDFS2(nums, target, index + 1, sum + nums[index]);
        cache.put(key, count - oldCount);
    }
    
    public void recursiveDFS2Old(int[] nums, int target, int index, int sum) {
        if (index == nums.length) {
            if (sum == target) {
                count++;
            }
            return;
        }
        recursiveDFS2Old(nums, target, index + 1, sum - nums[index]);
        recursiveDFS2Old(nums, target, index + 1, sum + nums[index]);
    }
    
    public int recursiveDFS(int[] nums, int target, int index, int sum) {
        String key = index + "_" + sum;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        if (index == nums.length) {
            cache.put(key, sum == target ? 1 : 0);
            return cache.get(key);
        }
        int left = recursiveDFS(nums, target, index + 1, sum - nums[index]);
        int right = recursiveDFS(nums, target, index + 1, sum + nums[index]);
        cache.put(key, left + right);
        return cache.get(key);
    }

    
    public int recursiveDFSOld(int[] nums, int target, int index, int sum) {
        if (index == nums.length) {
            return sum == target ? 1 : 0;
        }
        int left = recursiveDFSOld(nums, target, index + 1, sum - nums[index]);
        int right = recursiveDFSOld(nums, target, index + 1, sum + nums[index]);
        return left + right;
    }
    
    
    public static void main(String[] args) {
        
        int[] arr= {1,2,4,6,5,0};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        
        var target = new TargetSum();
        int[] nums = {1,1,1,1,1,1,1,1,1,1,1,1,1, 1, 1, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,};
        long begin;
        long end;
        
        begin = System.nanoTime();
        System.out.println(target.findTargetSumWays(nums, 3));
        end = System.nanoTime();
        System.out.println((end - begin) / 1000);
    }

}
