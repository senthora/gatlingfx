package com.senthora.gatlingfx.simulation.backend;

import java.util.ArrayList;
import java.util.List;

final class LifecycleRecorder {

    private static final List<Event> EVENTS = new ArrayList<>();

    private LifecycleRecorder() {}

    static void record(Event event) {
        EVENTS.add(event);
    }

    static List<Event> events() {
        return List.copyOf(EVENTS);
    }

    static void clear() {
        EVENTS.clear();
    }

    enum Event {
        SETUP,
        REQUEST,
        VERIFY,
        TEARDOWN
    }
}
