package net.projecteuler;

import java.math.BigInteger;

/**
 * Created by vio on 10.10.2015.
 */
public class PowerDigitSum {
    public static void main(String[] args) {
        BigInteger pow = BigInteger.valueOf(2).pow(1000);
        System.out.println(pow);
        System.out.println(pow.toString().chars().map(c -> c - '0').sum());
    }
}
