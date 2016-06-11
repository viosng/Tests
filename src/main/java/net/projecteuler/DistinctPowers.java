package net.projecteuler;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by viosng on 26.03.2016.
 */
public class DistinctPowers {

    @FunctionalInterface
    private interface SolveDistinctPowers {
        int solve(int bound);
    }

    private static SolveDistinctPowers naive = bound -> {
        if (bound <= 2) return 0;
        Set<BigInteger> numbers = new HashSet<>();
        for (int a = 2; a <= bound; a++) {
            for (int b = 2; b <= bound; b++) {
                numbers.add(BigInteger.valueOf(a).pow(b));
            }
        }
        return numbers.size();
    };


    public static void main(String[] args) {
        System.out.println(naive.solve(100));
    }
}
