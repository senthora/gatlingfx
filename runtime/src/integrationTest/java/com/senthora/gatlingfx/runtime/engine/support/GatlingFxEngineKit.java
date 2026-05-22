package com.senthora.gatlingfx.runtime.engine.support;

import org.junit.platform.engine.discovery.ClassSelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.testkit.engine.EngineDiscoveryResults;
import org.junit.platform.testkit.engine.EngineExecutionResults;
import org.junit.platform.testkit.engine.EngineTestKit;

import java.util.Arrays;
import java.util.List;

public final class GatlingFxEngineKit {

    private final EngineTestKit.Builder builder =
            EngineTestKit.engine("gatlingfx");

    private GatlingFxEngineKit() {}

    public static GatlingFxEngineKit engine() {
        return new GatlingFxEngineKit();
    }

    public GatlingFxEngineKit select(Class<?>... classes) {
        builder.selectors(selectClasses(classes));
        return this;
    }

    public GatlingFxEngineKit select(List<Class<?>> classes) {
        builder.selectors(selectClasses(classes));
        return this;
    }

    public EngineDiscoveryResults discover() {
        return builder.discover();
    }

    public EngineExecutionResults execute() {
        return builder.execute();
    }

    private static List<ClassSelector> selectClasses(Class<?>... classes) {
        return Arrays.stream(classes)
                .map(DiscoverySelectors::selectClass)
                .toList();
    }

    private static List<ClassSelector> selectClasses(List<Class<?>> classes) {
        return classes.stream()
                .map(DiscoverySelectors::selectClass)
                .toList();
    }
}
