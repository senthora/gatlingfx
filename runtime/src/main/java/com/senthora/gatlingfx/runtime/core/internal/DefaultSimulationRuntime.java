package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.api.SimulationExecutionResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationRuntime;
import com.senthora.gatlingfx.runtime.core.api.SimulationRuntimeException;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;
import com.senthora.gatlingfx.simulation.api.SimulationContext;

import org.jspecify.annotations.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.gatling.app.RunResultProcessor;
import io.gatling.app.Runner;
import io.gatling.app.cli.StatusCode;
import io.gatling.core.actor.ActorSystem;
import io.gatling.core.cli.GatlingArgs;
import io.gatling.core.config.GatlingConfiguration;
import io.netty.channel.epoll.EpollEventLoopGroup;
import scala.Console;
import scala.Option;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Default {@link SimulationRuntime} implementation.
 */
public final class DefaultSimulationRuntime implements SimulationRuntime {

    private static final DateTimeFormatter RUN_ID_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS");

    private static final Logger log = LoggerFactory.getLogger("SimulationRuntime");

    static {
        SimulationContextRegistry.initialize();
    }
    private final String runId;
    private final GatlingRunner gatlingRunner;
    private final GatlingConfiguration gatlingConfig;
    private final SimulationLogManager logManager;

    public DefaultSimulationRuntime(GatlingRunner gatlingRunner) {
        Objects.requireNonNull(gatlingRunner, "gatlingRunner must not be null");

        this.runId = RUN_ID_FORMATTER.format(LocalDateTime.now());
        this.gatlingRunner = gatlingRunner;
        this.gatlingConfig = GatlingConfiguration.load();
        this.logManager = new SimulationLogManager(
                Path.of("build/gatlingfx"),
                runId
        );
    }

    @Override
    public List<SimulationExecutionResult> execute(
            List<Class<? extends BaseSimulation>> simulationClasses
    ) {
        Objects.requireNonNull(simulationClasses, "simulationClasses must not be null");
        List<SimulationExecutionResult> results = new ArrayList<>();

        try (var actorSystem = new ActorSystem()) {
            var eventLoopGroup = new EpollEventLoopGroup();
            try {
                for (var simulationClass : simulationClasses) {
                    var result = execute(simulationClass, actorSystem, eventLoopGroup);
                    results.add(result);
                }
            }
            finally {
                var terminate = eventLoopGroup.shutdownGracefully(
                        0,
                        0,
                        TimeUnit.SECONDS
                );
                terminate.syncUninterruptibly();
            }
        }
        var okCount = results.stream()
                .map(SimulationExecutionResult::result)
                .filter(r -> r == SimulationResult.SUCCESS)
                .count();

        var koCount = results.stream()
                .map(SimulationExecutionResult::result)
                .filter(r -> r == SimulationResult.FAILURE)
                .count();

        log.info("Finished running {} simulations (ok={}, ko={})",
                results.size(),
                okCount,
                koCount
        );
        log.info("Logs available at: {}", logManager.logDirectory().toUri());
        return results;
    }

    private SimulationExecutionResult execute(
            Class<? extends BaseSimulation> simulationClass,
            ActorSystem actorSystem,
            EpollEventLoopGroup eventLoopGroup
    ) {
        var gatlingArgs = GatlingArgs.apply(
                Option.apply(simulationClass.getName()),
                Option.empty(),
                false,
                Option.empty(),
                Option.apply(Path.of("build/gatling")),
                Option.empty(),
                Option.empty()
        );
        var runner = Runner.apply(
                actorSystem,
                eventLoopGroup,
                gatlingArgs,
                gatlingConfig
        );
        var className = simulationClass.getName();
        log.info("Running simulation '{}'", className);
        var stopwatch = SimulationStopwatch.start();

        ExecutionResult executionResult;
        try {
            executionResult = execute(simulationClass, gatlingArgs, runner);
        }
        catch (Throwable e) {
            log.error("Simulation run failed (time={} ms)",
                    stopwatch.elapsed().toMillis(),
                    e
            );
            var message = "Failed executing simulation runtime for class " + className;
            throw new SimulationRuntimeException(message, e);
        }
        var context = executionResult.context;
        var simulationResult = executionResult.simulationResult();
        long simulationRunTime = stopwatch.elapsed().toMillis();

        if (context.hasFailed()) {
            var error = executionResult.context.failure().orElseThrow();
            var message = error.getMessage();
            log.warn("Simulation run failed (time={} ms, reason={})",
                    simulationRunTime,
                    message != null ? message : "unknown"
            );
        }
        else if (simulationResult == SimulationResult.FAILURE) {
            log.warn("Simulation run failed (time={} ms)", simulationRunTime);
        }
        else {
            log.info("Simulation run successful (time={} ms)", simulationRunTime);
        }
        return new DefaultSimulationExecutionResult(simulationClass, simulationResult);
    }

    private ExecutionResult execute(
            Class<? extends BaseSimulation> simulationClass,
            GatlingArgs gatlingArgs,
            Runner runner
    ) {
        SimulationContext context;
        var result = new AtomicReference<StatusCode>(StatusCode.AssertionsFailed$.MODULE$);
        var logFilePath = Path.of("N/A");

        try (var logSession = logManager.createSession(simulationClass)) {
            logFilePath = logSession.logFilePath();
            var outStream = logSession.output();

            Console.withOut(outStream, redirectedRun(result, gatlingArgs, runner));

            context = SimulationContextRegistry.get(simulationClass).orElseThrow(() -> {
                var className = simulationClass.getName();
                return new IllegalStateException("Unable to find context for class " + className);
            });
            if (context.hasFailed()) {
                context.failure().orElseThrow().printStackTrace(outStream);
            }
        }
        return new ExecutionResult(result.get(), logFilePath, context);
    }

    private scala.Function0<@Nullable Void> redirectedRun(
            AtomicReference<StatusCode> result,
            GatlingArgs gatlingArgs,
            Runner runner
    ) {
        return () -> {
            var runResult = gatlingRunner.run(gatlingArgs, runner);
            var processor = new RunResultProcessor(
                    gatlingArgs,
                    gatlingConfig
            );
            result.set(processor.processRunResult(runResult));
            return null;
        };
    }

    private record ExecutionResult(
            StatusCode code,
            Path logFilePath,
            SimulationContext context
    ) {
        private SimulationResult simulationResult() {
            if (context.hasFailed()) {
                return SimulationResult.FAILURE;
            }
            if (code.equals(StatusCode.Success$.MODULE$)) {
                return SimulationResult.SUCCESS;
            }
            return SimulationResult.FAILURE;
        }
    }
}
