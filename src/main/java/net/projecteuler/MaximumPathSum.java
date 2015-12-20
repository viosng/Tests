package net.projecteuler;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by vio on 25.10.2015.
 */
public class MaximumPathSum {

    private static BigInteger solve(int[][] m) {
        BigInteger result = BigInteger.valueOf(m[0][0]);
        BigInteger[] sum = new BigInteger[m[m.length - 1].length];
        sum[0] = BigInteger.valueOf(m[0][0]);
        BigInteger[] newSum = new BigInteger[m[m.length - 1].length];
        System.out.println(0+ ") sum = " + Arrays.toString(Arrays.copyOf(sum, 1)));
        for (int i = 1; i < m.length; i++) {
            int length = m[i].length;
            newSum[0] = sum[0].add(BigInteger.valueOf(m[i][0]));
            newSum[length - 1] = sum[length - 2].add(BigInteger.valueOf(m[i][length - 1]));
            for (int j = 1; j < length - 1; j++) {
                newSum[j] = sum[j - 1].compareTo(sum[j]) > 0
                            ? sum[j - 1].add(BigInteger.valueOf(m[i][j]))
                            : sum[j].add(BigInteger.valueOf(m[i][j]));

            }
            BigInteger[] tmp = sum;
            sum = newSum;
            newSum = tmp;
            System.out.println(i + ") sum = " + Arrays.toString(Arrays.copyOf(sum, i + 1)));
        }

        return Stream.of(sum).max(BigInteger::compareTo).get();
    }

    public static void main(String[] args) {
        List<int[]> data = new ArrayList<>();
        try(Scanner in = new Scanner(new File("src/main/resources/maxPathSumLarge"))) {
            while (in.hasNextLine()) {
                String[] input = in.nextLine().split(" ");
                data.add(Stream.of(input).mapToInt(Integer::parseInt).toArray());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int[][] dataArray = data.toArray(new int[data.size()][]);
        BigInteger result = solve(dataArray);
        System.out.println("result = " + result);
    }
}
