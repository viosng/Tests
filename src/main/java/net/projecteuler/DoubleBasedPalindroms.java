package net.projecteuler;

import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * Created by viosng on 11.06.2016.
 */
public class DoubleBasedPalindroms {
    public static void main(String[] args) {
        Predicate<String> isPalindrom = s -> {
            for (int i = 0; i < s.length() / 2; i++) {
                if (s.charAt(i) != s.charAt(s.length() - i - 1)) return false;
            }
            return true;
        };
        long sum = IntStream.range(0, 1000000)
                .filter(n -> isPalindrom.test(Integer.toBinaryString(n)) && isPalindrom.test(Integer.toString(n)))
                .peek(n -> System.out.println(n + " " + Integer.toBinaryString(n)))
                .sum();
        System.out.println("sum = " + sum);
    }
}
