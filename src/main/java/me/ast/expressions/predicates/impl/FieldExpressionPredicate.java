package me.ast.expressions.predicates.impl;

import me.ast.DataEntry;
import me.ast.expressions.FieldExpression;
import me.ast.expressions.Type;
import me.ast.expressions.predicates.ExpressionPredicate;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.function.Supplier;

/**
 * Created by viosn_000 on 20.01.2016.
 */
public class FieldExpressionPredicate<T extends DataEntry> implements ExpressionPredicate<T> {

    @NotNull
    private final FieldExpression expression;

    @NotNull
    private final Supplier<T> dataEntrySupplier;

    public FieldExpressionPredicate(@NotNull FieldExpression expression, @NotNull Supplier<T> dataEntrySupplier) {
        this.expression = expression;
        this.dataEntrySupplier = dataEntrySupplier;
    }

    private <E> E cast(@NotNull Class<E> eClass) {
        return dataEntrySupplier.get().getValue(expression.field(), eClass);
    }

    @Override
    public boolean test(T t) {
        return asBoolean();
    }

    @Override
    public Type type() {
        return expression.type();
    }

    @Override
    public Boolean asBoolean() {
        return cast(Boolean.class);
    }

    @Override
    public Long asLong() {
        return cast(Long.class);
    }

    @Override
    public Double asDouble() {
        return cast(Double.class);
    }

    @Override
    public LocalDateTime asLocalDateTime() {
        return cast(LocalDateTime.class);
    }

    @Override
    public String asString() {
        return cast(String.class);
    }
}