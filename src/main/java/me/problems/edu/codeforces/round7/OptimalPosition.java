package me.problems.edu.codeforces.round7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by viosng on 06.03.2016.
 */
public class OptimalPosition {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());
        in.close();

        PrintWriter out = new PrintWriter(System.out);
        if (n > 1) {
            int a[] = {1, 2};
            for (int k : a) {
                int i = k;
                while (i <= n) {
                    out.print(i);
                    out.print(' ');
                    i += 2;
                }
                i -= n % 2 == k % 2 ? 4 : 2;
                while (i > 0) {
                    out.print(i);
                    out.print(' ');
                    i -= 2;
                }
            }
        } else {
            out.print(n);
            out.print(' ');
        }

        out.print(n);

        out.close();
    }
}
