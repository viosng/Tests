package net.projecteuler;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by vio on 25.10.2015.
 */
public class FactorialDigitSum {
    public static void main(String[] args) {
        BigInteger f = BigInteger.ONE;
        for (int i = 2; i < 101; i++) {
            f = f.multiply(BigInteger.valueOf(i));
        }
        String s = f.toString();
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            sum += s.charAt(i) - '0';
        }
        System.out.println("sum = " + sum);
    }
}
