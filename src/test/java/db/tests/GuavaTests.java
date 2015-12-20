package db.tests;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import me.concurrent.util.Pair;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;

/**
 * Created by viosng on 10.07.2015.
 */
public class GuavaTests {

    private static List<List<String>> arrange(Map<String, Long> types, int count) {
        if (count <= 0) return null;
        PriorityQueue<Pair<Long, List<String>>> queue = new PriorityQueue<>();
        range(0, count).forEach(i -> queue.add(new Pair<>(0L, new ArrayList<>())));
        types.entrySet().stream()
                .map(e -> new Pair<>(e.getKey(), e.getValue()))
                .sorted((p1, p2) -> Long.compare(p2.getValue(), p1.getValue()))
                .forEach(p -> {
                    Pair<Long, List<String>> poll = queue.poll();
                    List<String> list = poll.getValue();
                    list.add(p.getKey());
                    Pair<Long, List<String>> e = new Pair<>(poll.getKey() + p.getValue(), list);
                    queue.add(e);
                });
        return queue.stream().sorted(Pair::compareTo).map(Pair::getValue).collect(Collectors.toList());
    }

    @Test
    public void testArrange() throws Exception {
        Map<String, Long> map = ImmutableMap.<String, Long>builder()
                .put("Spot", 20L)
                .put("Spot2", 19L)
                .put("Swap", 19L)
                .put("Swa2", 18L)
                .put("t1", 2L)
                .put("t11", 1L)
                .put("t2", 2L)
                .build();
        System.out.println(arrange(map, 6));
        System.out.println(arrange(map, 5));
        System.out.println(arrange(map, 4));
        System.out.println(arrange(map, 3));
        System.out.println(arrange(map, 2));
        System.out.println(arrange(map, 1));
    }

    @Test
    public void testGuava() throws Exception {
        System.out.println(Joiner.on(", ").join(Arrays.asList(1, 2, 3)));
        Queue<Integer> q = new PriorityQueue<>();
        q.offer(3);
        q.offer(1);
        q.offer(2);
        q.offer(4);
        q.offer(0);
        System.out.println(q);
    }
}
