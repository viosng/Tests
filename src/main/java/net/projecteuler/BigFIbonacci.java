package net.projecteuler;

import java.math.BigInteger;

/**
 * Created by viosng on 27.02.2016.
 */
public class BigFIbonacci {
    public static void main(String[] args) {
        BigInteger first = BigInteger.ONE, second = BigInteger.ONE, tmp;
        BigInteger finish = BigInteger.valueOf(10).pow(999);
        int count = 2;
        while (second.compareTo(finish) < 0) {
            tmp = second;
            second = second.add(first);
            first = tmp;
            count++;
            //System.out.println(count + ") second = " + second);
        }
        System.out.println(count + ") second = " + second);
    }
}
