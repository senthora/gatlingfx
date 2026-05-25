package com.senthora.gatlingfx.runtime.support;

import com.senthora.gatlingfx.http.api.HttpBaseUrl;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;
import com.senthora.gatlingfx.simulation.api.SimulationProtocol;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class TestBaseSimulation extends BaseSimulation {

    @Override
    protected SimulationProtocol protocol() {
        return SimulationProtocol.create().baseUrl(HttpBaseUrl.LOCALHOST);
    }
}
