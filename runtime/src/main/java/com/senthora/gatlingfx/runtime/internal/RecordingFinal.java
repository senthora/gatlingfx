package com.senthora.gatlingfx.runtime.internal;

import io.gatling.javaapi.core.CheckBuilder;

import java.util.Objects;

/**
 * Internal {@link CheckBuilder.Final} wrapper
 * ensuring GatlingFx runtime failure recording
 * remains attached to finalized Gatling checks.
 */
final class RecordingFinal implements CheckBuilder.Final {

    private final CheckBuilder.Final delegate;

    RecordingFinal(CheckBuilder.Final delegate) {
        Objects.requireNonNull(delegate, "delegate must not be null");
        this.delegate = delegate;
    }

    @Override
    public CheckBuilder.Final name(String n) {
        return new RecordingFinal(delegate.name(n));
    }

    @Override
    public CheckBuilder.Final logActualValueInError(boolean b) {
        return new RecordingFinal(delegate.logActualValueInError(b));
    }

    @Override
    public CheckBuilder.Final saveAs(String key) {
        return new RecordingFinal(delegate.saveAs(key));
    }

    @Override
    public io.gatling.core.check.CheckBuilder<?, ?> asScala() {
        return ScalaChecks.recording(
                delegate.asScala()
        );
    }

    @Override
    public CheckType type() {
        return delegate.type();
    }
}
