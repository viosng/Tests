package me.stream;

import java.util.function.Consumer;

/**
 * Created by viosn_000 on 20.12.2015.
 */
public interface ConsumingFlow<T> extends Consumer<T> {
    void finish();
}
