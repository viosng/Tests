package me.arithm;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.LongStream.range;
import static org.junit.Assert.assertEquals;

/**
 * Created by viosn_000 on 15.01.2016.
 */
public class BigIntTest {

    private static String randomNumber(int longCount) {
        return (ThreadLocalRandom.current().nextLong() % 20 == 0 ? "" : '-') +
                range(0, longCount)
                        .map(i -> ThreadLocalRandom.current().nextLong())
                        .map(Math::abs)
                        .mapToObj(Long::toString)
                        .collect(Collectors.joining());
    }

    private static final Supplier<String> strLongSupplier = () -> randomNumber(ThreadLocalRandom.current().nextInt(20) + 1);

    private static <S, T, V> V apply(@NotNull Function<S, T> creator,
                                     @NotNull BinaryOperator<T> operator,
                                     @NotNull Function<T, V> transformer,
                                     @NotNull S leftArg,
                                     @NotNull S rightArg) {
        T left = creator.apply(leftArg);
        T right = creator.apply(rightArg);
        return transformer.apply(operator.apply(left, right));
    }

    private static <S, T, R, V> void testOp(@NotNull Function<S, T> expectedCreator,
                                            @NotNull Function<S, R> actualCreator,
                                            @NotNull BinaryOperator<T> op1,
                                            @NotNull BinaryOperator<R> op2,
                                            @NotNull Function<T, V> transformer1,
                                            @NotNull Function<R, V> transformer2,
                                            @NotNull Supplier<S> supplier) {
        S left = supplier.get();
        S right = supplier.get();
        V expected = apply(expectedCreator, op1, transformer1, left, right);
        V actual = apply(actualCreator, op2, transformer2, left, right);
        assertEquals(Arrays.asList(left, right, expected, actual).toString(), expected, actual);
    }

    private static void testBigInt(@NotNull BinaryOperator<BigInteger> expected, @NotNull BinaryOperator<BigInt> actual) {
        range(0, 100000).forEach(i -> testOp(BigInteger::new, BigInt::new, expected, actual, BigInteger::toString,
                BigInt::toString, strLongSupplier));
    }

    @Test
    public void testAdd() throws Exception {
        testBigInt(BigInteger::add, BigInt::add);
    }

    @Test
    public void testSubtract() throws Exception {
        testBigInt(BigInteger::subtract, BigInt::subtract);
    }

    @Test
    public void testMultiply() throws Exception {
        testBigInt(BigInteger::multiply, BigInt::multiply);
    }

    @Test
    public void testSpecial() throws Exception {
        String left = "737";
        String right = "-991";
        BigInt bigInt1 = new BigInt(left);
        BigInt bigInt2 = new BigInt(right);
        BigInt bigInt3 = bigInt1.multiply(bigInt2);
        String s = bigInt3.toString();
        BigInteger bigInteger = new BigInteger(left);
        BigInteger bigInteger1 = new BigInteger(right);
        BigInteger bigInteger2 = bigInteger.multiply(bigInteger1);
        String s1 = bigInteger2.toString();
        assertEquals(s1, s);
    }

    @Test
    public void testCompareTo() throws Exception {

    }
}