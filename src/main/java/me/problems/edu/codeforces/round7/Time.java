package me.problems.edu.codeforces.round7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by viosng on 07.03.2016.
 */
public class Time {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] split = in.readLine().split(":");

        int h = Integer.parseInt(split[0]), m = Integer.parseInt(split[1]);
        int a = Integer.parseInt(in.readLine()), all = h * 60 + m + a;
        in.close();
        PrintWriter out = new PrintWriter(System.out);
        int nH = (all / 60) % 24;
        int nM = all % 60;
        out.print((nH < 10 ? "0" : "") + nH + ":" + (nM < 10 ? "0" : "") + nM);
        out.close();
    }
}
