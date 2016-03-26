package net.projecteuler;

import com.google.common.base.Stopwatch;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by viosng on 26.03.2016.
 */
public class QuadraticPrimes {
    
    private static Set<Integer> findPrimes(int bound) {
        BitSet bitSet = new BitSet(bound);
        bitSet.set(0, 2);
        int sqrt = (int) Math.sqrt(bound);
        for (int i = 2; i <= sqrt; i++) {
            if (!bitSet.get(i)) {
                for (int k = 2; i * k < bound; k++) {
                    bitSet.set(i * k);
                }
            }
        }
        bitSet.flip(0, bitSet.size());
        Set<Integer> primes = new HashSet<>();
        for (int i = 0; i < bound; i++) {
            if (bitSet.get(i)) {
                primes.add(i);
            }
        }
        return primes;
    }

    public static void main(String[] args) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Set<Integer> primes = findPrimes(5_000_000);
        System.out.println(stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
        int maxA = -1000, maxB = -1000, maxN = 0;

        for (int a = -999; a < 1000; a++) {
            for (int b = -999; b < 1000; b++) {
                int n = 0;
                int bitIndex;
                while ((bitIndex = n * n + a * n + b) >= 0 && primes.contains(bitIndex)) {
                    n++;
                }
                if (maxN < n - 1) {
                    maxN = n - 1;
                    maxA = a;
                    maxB = b;
                }
            }
        }
        System.out.println("maxA = " + maxA);
        System.out.println("maxB = " + maxB);
        System.out.println("maxN = " + maxN);
    }
}
