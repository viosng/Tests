package me.stream;

import org.jetbrains.annotations.NotNull;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by viosn_000 on 20.12.2015.
 */

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class ConsumingFlowUtil {

    public static <T> Stream<T> spliteratorBasedStream(Spliterator<T> spliterator, boolean parallel) {
        return StreamSupport.stream(spliterator, parallel);
    }

    public static <T> Stream<T> iteratorBasedStream(Iterator<T> consumingIterator) {
        return spliteratorBasedStream(Spliterators.spliteratorUnknownSize(consumingIterator,
                Spliterator.NONNULL | Spliterator.CONCURRENT), false);
    }

    public static <T> Stream<T> iteratorBasedParallelStream(Iterator<T> consumingIterator) {
        return spliteratorBasedStream(Spliterators.spliteratorUnknownSize(consumingIterator,
                Spliterator.NONNULL | Spliterator.CONCURRENT), true);
    }

    public static <T> ConsumingIterator<T> consumingIterator() {
        return new ConsumingIterator<T>() {
            private final LinkedBlockingQueue<T> queue = new LinkedBlockingQueue<>();
            private volatile boolean finish;

            @Override
            public void accept(@NotNull T t) {
                queue.add(t);
            }

            @Override
            public boolean hasNext() {
                return !finish || !queue.isEmpty();
            }

            @Override
            public T next() {
                T take;
                try {
                    take = queue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return take;
            }

            public void finish() {
                finish = true;
            }
        };
    }

    public static <T> ConsumingSpliterator<T> consumingSpliterator() {
        return new ConsumingSpliterator<T>(){

            private final LinkedBlockingQueue<T> queue = new LinkedBlockingQueue<>();
            private volatile boolean finish;

            @Override
            public void accept(T t) {
                queue.add(t);
            }

            @Override
            public void finish() {
                finish = true;
            }

            @Override
            public boolean tryAdvance(Consumer<? super T> action) {
                if (finish && queue.isEmpty()) {
                    return false;
                }
                try {
                    action.accept(queue.take());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }

            @Override
            public Spliterator<T> trySplit() {
                return null;
            }

            @Override
            public long estimateSize() {
                return 0;
            }

            @Override
            public int characteristics() {
                return Spliterator.NONNULL | Spliterator.CONCURRENT;
            }
        };
    }

    public static <F extends ConsumingFlow<Integer>> boolean measureFlow(F consumingFlow, Function<F, Stream<Integer>> toStream) {
        LongAdder adder = new LongAdder();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 10_000_000; i++) {
            int t = random.nextInt();
            if (t > 0) adder.increment();
            consumingFlow.accept(t);
        }
        consumingFlow.finish();
        return toStream.apply(consumingFlow).filter(x -> x > 0).mapToInt(x -> x).count() == adder.intValue();
    }

    @Benchmark
    public static void measureIterator() {
        measureFlow(consumingIterator(), ConsumingFlowUtil::iteratorBasedStream);
    }

    @Benchmark
    public static void measureParallelIterator() {
        measureFlow(consumingIterator(), ConsumingFlowUtil::iteratorBasedParallelStream);
    }

    @Benchmark
    public static void measureSpliterator() {
        measureFlow(consumingSpliterator(), s -> spliteratorBasedStream(s, false));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ConsumingFlowUtil.class.getSimpleName())
                .warmupIterations(5)
                .forks(2)
                .measurementIterations(10)
                .build();

        new Runner(opt).run();
    }

}
