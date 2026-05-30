package com.senthora.gatlingfx.wiremock.simulations.runs;

import com.senthora.gatlingfx.http.api.SimpleHttpClient;
import com.senthora.gatlingfx.runtime.core.api.GatlingSimulation;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;
import com.senthora.gatlingfx.simulation.api.SimulationBackend;
import com.senthora.gatlingfx.simulation.api.SimulationProtocol;
import com.senthora.gatlingfx.simulation.api.SimulationScenario;
import com.senthora.gatlingfx.wiremock.api.StubRequest;
import com.senthora.gatlingfx.wiremock.api.WireMockBackend;
import com.senthora.gatlingfx.wiremock.support.TeaFactorySimulation;

import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@GatlingSimulation
@DisplayName("Should begin new tea production runs with minimal paperwork")
public class TeaProductionRunSimulation extends BaseSimulation {

    private final WireMockBackend backend = WireMockBackend.create(
            TeaFactorySimulation.BASE_URL,
            StubRequest.any().willReturnText(200, "homemade")
    );

    @Override
    protected SimulationBackend backend() {
        return backend;
    }

    @Override
    protected SimulationProtocol protocol() {
        return SimulationProtocol.create().baseUrl(TeaFactorySimulation.BASE_URL);
    }

    @Override
    protected List<SimulationScenario> scenarios() {
        return List.of(new PrepareHouseBlendScenario());
    }

    @Override
    protected void verify() {
        var httpClient = SimpleHttpClient.create(TeaFactorySimulation.BASE_URL);
        var requests = backend.requests();

        assertThat(requests.lastFor("/recipes/house")).isPresent();
        assertThat(httpClient.get("/recipes/house").statusCode())
                .isEqualTo(200);

        backend.reset();

        assertThat(httpClient.get("/recipes/house").statusCode())
                .isNotEqualTo(200);
    }
}
