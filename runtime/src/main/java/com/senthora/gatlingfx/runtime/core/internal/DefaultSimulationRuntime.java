package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.api.SimulationExecutionResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationRuntime;
import com.senthora.gatlingfx.runtime.core.api.SimulationRuntimeException;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Default {@link SimulationRuntime} implementation.
 */
public final class DefaultSimulationRuntime implements SimulationRuntime {

    private static final Logger log = LoggerFactory.getLogger(DefaultSimulationRuntime.class);

    private final GatlingRunner gatlingRunner;
    private final GatlingConfiguration gatlingConfig;
    private final SimulationRunId runId;

    public DefaultSimulationRuntime(GatlingRunner gatlingRunner) {
        Objects.requireNonNull(gatlingRunner, "gatlingRunner must not be null");
        this.gatlingRunner = gatlingRunner;
        this.gatlingConfig = GatlingConfiguration.load();
        this.runId = SimulationRunId.create();
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
        var statusCode = run(simulationClass, gatlingArgs, runner);
        var result = statusCode == StatusCode.Success$.MODULE$
                ? SimulationResult.SUCCESS
                : SimulationResult.FAILURE;

        return new DefaultSimulationExecutionResult(simulationClass, result);
    }

    private StatusCode run(
            Class<? extends BaseSimulation> simulationClass,
            GatlingArgs gatlingArgs,
            Runner runner
    ) {
        log.info("Running simulation '{}'", simulationClass.getName());

        var result = new AtomicReference<StatusCode>(StatusCode.AssertionsFailed$.MODULE$);
        try (var logManager = new SimulationLogManager(runId, simulationClass.getSimpleName())) {
            Console.withOut(logManager.output(), redirectedRun(result, gatlingArgs, runner));
        }
        catch (AssertionError e) {
            e.printStackTrace();
            return StatusCode.AssertionsFailed$.MODULE$;
        }
        catch (Throwable e) {
            var className = simulationClass.getName();
            var message = "Failed executing simulation runtime for class " + className;
            throw new SimulationRuntimeException(message, e);
        }
        return result.get();
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
}
