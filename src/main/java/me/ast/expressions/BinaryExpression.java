package me.ast.expressions;

import org.jetbrains.annotations.NotNull;

/**
 * Created by viosn_000 on 19.01.2016.
 */
public interface BinaryExpression extends Expression {
    enum BinaryExpressionType {
        PLUS,
        MINUS,
        AND,
        OR
    }

    @NotNull
    BinaryExpression binaryExpression();
}
