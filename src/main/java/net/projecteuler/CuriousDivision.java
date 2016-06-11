package net.projecteuler;

import java.util.function.BiFunction;
import java.util.stream.IntStream;

/**
 * Created by viosng on 17.04.2016.
 */
public class CuriousDivision {
    public static void main(String[] args) {
        BiFunction<Integer, Integer, String> str = (i, j) -> i + " / " + j;
        IntStream.range(10, 100)
                .forEach(j -> IntStream.range(10, j)
                        .forEach(i -> {
                            double original = i / 1.0 / j;
                            double first = (i % 10) * 1.0 / (j / 10);
                            double second = (i / 10) * 1.0 / (j % 10);
                            if (Double.compare(first, second) == 0) return;
                            if (Double.compare(original, first) == 0) System.out.println(str.apply(i % 10, j / 10) + " : " + str.apply(i, j));
                            if (Double.compare(original, second) == 0) System.out.println(str.apply(i / 10, j % 10) + " : " + str.apply(i, j));
                        }));
    }
}
