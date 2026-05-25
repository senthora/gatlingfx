package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.api.SimulationExecutionResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationRuntime;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;

import io.gatling.app.RunResultProcessor;
import io.gatling.app.Runner;
import io.gatling.app.cli.StatusCode;
import io.gatling.core.actor.ActorSystem;
import io.gatling.core.cli.GatlingArgs;
import io.gatling.core.config.GatlingConfiguration;
import io.netty.channel.epoll.EpollEventLoopGroup;
import scala.Option;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Default {@link SimulationRuntime} implementation.
 */
public final class DefaultSimulationRuntime implements SimulationRuntime {

    private final GatlingRunner gatlingRunner;
    private final GatlingConfiguration gatlingConfig;

    public DefaultSimulationRuntime(GatlingRunner gatlingRunner) {
        Objects.requireNonNull(gatlingRunner, "gatlingRunner must not be null");
        this.gatlingRunner = gatlingRunner;
        this.gatlingConfig = loadConfiguration();
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
            Class<?> simulationClass,
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
        var statusCode = run(gatlingArgs, runner);
        var result = statusCode == StatusCode.Success$.MODULE$
                ? SimulationResult.SUCCESS
                : SimulationResult.FAILURE;

        return new DefaultSimulationExecutionResult(simulationClass, result);
    }

    private StatusCode run(GatlingArgs gatlingArgs, Runner runner) {
        try {
            var runResult = gatlingRunner.run(gatlingArgs, runner);
            var processor = new RunResultProcessor(gatlingArgs, gatlingConfig);

            return processor.processRunResult(runResult);
        }
        catch (AssertionError e) {
            e.printStackTrace();
            return StatusCode.AssertionsFailed$.MODULE$;
        }
        catch (Throwable e) {
            var message = "Failed executing simulation runtime";
            throw new DefaultSimulationRuntimeException(message, e);
        }
    }

    private static GatlingConfiguration loadConfiguration() {
        System.setProperty(
                "logback.configurationFile",
                "src/resources/logback-test.xml"
        );
        return GatlingConfiguration.load();
    }
}
