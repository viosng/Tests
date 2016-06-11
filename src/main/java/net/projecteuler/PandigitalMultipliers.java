package net.projecteuler;

import me.concurrent.util.Pair;

import java.util.stream.IntStream;

/**
 * Created by viosng on 11.06.2016.
 */
public class PandigitalMultipliers {
    public static void main(String[] args) {
/*        int max = IntStream.range(9000, 10000)
                .filter(n -> {
                    String s = Integer.toString(n) + Integer.toString(n * 2);
                    return s.length() == 9 && s.chars().distinct().count() == 9;
                })
                .peek(n -> System.out.println(Integer.toString(n) + Integer.toString(n * 2)))
                .max().getAsInt();
        System.out.println("max = " + max);*/

        Pair<Integer, String> pair = IntStream.range(1, 10000)
                .mapToObj(n -> n)
                .flatMap(n -> IntStream.range(1, 10).mapToObj(i -> {
                    String s = "";
                    for (int j = 1; j <= i; j++) {
                        s += Integer.toString(n * j);
                    }
                    return new Pair<>(n, s);
                }))
                .filter(p -> p.getValue().length() == 9 && p.getValue().chars().filter(c -> c != '0').distinct().count() == 9)
                .peek(System.out::println)
                .max((p1, p2) -> Integer.compare(p1.getKey(), p2.getKey())).get();
        System.out.println("pair = " + pair);
    }
}
