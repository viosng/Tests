package net.projecteuler;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by vio on 04.10.2015.
 */
public class LargeSum {
    public static void main(String[] args) {
        BigInteger sum = BigInteger.ZERO;
        try(Scanner in = new Scanner(new File("src/main/resources/largeSum.in"))) {
            while (in.hasNextLine()) {
                sum = sum.add(new BigInteger(in.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(sum.toString().substring(0, 10));


    }
}
