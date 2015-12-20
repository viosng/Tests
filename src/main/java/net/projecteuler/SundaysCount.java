package net.projecteuler;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

/**
 * Created by vio on 25.10.2015.
 */
public class SundaysCount {
    public static void main(String[] args) {
        LocalDateTime tmp = LocalDateTime.of(1901, Month.JANUARY, 6, 0, 0);
        LocalDateTime end = LocalDateTime.of(2000, Month.DECEMBER, 31, 0, 0);
        int count = 0;
        while (tmp.isBefore(end)) {
            if (tmp.getDayOfMonth() == 1 && tmp.getDayOfWeek() == DayOfWeek.SUNDAY) {
                count++;
            }
            tmp = tmp.plusDays(7);
        }
        System.out.println("count = " + count);
    }
}
