package me.ast;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by viosn_000 on 19.01.2016.
 */
@FunctionalInterface
public interface DataSource<T extends DataEntry, P extends Predicate<T>> {
    @NotNull
    Stream<T> getData(@NotNull P filter);
}
