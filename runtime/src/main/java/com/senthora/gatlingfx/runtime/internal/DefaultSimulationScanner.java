package com.senthora.gatlingfx.runtime.internal;

import com.senthora.gatlingfx.runtime.api.GatlingSimulation;

import io.github.classgraph.ClassGraph;

import java.util.List;

/**
 * Discovers GatlingFx simulations
 * available on the runtime classpath.
 * <p>
 * A simulation is considered discoverable
 * when annotated with {@link GatlingSimulation}.
 * <p>
 * This component is intended for runtime integrations that
 * need to automatically locate and execute simulations
 * without requiring explicit registration.
 */
final class DefaultSimulationScanner {

    private static final ClassGraph CLASS_GRAPH = classGraph();
    private static final String ANNOTATION_NAME = GatlingSimulation.class.getName();

    private DefaultSimulationScanner() {}

    /**
     * Scans the runtime classpath for discoverable simulations.
     *
     * @return discovered simulation classes
     */
    static List<Class<?>> scan() {
        try (var scan = CLASS_GRAPH.scan()) {
            return scan.getClassesWithAnnotation(ANNOTATION_NAME).loadClasses();
        }
    }

    private static ClassGraph classGraph() {
        return new ClassGraph().enableClassInfo().enableAnnotationInfo();
    }
}
