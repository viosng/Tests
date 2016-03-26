package me.ast.expressions.predicates;

import me.ast.DataEntry;
import me.ast.expressions.*;
import me.ast.expressions.Expression.ExpressionType;
import me.ast.expressions.predicates.impl.ConstantExpressionPredicate;
import me.ast.expressions.predicates.impl.FieldExpressionPredicate;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by viosn_000 on 19.01.2016.
 */
public class ExpressionPredicateConverter<T extends DataEntry, C extends Context> implements ExpressionConverter<T, Predicate<T>, C> {

    @NotNull
    @Override
    public Predicate<T> convert(@NotNull Expression expression, @NotNull C context, @NotNull Supplier<T> dataEntrySupplier) {
        return dataEntry -> convertExpression(expression, context, dataEntrySupplier).asBoolean();
    }

    private ResolvedExpression convertExpression(@NotNull Expression expression, @NotNull C context, @NotNull Supplier<T> dataEntrySupplier) {
        switch (expression.expressionType()) {
            case FUNCTION:
                return convertFunctionExpression((FunctionExpression) expression, context, dataEntrySupplier);
            case CONSTANT:
                return convertConstantExpression((ConstantExpression) expression, context);
            /*case CONTAINER:
                break;
            case BINARY_OPERATION:
                break;*/
            case UNARY_OPERATION:
                return convertUnaryExpression((UnaryExpression) expression, context, dataEntrySupplier);
            case FIELD:
                return convertFieldExpression((FieldExpression) expression, dataEntrySupplier);
            default:
                throw new UnsupportedOperationException("Unsupported expression type " + expression.expressionType());
        }
    }

    private <E> void  checkArgCount(@NotNull List<E> arguments, int expectedSize) {
        checkArg(arguments, args -> args.size() != expectedSize);
    }

    private <E> void  checkArg(@NotNull E arg, @NotNull Predicate<E> predicate) {
        if (predicate.test(arg)) {
            throw new IllegalArgumentException("not meet requirements");
        }
    }

    private ResolvedExpression convertUnaryExpression(@NotNull UnaryExpression expression, @NotNull C context, @NotNull Supplier<T> dataEntrySupplier) {
        checkArgCount(expression.arguments(), 1);
        switch (expression.unaryExpressionType()) {
            case MINUS: {
                Expression arg = expression.arguments().get(0);
                switch (arg.type()){
                    case LONG:
                    case DOUBLE:
                        ResolvedExpression resolvedArg = convertExpression(arg, context, dataEntrySupplier);
                        return new ResolvedExpression() {
                            @Override
                            public Type type() {
                                return arg.type();
                            }

                            @Override
                            public Long asLong() {
                                return -1 * resolvedArg.asLong();
                            }

                            @Override
                            public Double asDouble() {
                                return -1 * resolvedArg.asDouble();
                            }
                        };
                    default:
                        throw new UnsupportedOperationException("Unsupported arg expression type " + arg.type());
                }
            }
            case NOT: {
                Expression arg = expression.arguments().get(0);
                if (arg.type() != Type.BOOLEAN) {
                    throw new UnsupportedOperationException("Unsupported arg expression type " + arg.type());
                }
                ResolvedExpression resolvedArg = convertExpression(arg, context, dataEntrySupplier);
                return new ResolvedExpression() {
                    @Override
                    public Type type() {
                        return Type.BOOLEAN;
                    }

                    @Override
                    public Boolean asBoolean() {
                        return !resolvedArg.asBoolean();
                    }
                };
            }
            default:
                throw new UnsupportedOperationException("Unsupported UnaryExpression type " + expression.unaryExpressionType());
        }
    }

    private ResolvedExpression convertFieldExpression(@NotNull FieldExpression expression, @NotNull Supplier<T> dataEntrySupplier) {
        return new FieldExpressionPredicate<>(expression, dataEntrySupplier);
    }

    private ResolvedExpression convertConstantExpression(@NotNull ConstantExpression expression, @NotNull C context) {
        return new ConstantExpressionPredicate<>(expression);
    }

    private ResolvedExpression convertFunctionExpression(@NotNull FunctionExpression expression, @NotNull C context, @NotNull Supplier<T> dataEntrySupplier) {
        switch (expression.functionType()) {
            case IN: {
                List<Expression> arguments = expression.arguments();
                checkArgCount(arguments, 2);
                ResolvedExpression resolvedValue = convertExpression(arguments.get(0), context, dataEntrySupplier);
                Expression container = arguments.get(1);
                checkArg(container.expressionType(), type -> type == ExpressionType.CONTAINER);
                Stream<ResolvedExpression> resolvedExpressions = container.arguments().stream()
                        .map(a -> convertExpression(a, context, dataEntrySupplier))
                        .filter(r -> r.type() == resolvedValue.type());
                return new ResolvedExpression() {

                    private boolean hasValue(Function<ResolvedExpression, ?> converter,
                                             Stream<ResolvedExpression> expressionStream,
                                             ResolvedExpression value) {
                        return expressionStream
                                .map(converter)
                                .collect(Collectors.toSet())
                                .contains(converter.apply(value));
                    }

                    @Override
                    public Type type() {
                        return Type.BOOLEAN;
                    }

                    @Override
                    public Boolean asBoolean() {
                        Function<ResolvedExpression, ?> as;
                        switch (resolvedValue.type()) {
                            case STRING:
                                as = ResolvedExpression::asString;
                                break;
                            case LONG:
                                as = ResolvedExpression::asLong;
                                break;
                            case DOUBLE:
                                as = ResolvedExpression::asDouble;
                                break;
                            case DATETIME:
                                as = ResolvedExpression::asLocalDateTime;
                                break;
                            case BOOLEAN:
                                as = ResolvedExpression::asBoolean;
                                break;
                            default:
                                throw new IllegalArgumentException();
                        }
                        return hasValue(as, resolvedExpressions, resolvedValue);
                    }
                };
            }

            default:
                throw new UnsupportedOperationException("Unsupported FunctionExpression type " + expression.functionType());
        }
    }

}

