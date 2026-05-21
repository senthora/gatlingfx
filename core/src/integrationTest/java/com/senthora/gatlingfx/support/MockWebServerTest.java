package com.senthora.gatlingfx.support;

import com.senthora.gatlingfx.http.api.HttpBaseUrl;
import com.senthora.gatlingfx.http.api.HttpScheme;
import com.senthora.gatlingfx.http.api.NetworkAddress;

import okhttp3.mockwebserver.MockWebServer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;

public abstract class MockWebServerTest {

    protected static MockWebServer server;
    protected static HttpBaseUrl baseUrl;

    @BeforeAll
    static void setupMockWebServerTest() throws IOException {
        server = new MockWebServer();
        server.start();

        var address = NetworkAddress.of(
                server.getHostName(),
                server.getPort()
        );
        baseUrl = HttpBaseUrl.of(HttpScheme.HTTP, address);
    }

    @AfterAll
    static void teardownMockWebServerTest() throws IOException {
        server.shutdown();
    }
}
