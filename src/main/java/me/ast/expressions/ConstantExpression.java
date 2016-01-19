package me.ast.expressions;

import org.jetbrains.annotations.NotNull;

/**
 * Created by viosn_000 on 19.01.2016.
 */
public interface ConstantExpression extends Expression, ResolvedExpression{
    @NotNull
    Object value();
}
