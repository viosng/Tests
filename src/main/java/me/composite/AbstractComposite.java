package me.composite;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by viosng on 15.05.2016.
 */
public class AbstractComposite<V, C extends AbstractComposite<V, C>> implements Composite<V, C>{

    @Nullable
    private final V value;

    @NotNull
    private final List<C> children;

    public AbstractComposite(@Nullable V value) {
        this.value = value;
        this.children = Collections.emptyList();
    }

    public AbstractComposite(@NotNull Collection<C> children) {
        this.value = null;
        this.children = ImmutableList.copyOf(children);
    }

    @Nullable
    @Override
    public V value() {
        return value;
    }

    @NotNull
    @Override
    public List<C> children() {
        return children;
    }

    @Override
    public <R> R apply(@NotNull C that, @NotNull BiFunction<C, C, R> function, @NotNull Function<C, C> complexWrapper) {
        Function<C, C> wrapper = c -> c.isComplex() ? c : complexWrapper.apply(c);
        return function.apply(wrapper.apply((C) this), wrapper.apply(that));
    }


}
