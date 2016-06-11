package net.projecteuler;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by viosng on 02.04.2016.
 */
public class CoinSums {

    private static void solve(List<Integer> coins, int lastCoin, int curSum, int desiredSum, LongAdder solveCounter) {
        if (curSum >= desiredSum) {
            if (curSum == desiredSum) {
                solveCounter.increment();
            }
            return;
        }
        coins.stream().filter(coin -> coin >= lastCoin)
                .forEach(coin -> solve(coins, coin, curSum + coin, desiredSum, solveCounter));
    }

    public static void main(String[] args) {
        LongAdder solveCounter = new LongAdder();
        solve(Arrays.asList(1, 2, 5, 10, 20, 50, 100, 200), 1, 0, 200, solveCounter);
        System.out.println(solveCounter.longValue());
    }
}
