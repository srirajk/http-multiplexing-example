package org.example.httpmultiplexingregular.client;


import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.example.model.SampleRequest;
import org.example.model.SampleResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Random;
import java.util.UUID;

@Component
@Slf4j
public class ProviderClient {

    private final Random rand = new Random();

    @Value("${provider.baseUrl}")
    private String baseUrl;
    private RestClient restClient;

    public ProviderClient() {
    }

    @PostConstruct
    public void init() {
        this.restClient = RestClient.create(baseUrl);
    }


    public SampleResponse getProviderData(final String input) {
        final SampleRequest request = new SampleRequest();
        request.setResponseField1("Response Field 1 :: " + UUID.randomUUID() + " :: " + input);
        request.setResponseField2(rand.nextInt(100));
        return restClient.post().uri("/api/provider/generateData")
                .body(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (clientHttpRequest, clientHttpResponse) -> {
                    log.info("Failed while invoking API call to Provider API :: {} :: {}", clientHttpRequest.getURI().toString(), clientHttpResponse.getStatusCode());
                    throw new RuntimeException("Failed while invoking API call to Provider API :: " + clientHttpRequest.getURI().toString() +
                            " :: " + clientHttpResponse.getStatusCode());
                    //clientHttpResponse.bodyToMono(String.class).flatMap(errorMessage ->
                })
                .body(SampleResponse.class);
    }


}
