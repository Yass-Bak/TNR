package com.example.tnr;

import com.github.tomakehurst.wiremock.client.WireMock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockUtils {
    public void stubGetRequest(String endpoint, int status, String bodyFile) {
        WireMock.stubFor(get(endpoint)
                .willReturn(aResponse().withStatus(status).withHeader("Content-Type", "application/json")
                        .withBodyFile(bodyFile)));
    }
    public void stubPostRequest(String endpoint, int status, String bodyFile, String jsonRequest) throws IOException {
        String requestJson = new String(Files.readAllBytes(Paths.get("src/test/resources/" + jsonRequest)));
        WireMock.stubFor(post(endpoint)
                .withHeader("Content-Type", containing("application/json"))
                .withRequestBody(containing(requestJson))
                .willReturn(aResponse()
                        .withStatus(status)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile(bodyFile)));
    }
    public void stubGetRequestWithoutBody(String endpoint) {
        WireMock.stubFor(get(endpoint));
    }
}