package net.projecteuler;

import java.util.*;

/**
 * Created by viosng on 02.04.2016.
 */
public class PandigitalNumbers {

    private static int digitCount(int x) {
        return (int) (Math.log10(x) + 1);
    }

    private static Set<Integer> splitToDigits(int x) {
        Set<Integer> digits = new HashSet<>();
        int count = digitCount(x), i = 0;
        while (i++ < count) {
            digits.add(x % 10);
            x /= 10;
        }
        return digits;
    }

    public static void main(String[] args) {
        Set<List<Integer>> mult = new HashSet<>();
        for (int i = 1; i < 1000000; i++) {
            for (int j = 1; ; j++) {
                int digitCount = digitCount(i) + digitCount(j) + digitCount(i * j);
                if (digitCount == 9) {
                    Set<Integer> digits = splitToDigits(i);
                    digits.addAll(splitToDigits(j));
                    digits.addAll(splitToDigits(i * j));
                    if (digits.size() == 9 && !digits.contains(0)) {
                        List<Integer> numbers = Arrays.asList(i, j, i * j);
                        numbers.sort(Comparator.naturalOrder());
                        mult.add(numbers);
                    }
                } else if (digitCount > 9) {
                    break;
                }
            }
        }
        System.out.println(mult.size());
        System.out.println("mult = " + mult);
        System.out.println(mult.stream().mapToInt(list -> list.get(list.size() - 1)).distinct()
                .sum());
    }
}
