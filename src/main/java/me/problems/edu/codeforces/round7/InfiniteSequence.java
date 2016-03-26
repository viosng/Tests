package me.problems.edu.codeforces.round7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by viosng on 07.03.2016.
 */
public class InfiniteSequence {

    private static long blockNumber(long n) {
        long sum = 0;
        int block = 0;
        while (sum < n) {
            sum += ++block;
        }
        return block - sum + n;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        long n = Long.parseLong(in.readLine());
        in.close();

        PrintWriter out = new PrintWriter(System.out);
        out.print(blockNumber(n));
        out.close();
    }
}
