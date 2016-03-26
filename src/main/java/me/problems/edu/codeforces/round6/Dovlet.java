package me.problems.edu.codeforces.round6;

import java.util.Scanner;

/**
 * Created by viosng on 17.02.2016.
 */
public class Dovlet {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int a = in.nextInt(), b = in.nextInt();
        long count = 0;
        for (int i = a; i <= b; i++) {
            int t = i;
            while(t > 0) {
                switch (t % 10) {
                    case 0: count += 6; break;
                    case 1: count += 2; break;
                    case 2: count += 5; break;
                    case 3: count += 5; break;
                    case 4: count += 4; break;
                    case 5: count += 5; break;
                    case 6: count += 6; break;
                    case 7: count += 3; break;
                    case 8: count += 7; break;
                    default: count += 6; break;
                }
                t /= 10;
            }
        }
        System.out.println(count);
    }
}
