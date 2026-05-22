package com.senthora.gatlingfx.runtime.internal;

import com.senthora.gatlingfx.runtime.api.GatlingSimulation;
import com.senthora.gatlingfx.runtime.api.SimulationScanner;

import io.github.classgraph.ClassGraph;

import java.util.List;

/**
 * Internal runtime classpath scanner
 * used by {@link SimulationScanner}.
 */
public final class ClasspathSimulationScanner implements SimulationScanner {

    private static final ClassGraph CLASS_GRAPH = classGraph();
    private static final String ANNOTATION_NAME = GatlingSimulation.class.getName();

    public static List<Class<?>> scan() {
        try (var scan = CLASS_GRAPH.scan()) {
            return scan.getClassesWithAnnotation(ANNOTATION_NAME).loadClasses();
        }
    }

    private static ClassGraph classGraph() {
        return new ClassGraph().enableClassInfo().enableAnnotationInfo();
    }
}
