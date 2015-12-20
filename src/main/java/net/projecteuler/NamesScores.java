package net.projecteuler;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by vio on 31.10.2015.
 */
public class NamesScores {
    public static void main(String[] args) throws IOException {
        String[] strings = Files.readLines(new File("src/main/resources/names.txt"), Charset.defaultCharset())
                .stream()
                .reduce(String::concat)
                .get()
                .split(",");
        Arrays.sort(strings);
        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < strings.length; i++) {
            String s = strings[i].replaceAll("\"", "");
            System.out.println(s);
            if (s.equals("COLIN")){
                System.out.println(1);
            }
            sum = getBigInteger(sum, i, s);
        }
        System.out.println("sum = " + sum);
        System.out.println(getBigInteger(BigInteger.ZERO, 937, "COLIN"));
    }

    private static BigInteger getBigInteger(BigInteger sum, int i, String s) {
        int csum = 0;
        for (int j = 0; j < s.length(); j++) {
            csum += s.charAt(j) - 'A' + 1;
        }
        sum = sum.add(BigInteger.valueOf(i + 1).multiply(BigInteger.valueOf(csum)));
        return sum;
    }
}
