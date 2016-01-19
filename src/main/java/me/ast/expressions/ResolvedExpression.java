package me.ast.expressions;

import java.time.LocalDateTime;

/**
 * Created by viosn_000 on 20.01.2016.
 */
public interface ResolvedExpression {
    Type type();

    default Boolean asBoolean() {
        throw new UnsupportedOperationException();
    }

    default Long asLong() {
        throw new UnsupportedOperationException();
    }

    default Double asDouble() {
        throw new UnsupportedOperationException();
    }

    default LocalDateTime asLocalDateTime() {
        throw new UnsupportedOperationException();
    }

    default String asString() {
        throw new UnsupportedOperationException();
    }
}
