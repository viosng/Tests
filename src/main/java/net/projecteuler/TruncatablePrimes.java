package net.projecteuler;

import java.util.BitSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * Created by viosng on 11.06.2016.
 */
public class TruncatablePrimes {

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

    public static void main(String[] args) {
        int N = 1000000;
        BitSet bitSet = primeCeive(N);
        AtomicInteger counter = new AtomicInteger();
        int sum = IntStream.range(11, N)
                .filter(bitSet::get)
                .filter(p -> {
                    int digitCount = (int) Math.log10(p) + 1;
                    int tmp = p;
                    for (int i = 0; i < digitCount; i++) {
                        if (!bitSet.get(tmp)) return false;
                        tmp /= 10;
                    }
                    tmp = p;
                    for (int i = 0; i < digitCount; i++) {
                        if (!bitSet.get(tmp)) return false;
                        tmp %= ((int) Math.pow(10, digitCount - i - 1));
                    }
                    return true;

                })
                .peek(p -> counter.incrementAndGet())
                .peek(System.out::println)
                .sum();
        System.out.println("sum = " + sum);
        System.out.println("counter = " + counter);
    }
}
