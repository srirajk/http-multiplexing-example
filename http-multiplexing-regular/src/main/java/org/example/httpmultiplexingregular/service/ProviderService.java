package org.example.httpmultiplexingregular.service;


import lombok.extern.slf4j.Slf4j;
import org.example.httpmultiplexingregular.client.ProviderClient;
import org.example.model.WebResponseModel;
import org.example.model.WrappedServiceResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;

@Service
@Slf4j
public class ProviderService {

    private final ExecutorService executorService;

    private final ProviderClient providerClient;

    public ProviderService(final ProviderClient providerClient, @Qualifier("customExecutorService") final ExecutorService executorService) {
        this.providerClient = providerClient;
        this.executorService = executorService;
    }

    /*public WebResponseModel execute(final String input) {

        List<WrappedServiceResponse> responses = IntStream.rangeClosed(1, 6)
                .parallel()
                .mapToObj(i -> executorService.submit(() -> providerClient.getProviderData(input))).toList()
                .parallelStream()
                .map(future -> {
                    try {
                        return WrappedServiceResponse.builder()
                                .sampleResponse(future.get())
                                .isError(false)
                                .build();
                    } catch (InterruptedException | ExecutionException e) {
                        return WrappedServiceResponse.builder()
                                .isError(true)
                                .exception(e)
                                .build();
                    }
                }).toList();

        WebResponseModel webResponseModel = WebResponseModel.builder()
                .input(input)
                .build();
        responses.parallelStream().forEach(
                response -> {
                    if (response.isError()) {
                        log.error("Error occurred while invoking Provider API for input {} :: {}", input, response.getException().getMessage());
                        webResponseModel.setError(response.isError());
                    }
                    else
                        webResponseModel.getResponses().add(response.getSampleResponse());
                    return;
                }
        );

        return webResponseModel;
    }*/
    public WebResponseModel execute(final String input) {

        List<CompletableFuture<WrappedServiceResponse>> futures = IntStream.rangeClosed(1, 6)
                .mapToObj(i -> CompletableFuture.supplyAsync(() -> {
                    try {
                        return WrappedServiceResponse.builder()
                                .sampleResponse(providerClient.getProviderData(input))
                                .isError(false)
                                .build();
                    } catch (Exception e) {
                        return WrappedServiceResponse.builder()
                                .isError(true)
                                .exception(e)
                                .build();
                    }
                }, executorService))
                .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        List<WrappedServiceResponse> responses = futures.stream()
                .map(CompletableFuture::join)
                .toList();

        WebResponseModel webResponseModel = WebResponseModel.builder()
                .input(input)
                .responses(new ArrayList<>())
                .build();

        responses.forEach(response -> {
            if (response.isError()) {
                log.error("Error occurred while invoking Provider API for input {} :: {}", input, response.getException().getMessage());
                webResponseModel.setError(response.isError());
            } else {
                webResponseModel.getResponses().add(response.getSampleResponse());
            }
        });

        return webResponseModel;
    }

}
