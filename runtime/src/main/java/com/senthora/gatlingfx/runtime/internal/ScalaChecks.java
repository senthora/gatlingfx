package com.senthora.gatlingfx.runtime.internal;

import io.gatling.core.check.CheckBuilder;

import java.lang.reflect.Proxy;

/**
 * Factory utilities for wrapping Gatling Scala
 * check builders with GatlingFx runtime behavior.
 */
final class ScalaChecks {

    private ScalaChecks() {}

    /**
     * Wraps the provided Scala check builder
     * with runtime failure recording behavior.
     *
     * @param delegate underlying Scala check builder
     *
     * @return wrapped Scala check builder
     */
    static CheckBuilder<?, ?> recording(CheckBuilder<?, ?> delegate) {
        return (CheckBuilder<?, ?>) Proxy.newProxyInstance(
                delegate.getClass().getClassLoader(),
                new Class<?>[]{CheckBuilder.class},
                new RecordingScalaCheckBuilder(delegate)
        );
    }
}
