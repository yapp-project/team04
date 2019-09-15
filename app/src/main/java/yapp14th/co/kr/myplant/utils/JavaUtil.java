package yapp14th.co.kr.myplant.utils;

public class JavaUtil {
    public static void sort(int[][] data, int l, int r) {
        int left = l;
        int right = r;
        int pivot = data[(l + r) / 2][1];

        do {
            while (data[left][1] > pivot) left++;
            while (data[right][1] < pivot) right--;
            if (left <= right) {
                int[] temp = data[left];
                data[left] = data[right];
                data[right] = temp;
                left++;
                right--;
            }
        } while (left <= right);

        if (l < right) sort(data, l, right);
        if (r > left) sort(data, left, r);
    }
}
