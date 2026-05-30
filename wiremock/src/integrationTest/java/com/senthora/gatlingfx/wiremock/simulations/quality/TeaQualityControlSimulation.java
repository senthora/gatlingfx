package com.senthora.gatlingfx.wiremock.simulations.quality;

import com.senthora.gatlingfx.http.api.HttpHeader;
import com.senthora.gatlingfx.runtime.core.api.GatlingSimulation;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;
import com.senthora.gatlingfx.simulation.api.SimulationBackend;
import com.senthora.gatlingfx.simulation.api.SimulationProtocol;
import com.senthora.gatlingfx.simulation.api.SimulationScenario;
import com.senthora.gatlingfx.wiremock.api.StubMapping;
import com.senthora.gatlingfx.wiremock.api.StubRequest;
import com.senthora.gatlingfx.wiremock.api.StubResponse;
import com.senthora.gatlingfx.wiremock.api.WireMockBackend;
import com.senthora.gatlingfx.wiremock.support.TeaFactorySimulation;

import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@GatlingSimulation
@DisplayName("Should issue quality control reports for questionable tea batches")
public class TeaQualityControlSimulation extends BaseSimulation {

    private final WireMockBackend backend = WireMockBackend.create(TeaFactorySimulation.BASE_URL);

    @Override
    protected SimulationBackend backend() {
        backend.stub(new StubMapping(
                StubRequest.request("GET", "/quality/inspection-result"),
                new StubResponse(200, "APPROVED", List.of())
                        .withHeader(HttpHeader.of("X-Inspection-Status", "passed"))
        ));
        backend.stub(StubRequest
                .request("GET", "/quality/report")
                .willReturnJson(200, "{\"result\":\"approved\"}")
        );
        backend.stub(StubRequest
                .request("GET", "/quality/warehouse-label")
                .willReturnText(200, "BATCH-42")
        );
        return backend;
    }

    @Override
    protected SimulationProtocol protocol() {
        return SimulationProtocol.create().baseUrl(TeaFactorySimulation.BASE_URL);
    }

    @Override
    protected List<SimulationScenario> scenarios() {
        return List.of(
                new IssueInspectionResultScenario(),
                new IssueQualityReportScenario(),
                new IssueWarehouseLabelScenario()
        );
    }

    @Override
    protected void verify() {
        var requests = backend.requests();

        assertThat(requests.lastFor("/quality/inspection-result")).isPresent();
        assertThat(requests.lastFor("/quality/report")).isPresent();
        assertThat(requests.lastFor("/quality/warehouse-label")).isPresent();
    }
}
