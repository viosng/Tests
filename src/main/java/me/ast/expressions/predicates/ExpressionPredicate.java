package me.ast.expressions.predicates;

import me.ast.DataEntry;
import me.ast.expressions.ResolvedExpression;

import java.util.function.Predicate;

/**
 * Created by viosn_000 on 19.01.2016.
 */
public interface ExpressionPredicate<T extends DataEntry> extends Predicate<T>, ResolvedExpression {
    @Override
    default boolean test(T t) {
        throw new UnsupportedOperationException();
    }
}
