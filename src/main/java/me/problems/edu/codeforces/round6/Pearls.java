package me.problems.edu.codeforces.round6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by viosng on 17.02.2016.
 */
public class Pearls {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());
        int[] a = Stream.of(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        in.close();
        Map<Integer, Integer> indices = new HashMap<>();
        PrintWriter out = new PrintWriter(System.out);
        List<int[]> answers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int cur = i + 1;
            Integer prev = indices.putIfAbsent(a[i], cur);
            if (prev != null) {
                answers.add(new int[]{prev, cur});

                indices = new HashMap<>();
            }
        }
        if (answers.isEmpty()) out.print(-1);
        else if (answers.size() == 1) {
            out.println(answers.size());
            out.println("1 " + n);
        }
        else {
            out.println(answers.size());
            int prev = answers.get(0)[1];
            out.println(1 + " " + prev);
            for (int i = 1; i < answers.size() - 1; i++) {
                int newPrev = answers.get(i)[1];
                out.println((prev + 1) + " " + newPrev);
                prev = newPrev;
            }
            out.println((prev + 1) + " " + n);
        }
        out.close();
    }
}
