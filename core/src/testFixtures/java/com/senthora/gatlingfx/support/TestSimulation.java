package com.senthora.gatlingfx.support;

import com.senthora.gatlingfx.http.api.HttpBaseUrl;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;
import com.senthora.gatlingfx.simulation.api.SimulationProtocol;

public class TestSimulation extends BaseSimulation {

    @Override
    protected SimulationProtocol protocol() {
        return SimulationProtocol.create().baseUrl(HttpBaseUrl.LOCALHOST);
    }
}
