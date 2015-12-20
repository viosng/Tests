package net.projecteuler;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by vio on 21.07.2015.
 */
public class HighlyDivisibleTriangularNumber {

    private static final Function<BigInteger, BigInteger> sumOf = n -> n.multiply(n.add(BigInteger.ONE)).divide(BigInteger.valueOf(2));

    private static Map<BigInteger, BigInteger> getDivisors(@NotNull BigInteger n) {
        Map<BigInteger, BigInteger> divisors = new HashMap<>();
        BigInteger currentDivisor = BigInteger.ZERO;
        BigInteger rest = n;
        do {
            currentDivisor = currentDivisor.add(BigInteger.ONE);
            BigInteger[] divideAndRemainder = n.divideAndRemainder(currentDivisor);
            if (divideAndRemainder[1].equals(BigInteger.ZERO)) {
                divisors.put(currentDivisor, currentDivisor);
                divisors.put(rest = divideAndRemainder[0], divideAndRemainder[0]);
            }
        } while (currentDivisor.compareTo(rest) < 0);
        return divisors;
    }

    public static void main(String... args) {
        // n * (n + 1) / 2;
        Map<BigInteger, BigInteger> divisors = new HashMap<>();
        int divisorsCount = 500;
        BigInteger current = BigInteger.ZERO;
        BigInteger two = BigInteger.valueOf(2);
        do {
            divisors.clear();
            current = current.add(BigInteger.ONE);
            BigInteger first, second;
            BigInteger[] bigIntegers = current.divideAndRemainder(two);
            if (bigIntegers[1].equals(BigInteger.ZERO)) {
                first = bigIntegers[0];
                second = current.add(BigInteger.ONE);
            } else {
                first = current;
                second = bigIntegers[0].add(BigInteger.ONE);
            }
            Map<BigInteger, BigInteger> divisors1 = getDivisors(first);
            Map<BigInteger, BigInteger> divisors2 = getDivisors(second);
            for (BigInteger val1 : divisors1.values()) {
                for (BigInteger val2 : divisors2.values()) {
                    BigInteger multiplied = val1.multiply(val2);
                    divisors.put(multiplied, multiplied);
                }
            }
        } while (divisors.size() < divisorsCount);
        System.out.println(sumOf.apply(current));
    }
}
