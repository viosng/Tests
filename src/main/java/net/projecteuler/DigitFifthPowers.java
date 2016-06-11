package net.projecteuler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by viosng on 27.03.2016.
 */
public class DigitFifthPowers {
    public static void main(String[] args) {
        long sum = 0;
        for (int i = 10; i < 300000; i++) {
            List<Integer> digits = new ArrayList<>();
            int cur = i;
            while(cur > 0) {
                digits.add(cur % 10);
                cur /= 10;
            }
            int sum1 = digits.stream().mapToInt(d -> d).map(d -> d * d * d * d * d).sum();
            if (sum1 == i) {
                System.out.println(i);
                sum += sum1;
            }
        }
        System.out.println("sum = " + sum);
    }
}
