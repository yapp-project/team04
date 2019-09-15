package yapp14th.co.kr.myplant;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void collection() {
        int[][] array = new int[][]{{0, 2}, {1, 3}, {2, 5}, {3, 7}, {4, 0}, {5, 2}, {6, 3}, {7, 1}, {8, 10}};

        sort(array, 0, array.length - 1);

        System.out.print(array);

        assertEquals(4, 2 + 2);
    }

    public void sort(int[][] data, int l, int r) {
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