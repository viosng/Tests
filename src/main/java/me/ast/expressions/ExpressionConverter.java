package me.ast.expressions;

import me.ast.DataEntry;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Created by viosn_000 on 19.01.2016.
 */
@FunctionalInterface
public interface ExpressionConverter<T extends DataEntry, P, C extends Context> {
    @NotNull
    P convert(@NotNull Expression expression, @NotNull C context, @NotNull Supplier<T> dataEntrySupplier);
}

