package net.projecteuler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by vio on 26.10.2015.
 */
public class AmicableNumbers {
    private static int d(int n) {
        int sqr = (int)Math.sqrt((double)n), sum = 0;
        for (int i = 2; i <= sqr ; i++) {
            int q = n / i;
            int r = n % i;
            if (r == 0) {
                System.out.printf("%d: -> %d %d\n", n, q, i);
                sum += q + (i != q ? i : 0);
            }
        }
        System.out.println("sum = " + sum);
        return sum + 1;
    }
    public static void main(String[] args) {
        Map<Integer, Integer> direct = new HashMap<>();
        int N = 10000;
        for (int i = 2; i < N; i++) {
            int d = d(i);
            if (d != i) {
                direct.put(i, d);
            }
        }
        int sum = 0;
        for (int a = 2; a < N; a++) {
            Integer b = direct.get(a);
            if (b != null && Optional.ofNullable(direct.get(b)).orElse(-1).equals(a)) {
                sum += a + b;
            }
        }
        System.out.println("sum = " + sum / 2);
    }
}
