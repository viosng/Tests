package me.ast.expressions;

import org.jetbrains.annotations.NotNull;

/**
 * Created by viosn_000 on 19.01.2016.
 */
public interface UnaryExpression extends Expression {
    enum UnaryExpressionType {
        MINUS,
        NOT
    }

    @NotNull
    UnaryExpressionType unaryExpressionType();
}
