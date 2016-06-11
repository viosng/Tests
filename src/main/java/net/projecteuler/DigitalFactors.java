package net.projecteuler;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

/**
 * Created by viosng on 05.06.2016.
 */
public class DigitalFactors {

    public static void main(String[] args) {
        int f[] = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880};
        IntPredicate digitFactor = n -> {
            int sum = 0, nn = n;
            while (nn > 0 && sum < n) {
                sum += f[nn % 10];
                nn /= 10;
            }
            return nn == 0 && sum == n;
        };
        int sum = IntStream.range(10, 10000000).filter(digitFactor).peek(System.out::println).sum();
        System.out.println("sum = " + sum);
    }
}
