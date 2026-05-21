package com.senthora.gatlingfx.runtime.internal;

import io.gatling.core.check.Check;
import io.gatling.core.check.CheckBuilder;
import io.gatling.http.check.HttpCheck;
import io.gatling.http.response.Response;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Invocation handler intercepting Gatling
 * Scala check builder runtime check creation.
 * <p>
 * Built {@link HttpCheck} instances are reconstructed
 * with {@link RecordingCheck} wrappers so GatlingFx
 * can observe runtime validation failures.
 */
final class RecordingScalaCheckBuilder implements InvocationHandler {

    private final CheckBuilder<?, ?> delegate;

    /**
     * Creates a new recording Scala check builder.
     *
     * @param delegate underlying Scala check builder
     */
    RecordingScalaCheckBuilder(CheckBuilder<?, ?> delegate) {
        Objects.requireNonNull(delegate, "delegate must not be null");
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!method.getName().equals("build")) {
            return method.invoke(delegate, args);
        }
        Object built = method.invoke(delegate, args);

        if (!(built instanceof HttpCheck httpCheck)) {
            return built;
        }
        Check<Response> recording = new RecordingCheck<>(httpCheck.wrapped());

        return new HttpCheck(recording, httpCheck.scope());
    }
}
