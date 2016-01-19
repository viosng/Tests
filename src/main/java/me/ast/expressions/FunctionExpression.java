package me.ast.expressions;

import org.jetbrains.annotations.NotNull;

/**
 * Created by viosn_000 on 19.01.2016.
 */
public interface FunctionExpression extends Expression {

    enum FunctionType {
        IN
    }

    @NotNull
    FunctionType functionType();
}
