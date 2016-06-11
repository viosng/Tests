package me.arithm;

import me.concurrent.util.Pair;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by viosng on 07.06.2016.
 */
public class Normalization {

    @Test
    public void testNorm() throws Exception {
        double[] doubles = IntStream.range(0, 10).mapToDouble(i -> ThreadLocalRandom.current().nextInt(100) - 50).toArray();
        double mean = Arrays.stream(doubles).average().orElse(0.0);
        double std = Math.sqrt(Arrays.stream(doubles).map(x -> x * x).average().orElse(0.0) - mean * mean);
        double[] norm1 = Arrays.stream(doubles).map(x -> (x - mean) / std).toArray();
        double min = Arrays.stream(norm1).min().orElse(0.0);
        double max = Arrays.stream(norm1).max().orElse(0.0);
        double[] norm2 = Arrays.stream(norm1).map(x -> (x - min) / (max - min)).toArray();
        System.out.println(Arrays.toString(doubles));
        System.out.println(Arrays.toString(norm2));
        List<Pair<Double, Double>> collect = Arrays.stream(norm2)
                .mapToObj(x -> new Pair<>(x, Arrays.stream(norm2)
                        .filter(y -> Double.compare(x, y) != 0)
                        .mapToObj(y -> new Pair<>(y, Math.abs(x - y)))
                        .min((p1, p2) -> Double.compare(p1.getValue(), p2.getValue()))
                        .get().getKey()))
                .collect(Collectors.toList());
        System.out.println(collect);
    }
}
