package me.arithm;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.commons.math3.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

/**
 * Created by viosn_000 on 06.01.2016.
 */
public class BigInt implements Comparable<BigInt>{
    private static final int ALIGNMENT = 9;
    private static final long OVERFLOW = (long) Math.pow(10, ALIGNMENT);
    private static final char MINUS = '-';
    private static final BinaryOperator<Long> PLUS_OPERATOR = (a, b) -> a + b;
    private static final BinaryOperator<Long> MINUS_OPERATOR = (a, b) -> a - b;
    private static final BinaryOperator<Long> MULT_OPERATOR = (a, b) -> a * b;
    public static final BigInt ZERO = new BigInt("0");
    public static final BigInt ONE = new BigInt("1");

    private final boolean isNegative;

    @NotNull
    private final List<Long> data; // reversed

    private static String align(@NotNull String s, int alignment) {
        int r = s.length() % alignment;
        return Strings.repeat("0", r == 0 ? 0 : alignment - r) + s;
    }

    private BigInt(boolean isNegative, @NotNull Collection<Long> data) {
        this.isNegative = isNegative;
        this.data = ImmutableList.copyOf(data);
    }

    public BigInt(@NotNull String number) {
        if (number.isEmpty()) {
            throw new IllegalArgumentException("empty number");
        }
        if (number.charAt(0) == MINUS) {
            isNegative = true;
            number = number.substring(1);
        } else {
            isNegative = false;
        }

        String s = align(number, ALIGNMENT);
        data = Lists.newArrayList(Splitter.fixedLength(ALIGNMENT).split(s))
                .stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());
        Collections.reverse(data);
    }

    private static Pair<Long, Long> operationWithOverflow(@NotNull BinaryOperator<Long> op, @NotNull Long operand, @NotNull Long ...operands) {
        Long v = operand;
        for (Long cur : operands) {
            v = op.apply(v, cur);
        }
        return new Pair<>(v / OVERFLOW, v % OVERFLOW);
    }

    @NotNull
    private List<Long> applyOp(@NotNull BinaryOperator<Long> operator,
                               @NotNull Supplier<Long> left,
                               @NotNull Supplier<Long> right,
                               @NotNull BooleanSupplier until) {
        List<Long> newData = new ArrayList<>();
        Long overflow = 0L;
        while (until.getAsBoolean()) {
            Pair<Long, Long> pair = operationWithOverflow(operator, left.get(), right.get(), overflow);
            newData.add(pair.getSecond());
            overflow = pair.getFirst();
        }
        if (overflow > 0) newData.add(overflow);
        return newData;
    }

    @NotNull
    public BigInt add(@NotNull BigInt that) {
        if (this.isNegative != that.isNegative) {
            return this.isNegative
                    ? that.subtract(new BigInt(false, this.data))
                    : subtract(new BigInt(false, that.data));
        }
        List<Long> moreData = this.data;
        List<Long> lessData = that.data;
        if (moreData.size() < lessData.size()) {
            List<Long> tmp = moreData;
            moreData = lessData;
            lessData = tmp;
        }
        lessData = new ArrayList<>(lessData);
        lessData.addAll(Collections.nCopies(moreData.size() - lessData.size(), 0L));
        Iterator<Long> moreIterator = moreData.iterator();
        Iterator<Long> lessIterator = lessData.iterator();
        List<Long> newData = new ArrayList<>();
        Long overflow = 0L;
        while (moreIterator.hasNext()) {
            Long next = lessIterator.hasNext() ? lessIterator.next() : 0L;
            Pair<Long, Long> pair = operationWithOverflow(PLUS_OPERATOR, moreIterator.next(), next, overflow);
            newData.add(pair.getSecond());
            overflow = pair.getFirst();
        }
        if (overflow > 0) newData.add(overflow);
        return new BigInt(this.isNegative, newData);
    }

    @NotNull
    public BigInt subtract(@NotNull BigInt that) {
        if (this.isNegative != that.isNegative) {
            return add(new BigInt(this.isNegative, that.data));
        }
        int compareTo = this.compareTo(that) * (this.isNegative ? -1 : 1);
        if (compareTo == 0) {
            return ZERO;
        }
        BigInt more = this, less = that;
        if (compareTo < 0) {
            more = that;
            less = this;
        }
        List<Long> newData = new ArrayList<>();
        Iterator<Long> moreIterator = more.data.iterator();
        Iterator<Long> lessIterator = less.data.iterator();
        Long overflow = 0L;
        while (moreIterator.hasNext()) {
            Long left = moreIterator.next() + OVERFLOW;
            Long right = lessIterator.hasNext() ? lessIterator.next() : 0L;
            Pair<Long, Long> pair = operationWithOverflow(MINUS_OPERATOR, left, right, overflow);
            newData.add(pair.getSecond());
            overflow = 1 - pair.getFirst();
        }
        int firstNoneZero;
        for (firstNoneZero = newData.size() - 1; firstNoneZero >= 0 && newData.get(firstNoneZero) == 0; firstNoneZero--);
        boolean sign = this.isNegative ? compareTo > 0 : compareTo < 0;
        return firstNoneZero >= 0 ? new BigInt(sign, newData.subList(0, firstNoneZero + 1)) : ZERO;
    }

    @NotNull
    public BigInt multiply(@NotNull BigInt that) {
        if (this.equals(ZERO) || that.equals(ZERO)) {
            return ZERO;
        }
        BigInt res = ZERO;
        for (int i = 0; i < that.data.size(); i++) {
            Long value = that.data.get(i);
            List<Long> newData = new ArrayList<>(Collections.nCopies(i, 0L));
            Iterator<Long> iterator = this.data.iterator();
            Long overflow = 0L;
            while (iterator.hasNext()) {
                Long next = iterator.next();
                Pair<Long, Long> pair = operationWithOverflow(MULT_OPERATOR, next, value);
                Pair<Long, Long> pair2 = operationWithOverflow(PLUS_OPERATOR, pair.getSecond(), overflow);
                newData.add(pair2.getSecond());
                overflow = pair.getFirst() + pair2.getFirst();
            }
            if (overflow > 0) newData.add(overflow);
            res = res.add(new BigInt(false, newData));
        }
        return new BigInt(this.isNegative != that.isNegative, res.data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BigInt bigInt = (BigInt) o;

        return data.equals(bigInt.data);
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public String toString() {
        List<Long> list = new ArrayList<>(data);
        Collections.reverse(list);
        return (isNegative ? MINUS : "")
                + list.get(0).toString()
                + list.subList(1, list.size()).stream()
                .map(l -> align(l.toString(), ALIGNMENT))
                .collect(Collectors.joining());
    }

    @Override
    public int compareTo(@NotNull BigInt that) {
        int negativeMultiplier = this.isNegative ? -1 : 1;
        if (this.isNegative == that.isNegative) {
            int size = this.data.size();
            int compare = Integer.compare(size, that.data.size());
            if (compare != 0) {
                return compare * negativeMultiplier;
            }
            for (int i = size - 1; i >= 0; i--) {
                compare = Long.compare(this.data.get(i), that.data.get(i));
                if (compare != 0) {
                    return compare * negativeMultiplier;
                }
            }
            return 0;
        }
        return negativeMultiplier;
    }


    public static void main(String... args) {
        /*String number = "99900123456789000000000123456789";
        BigInt bigInt = new BigInt(number);
        System.out.println(bigInt);
        System.out.println(bigInt.toString().equals(number));
        System.out.println(new BigInt("1"));
        System.out.println(new BigInt("111111111111111111").add(new BigInt("111111111000000000888888889")));
        System.out.println(new BigInteger("111111111111111111").add(new BigInteger("111111111000000000888888889")));
        System.out.println(new BigInt("1").compareTo(new BigInt("-1100000000")));
        System.out.println(new BigInt("111111111111111111").subtract(new BigInt("111111111111111110")));*/


    }
}
