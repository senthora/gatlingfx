package com.senthora.gatlingfx.runtime.core.internal.support;

import java.util.ArrayList;
import java.util.List;

public abstract class OrderedSimulation extends SuccessfulSimulation {

    public static final List<Integer> executionOrder = new ArrayList<>();

    @Override
    protected final void verify() {
        executionOrder.add(orderNumber());
    }

    protected abstract int orderNumber();
}
