package me.problems.edu.codeforces.round7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by viosng on 03.03.2016.
 */
public class SubArray {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String[] split = in.readLine().split(" ");
        int n = Integer.parseInt(split[0]), m = Integer.parseInt(split[1]);
        split = in.readLine().split(" ");
        int length = split.length;
        int [][]a = new int[n][2];
        a[0][0] = Integer.parseInt(split[0]);
        a[0][1] = -1;
        for (int i = 1; i < length; i++) {
            int number = Integer.parseInt(split[i]);
            a[i][0] = number;
            a[i][1] = number == a[i - 1][0] ? a[i - 1][1] : i - 1;
        }
        PrintWriter out = new PrintWriter(System.out);
        for (int i = 0; i < m; i++) {
            split = in.readLine().split(" ");
            int l = Integer.parseInt(split[0]) - 1, r = Integer.parseInt(split[1]) - 1, x = Integer.parseInt(split[2]), res = -1;
            if (a[r][0] != x) {
                res = r + 1;
            } else if (a[r][1] >= l) {
                res = a[r][1] + 1;
            }
            out.println(res);
        }

        in.close();
        out.close();
    }
}