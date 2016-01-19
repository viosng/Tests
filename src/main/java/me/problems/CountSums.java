package me.problems;

import java.util.BitSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;

/**
 * Created by viosn_000 on 13.01.2016.
 */
public class CountSums {

    private static AtomicInteger counter = new AtomicInteger();

    private static void solve(BitSet bitSet, int sum, int index, int from, int findSum) {
        if (index >= bitSet.size()) {
            return;
        }
        if (sum == findSum) {
            System.out.println(counter.incrementAndGet() + ") " + bitSet.stream().mapToObj(i -> i + from)
                    .collect(Collectors.toList()));
            return;
        }
        if (sum < findSum) return;

        solve(bitSet, sum, index + 1, from, findSum);
        bitSet.flip(index);
        solve(bitSet, sum - index - from, index + 1, from, findSum);
        bitSet.flip(index);
    }

    private static void solve(int from, int to, int findSum) {
        solve(new BitSet(to - from), range(from, to).sum(), 0, from, findSum);
    }

    public static void main(String[] args) {
        solve(31, 101, 4489);
    }
}
