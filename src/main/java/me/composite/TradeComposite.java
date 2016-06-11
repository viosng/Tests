package me.composite;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Created by viosng on 15.05.2016.
 */
public class TradeComposite extends AbstractComposite<TradeFieldValue, TradeComposite> {
    public TradeComposite(@Nullable TradeFieldValue value) {
        super(value);
    }

    public TradeComposite(@NotNull Collection<TradeComposite> children) {
        super(children);
    }
}
