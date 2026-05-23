package com.senthora.gatlingfx.simulation.support;

import com.senthora.gatlingfx.http.api.HttpBaseUrl;
import com.senthora.gatlingfx.http.api.HttpHost;
import com.senthora.gatlingfx.http.api.HttpScheme;
import com.senthora.gatlingfx.http.api.NetworkAddress;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;
import com.senthora.gatlingfx.simulation.api.SimulationProtocol;

import org.jspecify.annotations.NullMarked;

@NullMarked
public abstract class TeaShopSimulation extends BaseSimulation {

    private static final HttpBaseUrl BASE_URL = HttpBaseUrl.of(
            HttpScheme.HTTP,
            new NetworkAddress(HttpHost.LOCALHOST, 4180)
    );

    @Override
    protected SimulationProtocol protocol() {
        return SimulationProtocol.create().baseUrl(BASE_URL);
    }
}
