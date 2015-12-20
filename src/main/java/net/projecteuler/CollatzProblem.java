package net.projecteuler;

import javafx.util.Pair;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vio on 04.10.2015.
 */
public class CollatzProblem {
    public static void main(String[] args) {
        List<BigInteger> numbers = new ArrayList<>();
        for (int i = 2; i < 1000000; i++) {
            numbers.add(BigInteger.valueOf(i));
        }
        System.out.println(numbers.parallelStream().map(n-> {
            int cur = 0;
            BigInteger j = n;
//            System.out.println("---" + j);
            while (!j.equals(BigInteger.ONE)) {
                cur++;
                if (!j.testBit(0)) {
                    j = j.divide(BigInteger.valueOf(2));
                } else {
                    j = BigInteger.valueOf(3).multiply(j).add(BigInteger.ONE);
                }
            }
//            System.out.println(cur);
            return new Pair<>(n, cur);
        }).max((a, b) -> Integer.compare(a.getValue(), b.getValue())));
    }
}
