package me.problems.edu.codeforces.round6;

import java.util.Scanner;

/**
 * Created by viosng on 17.02.2016.
 */
public class Gukiz {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long x1 = in.nextLong(), y1 = in.nextLong();
        long x2 = in.nextLong(), y2 = in.nextLong();
        System.out.println(Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)));
    }
}
