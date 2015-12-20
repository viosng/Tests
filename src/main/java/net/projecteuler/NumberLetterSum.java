package net.projecteuler;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by vio on 10.10.2015.
 */
public class NumberLetterSum {

    private static final Map<Integer, String> digitWord;
    static {
        Builder<Integer, String> builder = ImmutableMap.<Integer, String>builder();
        try(Scanner in = new Scanner(new File("src/main/resources/numberWords"))) {
            while (in.hasNextLine()) {
                String[] input = in.nextLine().split("\t");
                int key = Integer.parseInt(input[0]);
                String value = input[1];
                builder.put(key, value);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        digitWord = builder.build();
    }

    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= 1000; i++) {
            builder.append(digitWord.get(i)) ;
        }
        System.out.println(builder.toString().replaceAll("[ -]", "").length());
    }
}
