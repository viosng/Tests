package net.projecteuler;

import com.google.common.base.Stopwatch;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.IntStream.range;

/**
 * Created by viosn_000 on 06.02.2016.
 */
public class PerfectNumbers {

    private static int divisorSum(int x) {
        return range(2, (int) Math.sqrt(x) + 1)
                .filter(i -> x % i == 0)
                .flatMap(i -> IntStream.of(i, x / i))
                .distinct()
                .sum() + 1;
    }

    public static void main(String[] args) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Set<Integer> collect = range(12, 28123).parallel()
                .filter(n -> n < divisorSum(n))
                .mapToObj(i -> i)
                .collect(Collectors.toSet());
        Set<Integer> pairSum = collect.parallelStream()
                .flatMap(n -> collect.stream().map(n1 -> n + n1))
                .collect(Collectors.toSet());
        System.out.println(range(1, 28123).filter(i -> !pairSum.contains(i)).sum());
        System.out.println(stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
    }
}
