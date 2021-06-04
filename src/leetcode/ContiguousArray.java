package leetcode;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

class ContiguousArray {

    // 暴力，O(m*m)，会超时
    public int findMaxLength2(int[] nums) {
        int maxLength = 0;
        for (int right = 0; right < nums.length; right++) {
            int[] count = { 0, 0 };
            for (int left = right; left >= 0; left--) {
                count[nums[left]]++;
                if (count[0] == count[1] && count[0] * 2 > maxLength) {
                    maxLength = count[0] * 2;
                }
            }

        }
        return maxLength;
    }

    // hashmap + 前缀和（sums）
    public int findMaxLength(int[] nums) {
        int maxLength = 0;

        Map<Integer, Integer> sum2FirstIndex = new HashMap<Integer, Integer>();
        int sum = 0;
        sum2FirstIndex.put(0, -1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i] == 1 ? 1 : -1; // 将判断 0 和 1 的个数，把 0 当做 -1，转化为和
            if (sum2FirstIndex.containsKey(sum)) { // 再次出现和相同，说明这两个 index 之间的数，0 和 1 个数相等
                int firstIndex = sum2FirstIndex.get(sum);
                maxLength = Math.max(maxLength, i - firstIndex);
            } else {
                sum2FirstIndex.put(sum, i);
            }
        }
        return maxLength;
    }

    // hashmap + 前缀和（preSums）
    public int findMaxLength3(int[] nums) {
        int maxLength = 0;

        int[] preSums = new int[nums.length + 1];
        for (int i = 1; i <= nums.length; i++) {
            preSums[i] = preSums[i - 1] + (nums[i - 1] == 1 ? 1 : -1);
        }

        Map<Integer, Integer> preSum2FirstIndex = new HashMap<Integer, Integer>();
        preSum2FirstIndex.put(0, 0);

        for (int i = 1; i <= nums.length; i++) { // 用preSums，for 的 其实和终止条件是不一样的
            if (!preSum2FirstIndex.containsKey(preSums[i - 1])) {
                preSum2FirstIndex.put(preSums[i - 1], i - 1); // 这里也不一样
            }
            if (preSum2FirstIndex.containsKey(preSums[i])) {
                maxLength = Math.max(maxLength, i - preSum2FirstIndex.get(preSums[i]));
            }
        }
        return maxLength;
    }

    // hashmap + 使用到了前缀和（preSum，但没用preSums数组）（但是将hashMap 给换成数组的实现了）
    public int findMaxLength4(int[] nums) {
        int len = nums.length;
        int maxLength = 0;
        
        // 用这个数组替换了 hashmap。用index 代表 preSum，value 代表 index
        // 最多 m 个 1，最多 m 个 0（被我当做 -1），所以新数组长度用 2 * m + 1
        int[] preSum2FirstIndex = new int[2 * len + 1];
        Arrays.fill(preSum2FirstIndex, -1);
        preSum2FirstIndex[0 + len] = 0; // 和 preSum == 0 则代表从 0 到 i - 1 是最大子串是等价的

        int preSum = 0;
        for (int i = 1; i <= len; i++) {
            preSum += (nums[i - 1] == 1 ? 1 : -1);
            if (preSum2FirstIndex[preSum + len] != -1) { // 代表这个 preSum 曾经出现过
                int firstIndex = preSum2FirstIndex[preSum + len];
                maxLength = Math.max(maxLength, i - firstIndex); // 本次又出现，则 index 之差，就是长度
            }
            if (preSum2FirstIndex[preSum + len] == -1) {
                preSum2FirstIndex[preSum + len] = i;
            }
        }
        return maxLength;
    }
    
    // hashmap + 使用到了前缀和（preSums数组）（但是将hashMap 给换成数组的实现了）
    public int findMaxLength5(int[] nums) {
        int len = nums.length;
        int maxLength = 0;
        
        int[] preSums = new int[len + 1];
        preSums[0] = 0;
        for (int i = 1; i <= len; i++) {
            preSums[i] = preSums[i - 1] + (nums[i - 1] == 1 ? 1 : -1);
        }
        
        // 用这个数组替换了 hashmap。用index 代表 preSum，value 代表 index
        // 最多 m 个 1，最多 m 个 0（被我当做 -1），所以新数组长度用 2 * m + 1
        int[] preSum2FirstIndex = new int[2 * len + 1];
        Arrays.fill(preSum2FirstIndex, -1);
//        preSum2FirstIndex[0 + len] = 0; // 和 preSum == 0 则代表从 0 到 i - 1 是最大子串是等价的

        for (int i = 1; i <= len; i++) {
            if (preSum2FirstIndex[preSums[i - 1] + len] == -1) {
                preSum2FirstIndex[preSums[i - 1] + len] = i - 1;
            }
            if (preSum2FirstIndex[preSums[i] + len] != -1) { // 代表这个 preSum 曾经出现过
                int firstIndex = preSum2FirstIndex[preSums[i] + len];
                maxLength = Math.max(maxLength, i - firstIndex); // 本次又出现，则 index 之差，就是长度
            }
        }
        return maxLength;
    }

 // hashmap + 使用到了前缀和（sums数组）
    public int findMaxLength6(int[] nums) {
        int maxLength = 0;
     
        Map<Integer, Integer> sum2Index = new HashMap<>();
        sum2Index.put(0, -1);
     
        int sum = 0;
        for (int right = 0; right < nums.length; right++) {
            sum += (nums[right] == 1 ? 1 : -1);
            if (sum2Index.containsKey(sum)) {
                maxLength = Math.max(maxLength, right - sum2Index.get(sum));
            } else {
                sum2Index.put(sum, right);
            }
        }
        return maxLength;
    }
    
    // prefix sum + hasmap(array 实现)
    public int findMaxLength7(int[] nums) {
        int maxLength = 0;
     
        int[] sum2Index = new int[nums.length * 2 + 1];
        Arrays.fill(sum2Index, -1);
        // sum2Index[0 + nums.length] = 0;

     
        int sum = 0;
        for (int right = 0; right < nums.length; right++) {
            sum += (nums[right] == 1 ? 1 : -1);
            if (sum == 0) {
                maxLength = right + 1; // 或者在上面初始化 sum2Index[0 + nums.length] = 0;
            } else if (sum2Index[sum + nums.length] != -1) {
                maxLength = Math.max(maxLength, right + 1 - sum2Index[sum + nums.length]);
            } else {
                sum2Index[sum + nums.length] = right + 1;
            }
        }
        return maxLength;
    }

    public static void main(String[] args) {
        var target = new ContiguousArray();

//        var nums = "[0,1,0,0,0,1,1,1,1]";
//        var maxLength = 8;

        var nums = "[0,1,0,1]";
        var maxLength = 4;

//        var nums = "[1,1,0,0,0,1,1,0,1,1,1,1,0,0,0,1,0,0,0,0,1,1,0,0,1,1,1,1,1,1,1,0,0,1,1,0,0,1,1,1,0,0,0,0,0,0,0,0,0,1,0,1,1,0,0,1,0,1,1,1,0,0,0,1,0,0,1,1,1,1,0,1,0,0,1,1,0,0,1,0,0,0,0,0,0,0,1,1,0,0,1,0,0,1,1,1,0,0,0,0]";
//        var maxLength = 76;

//        var nums = "[0,0,1,0,0,0,1,1]";
//        var maxLength = 6;

        assertEquals(maxLength, target.findMaxLength7(new Gson().fromJson(nums, int[].class)));
    }
}