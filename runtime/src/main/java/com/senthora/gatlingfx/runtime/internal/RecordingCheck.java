package com.senthora.gatlingfx.runtime.internal;

import io.gatling.commons.validation.Validation;
import io.gatling.core.check.Check;
import io.gatling.core.check.CheckResult;
import io.gatling.core.session.Session;
import scala.runtime.BoxedUnit;

import java.util.Map;
import java.util.Objects;

/**
 * Internal {@link Check} wrapper recording
 * runtime validation failures in the current
 * GatlingFx simulation execution context.
 *
 * @param <R> check input type
 */
final class RecordingCheck<R> implements Check<R> {

    private final Check<R> delegate;

    RecordingCheck(Check<R> delegate) {
        Objects.requireNonNull(delegate, "delegate must not be null");
        this.delegate = delegate;
    }

    @Override
    public Validation<CheckResult> check(R response, Session session, Map<Object, Object> cache) {
        var validation = delegate.check(response, session, cache);
        validation.onFailure(message -> {
            SimulationExecution.current().markFailed();
            return BoxedUnit.UNIT;
        });
        return validation;
    }

    @Override
    public Check<R> checkIf(scala.Function1<Session, Validation<Object>> condition) {
        return new RecordingCheck<>(delegate.checkIf(condition));
    }

    @Override
    public Check<R> checkIf(scala.Function2<R, Session, Validation<Object>> condition) {
        return new RecordingCheck<>(delegate.checkIf(condition));
    }
}
