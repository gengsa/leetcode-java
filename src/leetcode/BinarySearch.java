package leetcode;

public class BinarySearch {
    public int search(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;

        while (low <= high) {
            int middle = (low + high) >>> 1;
            if (nums[middle] < target) {
                low = middle + 1;
            } else if (nums[middle] > target) {
                high = middle - 1;
            } else {
                return middle;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        var binarySearch = new BinarySearch();
        System.out.println(binarySearch.search(new int[] {5}, 5));
    }

}
