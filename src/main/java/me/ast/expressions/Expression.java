package me.ast.expressions;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * Created by viosn_000 on 19.01.2016.
 */
public interface Expression {
    enum ExpressionType {
        FUNCTION {
            @NotNull
            @Override
            public Class<FunctionExpression> representationClass() {
                return FunctionExpression.class;
            }
        },
        CONSTANT {
            @NotNull
            @Override
            public Class<ConstantExpression> representationClass() {
                return ConstantExpression.class;
            }
        },
        CONTAINER {
            @NotNull
            @Override
            public Class<Expression> representationClass() {
                return Expression.class;
            }
        },
        BINARY_OPERATION {
            @NotNull
            @Override
            public Class<BinaryExpression> representationClass() {
                return BinaryExpression.class;
            }
        },
        UNARY_OPERATION {
            @NotNull
            @Override
            public Class<UnaryExpression> representationClass() {
                return UnaryExpression.class;
            }
        },
        FIELD {
            @NotNull
            @Override
            public Class<FieldExpression> representationClass() {
                return FieldExpression.class;
            }
        };

        @NotNull
        public abstract Class<? extends Expression> representationClass();
    }

    @NotNull
    default List<Expression> arguments() {
        return Collections.emptyList();
    }

    @NotNull
    ExpressionType expressionType();

    @NotNull
    Type type();
}
