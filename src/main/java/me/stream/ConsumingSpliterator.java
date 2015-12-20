package me.stream;

import java.util.Spliterator;

/**
 * Created by viosn_000 on 20.12.2015.
 */
public interface ConsumingSpliterator<T> extends ConsumingFlow<T>, Spliterator<T> {
}
