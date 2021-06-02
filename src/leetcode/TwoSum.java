package leetcode;

import static org.junit.Assert.assertArrayEquals;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        
        Map<Integer, Integer> deltaToIndex = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int delta = target - nums[i];
            if (deltaToIndex.containsKey(delta)) {
                return new int [] {deltaToIndex.get(delta), i};
            }
            deltaToIndex.put(nums[i], i);
        }
        
//        Map<Integer, Integer> hashtable = new HashMap<Integer, Integer>();
//        for (int i = 0; i < nums.length; ++i) {
//            if (hashtable.containsKey(target - nums[i])) {
//                return new int[]{hashtable.get(target - nums[i]), i};
//            }
//            hashtable.put(nums[i], i);
//        }

        return null;
    }

    public static void main(String[] args) {
        
        var target = new TwoSum();        
        assertArrayEquals(new int [] {0, 1}, target.twoSum(new int [] {2, 7, 11, 15}, 9));
    }

}
