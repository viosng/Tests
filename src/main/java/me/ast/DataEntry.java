package me.ast;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by viosn_000 on 19.01.2016.
 */
public interface DataEntry {
    @Nullable
    <T> T getValue(@NotNull String fieldName, @NotNull Class<T> tClass);
}
