package net.projecteuler;

import java.util.Arrays;

/**
 * Created by viosng on 27.02.2016.
 */
public class LexicographicalPermutations {

    public static void main(String[] args) {
        int[] m = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}; //, 4, 5, 6, 7, 8, 9};
        int length = m.length;
        int count = 1;
        System.out.println(count++ + ") " + Arrays.toString(m));
        while (true) {
            int cur = length - 2;
            while(cur >= 0 && m[cur] > m[cur + 1]) cur--;
            if (cur < 0) break;
            int min = cur + 1;
            for (int i = cur + 2; i < length; i++) {
                if (m[cur] < m[i] && m[i] < m[min]) {
                    min = i;
                }
            }
            if (m[min] <= m[cur]) {
                 break;
            }
            int t = m[min];
            m[min] = m[cur];
            m[cur] = t;
            Arrays.sort(m, cur + 1, length);
            if (count++ == 1_000_000) {
                System.out.println(count + ") " + Arrays.toString(m));
                break;
            }
        }
    }
}
