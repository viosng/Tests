package me.problems.round;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by viosng on 07.03.2016.
 */
public class Joysticks {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] split = in.readLine().split(" ");

        int a1 = Integer.parseInt(split[0]), a2 = Integer.parseInt(split[1]);
        in.close();
        PrintWriter out = new PrintWriter(System.out);
        long sum = 0;
        while (a1 > 0 && a2 > 0) {
            int a = Math.max(a1, a2);
            int aMin = Math.min(a1, a2);
            int count = a / 2;
            if (a % 2 == 0) count--;
            if (count == 0) break;
            sum += count;
            a1 = aMin + count;
            a2 = a - (count << 1);
        }
        out.print(sum);
        out.close();
    }
}
