package net.projecteuler;

/**
 * Created by viosng on 26.03.2016.
 */
public class NumberSpiralDiagonals {
    public static void main(String[] args) {
        int level = 1;
        long sum = 1;
        long cur = 1;
        while (level < 1001) {
            level += 2;
            sum += 4 * cur + 10 * (level - 1);
            cur += 4 * (level - 1);
        }
        System.out.println("cur = " + cur);
        System.out.println("sum = " + sum);
    }
}
