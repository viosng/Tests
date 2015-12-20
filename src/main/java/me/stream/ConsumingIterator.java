package me.stream;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Created by viosn_000 on 20.12.2015.
 */
public interface ConsumingIterator<T> extends ConsumingFlow<T>, Iterator<T> {
}
