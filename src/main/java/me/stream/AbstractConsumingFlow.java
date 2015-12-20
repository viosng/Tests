package me.stream;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by viosn_000 on 20.12.2015.
 */
public abstract class AbstractConsumingFlow<T> implements ConsumingFlow<T> {
    protected final LinkedBlockingQueue<T> queue = new LinkedBlockingQueue<>();
    protected volatile boolean finish;

    @Override
    public void accept(T t) {
        queue.add(t);
    }

    @Override
    public void finish() {
        finish = true;
    }
}
