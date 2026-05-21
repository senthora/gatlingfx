package com.senthora.gatlingfx.runtime.internal;

import com.senthora.gatlingfx.runtime.api.SimulationCheck;

import io.gatling.javaapi.core.CheckBuilder;

public final class RecordingValidate<X> implements SimulationCheck<X> {

    private final CheckBuilder.Validate<X> delegate;

    public RecordingValidate(CheckBuilder.Validate<X> delegate) {
        this.delegate = delegate;
    }

    public CheckBuilder.Final is(X expected) {
        return new RecordingFinal(delegate.is(expected));
    }
}
