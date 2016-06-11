package net.projecteuler;

import me.concurrent.util.Pair;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by viosng on 11.06.2016.
 */
public class IntegerRightTriangles {
    public static void main(String[] args) {
        int p = 1000;
        Pair<Integer, Set<List<Integer>>> integerSetPair = IntStream.range(1, p + 1)
                .mapToObj(n -> {
                    Set<List<Integer>> sides = new HashSet<>();
                    IntStream.range(1, n).mapToObj(i -> i)
                            .flatMap(i -> IntStream.range(1, n - i).mapToObj(j -> j).map(j -> new Pair<>(i, new Pair<>(j, n - i - j))))
                            .filter(t -> {
                                int s1 = t.getKey();
                                int s2 = t.getValue().getKey();
                                int s3 = t.getValue().getValue();
                                return s1 <= s2 + s3
                                        && s2 <= s1 + s3
                                        && s3 <= s1 + s2
                                        && (s1 * s1 == s2 * s2 + s3 * s3
                                            || s2 * s2 == s1 * s1 + s3 * s3
                                        || s3 * s3 == s1 * s1 + s2 * s2);
                            })
                            .forEach(t -> {
                                int s1 = t.getKey();
                                int s2 = t.getValue().getKey();
                                int s3 = t.getValue().getValue();
                                sides.add(Arrays.asList(s1, s2, s3).stream().sorted().collect(Collectors.toList()));
                            });
                    return new Pair<>(n, sides);
                })
                .filter(pair -> !pair.getValue().isEmpty())
                .peek(System.out::println)
                .max((p1, p2) -> Integer.compare(p1.getValue().size(), p2.getValue().size()))
                .get();
        System.out.println("max = " + integerSetPair);
        System.out.println("max = " + integerSetPair.getValue().size());
    }
}
