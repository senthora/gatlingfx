package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.api.GatlingSimulation;
import com.senthora.gatlingfx.runtime.core.api.SimulationDiscoveryResult;
import com.senthora.gatlingfx.runtime.core.api.SimulationScanner;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;

import io.github.classgraph.ClassGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * Default {@link SimulationScanner} implementation.
 */
public final class ClasspathSimulationScanner implements SimulationScanner {

    private static final String ANNOTATION_NAME = GatlingSimulation.class.getName();
    private static final ClassGraph CLASS_GRAPH = new ClassGraph()
            .enableClassInfo()
            .enableAnnotationInfo();

    @Override
    public SimulationDiscoveryResult scan() {
        try (var scan = CLASS_GRAPH.scan()) {
            var discoveredClasses = scan
                    .getClassesWithAnnotation(ANNOTATION_NAME)
                    .loadClasses();

            List<Class<?>> unsupportedClasses = new ArrayList<>();
            List<Class<? extends BaseSimulation>> supportedClasses = new ArrayList<>();

            for (Class<?> clazz : discoveredClasses) {
                if (SimulationResolver.isSupported(clazz)) {
                    supportedClasses.add(SimulationResolver.resolveInternal(clazz));
                }
                else {
                    unsupportedClasses.add(clazz);
                }
            }
            return new SimulationDiscoveryResult(
                    supportedClasses,
                    unsupportedClasses
            );
        }
    }
}
