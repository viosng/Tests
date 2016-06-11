package me.composite;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by viosng on 15.05.2016.
 */
public interface Composite<V, C extends Composite<V, C>> {
    @Nullable
    V value();

    @NotNull
    List<C> children();

    <R> R apply (@NotNull C other, @NotNull BiFunction<C, C, R> function, @NotNull Function<C, C> complexWrapper);

    default boolean isComplex() {
        return !children().isEmpty();
    }
}
