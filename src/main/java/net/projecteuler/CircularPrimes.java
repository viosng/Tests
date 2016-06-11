package net.projecteuler;

import java.util.*;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

/**
 * Created by viosng on 05.06.2016.
 */
public class CircularPrimes {

    private static BitSet primeCeive(int N) {
        BitSet bitSet = new BitSet(N);
        bitSet.set(0);
        bitSet.set(1);
        int sqrt = (int) Math.sqrt(N);
        for (int i = 2; i <= sqrt; i++) {
            if (bitSet.get(i)) continue;
            for (int j = 2 * i; j <= N; j += i) {
                bitSet.set(j);
            }
        }
        bitSet.flip(0, N);
        return bitSet;
    }

    private static int convertToInt(List<Integer> digits) {
        int number = 0, k = 1;
        for (int i = digits.size() - 1; i >= 0; i--) {
            number += digits.get(i) * k;
            k *= 10;
        }
        return number;
    }

    public static void main(String[] args) {
        int N = 1000000;
        BitSet primes = primeCeive(N);
        IntPredicate isCircular = n -> {
            int digitCount = (int) Math.log10(n) + 1;
            int tmp = n;
            for (int i = 0; i < digitCount; i++) {
                if (!primes.get(tmp)) {
                    return false;
                }
                tmp = tmp / 10 + (int) Math.pow(10, digitCount - 1) * (tmp % 10);
            }
            return true;
        };
        System.out.println("count = " +
                IntStream.range(0, N).filter(primes::get).filter(isCircular).peek(System.out::println).count());
    }
}
