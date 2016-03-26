package me.problems.edu.codeforces.round6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Map.Entry;

/**
 * Created by viosng on 18.02.2016.
 */
public class TwoArrays {

    private static long minOneSum(long[][] a, long sumA, long[] b, long sumB, List<int[]> steps, long min) {
        TreeMap<Long, Integer> map = new TreeMap<>();
        int aLen = a.length;
        for (int i = 0; i < aLen; i++) {
            map.put(sumA - 2 * a[i][0], (int)a[i][1]);
        }
        int bLen = b.length;
        for (int i = 0; i < bLen; i++) {
            long s = sumB - 2 * b[i];
            Entry<Long, Integer> floor = map.floorEntry(s);
            Entry<Long, Integer> ceil = map.ceilingEntry(s);
            Entry<Long, Integer> m;
            if (floor == null || ceil == null) {
                m = floor == null ? ceil : floor;
            } else {
                m = Math.abs(floor.getKey() - s) < Math.abs(ceil.getKey() - s) ? floor : ceil;
            }
            long mAbs = Math.abs(m.getKey() - s);
            if (mAbs < min) {
                steps.clear();
                steps.add(new int[]{m.getValue(), i});
                min = mAbs;
            }
        }
        return min;
    }


    /* fucking idiotism!!!!!!!!!!*/
    private static long minTwoSum(long[][] a, long sumA, long[] b, long sumB, List<int[]> steps, long min) {
        int aLen = a.length;
        List<long[]> sortedList = new ArrayList<>();
        //Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < aLen; i++) {
            for (int j = 0; j < i; j++) {
                sortedList.add(new long[]{sumA - 2 * a[i][0] - 2 * a[j][0], a[i][1] * 10000 + a[j][1]}); // facepalm
            }

        }
        long[][] sorted = sortedList.toArray(new long[0][]);
        Comparator<long[]> c = new Comparator<long[]>() {
            @Override
            public int compare(long[] o1, long[] o2) {
                return Long.compare(o1[0], o2[0]);
            }
        };
        Arrays.sort(sorted, c);
        //System.out.println("map two add = " + stopwatch.elapsed(TimeUnit.MILLISECONDS));
        int bLen = b.length;
        long[] sample = new long[2];
        for (int i = 0; i < bLen; i++) {
            for (int j = 0; j < i; j++) {
                long s = sumB - 2 * b[i] - 2 * b[j];
                sample[0] = s;
                int index = Arrays.binarySearch(sorted, sample, c);
                if (index < 0) {
                    index = -index - 1;
                    if (index > 0 && index < sorted.length) {
                        index =  Math.abs(sorted[index][0] - s) < Math.abs(sorted[index - 1][0] - s) ? index : index - 1;
                    } else if (index >= sorted.length) {
                        index--;
                    }
                }
                long mAbs = Math.abs(sorted[index][0] - s);
                if (mAbs < min) {
                    steps.clear();
                    steps.add(new int[]{(int) (sorted[index][1]/ 10000), i});
                    steps.add(new int[]{(int) (sorted[index][1]% 10000), j});
                    min = mAbs;
                }
            }
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("stopwatch = " + stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
        return min;
    }

    private static void solve(long[] a, long[] b) {
        int nA = a.length;
        long sumA = 0;
        for (int i = 0; i < nA; i++) {
            sumA += a[i];
        }
        int nB = b.length;
        long sumB = 0;
        for (int i = 0; i < nB; i++) {
            sumB += b[i];
        }
        long[][] aA = new long[a.length][2];
        for (int i = 0; i < a.length; i++) {
            aA[i][0] = a[i];
            aA[i][1] = i;
        }
        Arrays.sort(aA, new Comparator<long[]>() {
            @Override
            public int compare(long[] o1, long[] o2) {
                return Long.compare(o1[0], o2[0]);
            }
        });
        List<int[]> steps = new ArrayList<>();
        long min = minOneSum(aA, sumA, b, sumB, steps, Math.abs(sumA - sumB));
        if (nA > 1 && nB > 1) min = minTwoSum(aA, sumA, b, sumB, steps, min);
        System.out.println(min);
        System.out.println(steps.size());
        for (int[] step : steps) {
            System.out.println((step[0] + 1) + " " + (step[1] + 1));
        }
    }

    private static void solveMain() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        /*int nA = Integer.parseInt(in.readLine());
        long[] a = Stream.of(in.readLine().split(" ")).mapToLong(Long::parseLong).toArray();
        int nB = Integer.parseInt(in.readLine());
        long[] b = Stream.of(in.readLine().split(" ")).mapToLong(Long::parseLong).toArray();*/
        int nA = Integer.parseInt(in.readLine());
        String[] split = in.readLine().split(" ");
        long[] a = new long[nA];
        for (int i = 0; i < nA; i++) {
            a[i] = Long.parseLong(split[i]);
        }
        int nB = Integer.parseInt(in.readLine());
        split = in.readLine().split(" ");
        long[] b = new long[nB];
        for (int i = 0; i < nB; i++) {
            b[i] = Long.parseLong(split[i]);
        }
        solve(a, b);
    }

    /*private static void solveTest(int n) {
        Supplier<long[]> getArray = () -> range(0, n).mapToLong(i -> ThreadLocalRandom.current().nextInt()).toArray();
        Stopwatch started = Stopwatch.createStarted();
        solve(getArray.get(), getArray.get());
        System.out.println("started = " + started.stop().elapsed(TimeUnit.MILLISECONDS));
    }*/

    public static void main(String... args) throws IOException {
        //solveTest(2000);
        solveMain();
    }
}
