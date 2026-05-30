package com.senthora.gatlingfx.wiremock.support;

import com.senthora.gatlingfx.http.api.HttpBaseUrl;
import com.senthora.gatlingfx.http.api.HttpScheme;
import com.senthora.gatlingfx.http.api.NetworkAddress;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;
import com.senthora.gatlingfx.simulation.api.SimulationProtocol;

import org.jspecify.annotations.NullMarked;

@NullMarked
public abstract class TeaFactorySimulation extends BaseSimulation {

    protected static final HttpBaseUrl BASE_URL = HttpBaseUrl.of(
            HttpScheme.HTTP,
            NetworkAddress.localhost(8081)
    );

    protected SimulationProtocol protocol() {
        return SimulationProtocol.create().baseUrl(BASE_URL);
    }
}
