package net.projecteuler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by viosng on 28.02.2016.
 */
public class MaxPeriod {

    private static String divide(int p, int q) {
        int d = p / q;
        int r = p % q;
        if (r == 0) return Integer.toString(d);
        String res = Integer.toString(d) + ',';
        p = r;
        List<Integer> numbers = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();
        Map<Integer, Integer> indices = new HashMap<>();
        while (p > 0) {
            int count = 0;
            while (p < q) {
                p *= 10;
                count++;
            }
            d = p / q;
            r = p % q;
            Integer index = indices.get(r);
            if (index != null) {
                for (int i = 0; i < index; i++) {
                    String number = numbers.get(i).toString();
                    count = counts.get(i);
                    for (int j = 0; j < count; j++) {
                        res += '0';
                    }
                    res += number;
                }
                res += "(";
                for (int i = index; i < numbers.size(); i++) {
                    String number = numbers.get(i).toString();
                    count = counts.get(i);
                    for (int j = 0; j < count; j++) {
                        res += '0';
                    }
                    res += number;
                }
                return res + ')';
            }
            indices.put(r, numbers.size());
            numbers.add(d);
            counts.add(count - 1);
            p = r;
        }
        for (int i = 0; i < numbers.size(); i++) {
            String number = numbers.get(i).toString();
            int count = counts.get(i);
            for (int j = 0; j < count; j++) {
                res += '0';
            }
            res += number;
        }
        return res;
    }

    public static void main(String[] args) {
        String max = "";
        int maxD = 0;
        for (int d = 1; d < 1001; d++) {
            String div = divide(1, d);
            int i = div.indexOf("(");
            if (i > 0) {
                String period = div.substring(i + 1, div.lastIndexOf(")"));
                if (period.length() > max.length()) {
                    max = period;
                    maxD = d;
                    //System.out.println("d = " + d+ ", div = " + div);
                }
            }
        }
        //System.out.println("max = " + max);
        System.out.println("maxD = " + maxD);
    }
}
