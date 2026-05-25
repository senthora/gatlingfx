package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.api.GatlingSimulation;
import com.senthora.gatlingfx.runtime.core.api.SimulationScanner;

import io.github.classgraph.ClassGraph;

import java.util.List;

/**
 * Internal runtime classpath scanner
 * used by {@link SimulationScanner}.
 */
public final class ClasspathSimulationScanner {

    private static final String ANNOTATION_NAME = GatlingSimulation.class.getName();
    private static final ClassGraph CLASS_GRAPH = new ClassGraph()
            .enableClassInfo()
            .enableAnnotationInfo();

    public static List<Class<?>> scan() {
        try (var scan = CLASS_GRAPH.scan()) {
            return scan.getClassesWithAnnotation(ANNOTATION_NAME).loadClasses();
        }
    }
}
