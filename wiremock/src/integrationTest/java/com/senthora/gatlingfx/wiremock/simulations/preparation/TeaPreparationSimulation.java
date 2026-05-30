package com.senthora.gatlingfx.wiremock.simulations.preparation;

import com.senthora.gatlingfx.http.api.HttpHeader;
import com.senthora.gatlingfx.runtime.core.api.GatlingSimulation;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;
import com.senthora.gatlingfx.simulation.api.SimulationBackend;
import com.senthora.gatlingfx.simulation.api.SimulationProtocol;
import com.senthora.gatlingfx.simulation.api.SimulationScenario;
import com.senthora.gatlingfx.wiremock.api.StubRequest;
import com.senthora.gatlingfx.wiremock.api.WireMockBackend;
import com.senthora.gatlingfx.wiremock.support.TeaFactory;

import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@GatlingSimulation
@DisplayName("Should prepare tea batches using questionable factory recipes")
public class TeaPreparationSimulation extends BaseSimulation {

    private final WireMockBackend backend = WireMockBackend.create(TeaFactory.BASE_URL);

    @Override
    protected SimulationBackend backend() {
        backend.stub(StubRequest.any().willReturn(418));
        backend.stub(StubRequest
                .request("GET", "/recipes/imperial-earl-grey")
                .willReturn(200)
        );
        backend.stub(StubRequest
                .requestMatching("GET", "/recipes/(spring|summer)-.*")
                .willReturn(200)
        );
        backend.stub(StubRequest.request("GET", "/recipes/custom-blend")
                .withHeader(HttpHeader.of("X-Customer-Blend", "floral"))
                .willReturn(200)
        );
        return backend;
    }

    @Override
    protected SimulationProtocol protocol() {
        return SimulationProtocol.create().baseUrl(TeaFactory.BASE_URL);
    }

    @Override
    protected List<SimulationScenario> scenarios() {
        return List.of(
                new PrepareNamedTeaBatchScenario(),
                new PrepareSeasonalTeaBatchScenario(),
                new PrepareCustomTeaBatchScenario(),
                new PrepareExperimentalTeaBatchScenario()
        );
    }

    @Override
    protected void verify() {
        var requests = backend.requests();

        assertThat(requests.lastFor("/recipes/experimental")).isPresent();
        assertThat(requests.lastFor("/recipes/imperial-earl-grey")).isPresent();
        assertThat(requests.lastFor("/recipes/spring-jasmine")).isPresent();
        assertThat(requests.lastFor("/recipes/summer-oolong")).isPresent();

        assertThat(requests.lastFor("/recipes/custom-blend"))
                .isPresent().get()
                .extracting(request -> request.header("X-Customer-Blend"))
                .isEqualTo(Optional.of("floral"));
    }
}
