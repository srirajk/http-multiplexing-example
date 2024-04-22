package org.example.httpmultiplexingreactive.service;


import lombok.extern.slf4j.Slf4j;
import org.example.httpmultiplexingreactive.client.ProviderClient;
import org.example.model.WebResponseModel;
import org.example.model.WrappedServiceResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class ProviderService {


    private final ProviderClient providerClient;

    public ProviderService(final ProviderClient providerClient) {
        this.providerClient = providerClient;
    }

    public Mono<WebResponseModel> execute(final String input) {

        Flux<WrappedServiceResponse> serviceInvocations = Flux.range(1, 6)
                .flatMap(i -> providerClient.getProviderData(input)
                        .map(sampleResponse -> WrappedServiceResponse.builder()
                                .sampleResponse(sampleResponse)
                                .isError(false)
                                .build())
                        .onErrorResume(e -> Mono.just(WrappedServiceResponse.builder()
                                .isError(true)
                                .exception((Exception) e)
                                .build())));


        return serviceInvocations
                .collectList()
                .flatMap(wrappedServiceResponses -> {
                    WebResponseModel webResponseModel = WebResponseModel.builder()
                            .input(input)
                            .responses(new ArrayList<>())
                            .build();
                    wrappedServiceResponses.forEach(response -> {
                        if (response.isError()) {
                            log.error("Error occurred while invoking Provider API for input {} :: {}", input, response.getException().getMessage());
                            webResponseModel.setError(response.isError());
                        } else
                            webResponseModel.getResponses().add(response.getSampleResponse());
                    });
                    return Mono.just(webResponseModel);
                });
    }

}
